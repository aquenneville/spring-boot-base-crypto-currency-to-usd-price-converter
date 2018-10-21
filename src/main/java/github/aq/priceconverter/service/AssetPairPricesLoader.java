package github.aq.priceconverter.service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import github.aq.priceconverter.model.AssetPair;
import github.aq.priceconverter.model.AssetPairPrice;

public class AssetPairPricesLoader {

    private Map<LocalDate, AssetPairPrice> assetPairPrices;
    
    public AssetPairPricesLoader() {
        assetPairPrices = new HashMap<>();
    }

    public Map<LocalDate, AssetPairPrice> load(AssetPair assetPair) throws IOException {
        switch(assetPair) {
            case ETHUSD:
                return load(AssetPair.ETHUSD, "src/main/resources/BITFINEX-ETHUSD.csv");

            case BTCUSD:
                return load(AssetPair.BTCUSD, "src/main/resources/BCHARTS-KRAKENUSD.csv");
                
            case ETHBTC:
                return load(AssetPair.ETHBTC, "src/main/resources/BITFINEX-ETHBTC.csv");

            case ETHEUR:
                return load(AssetPair.ETHEUR, "src/main/resources/GDAX-ETH_EUR.csv");
            case EURUSD:
                return load(AssetPair.EURUSD, "src/main/resources/ECB-EURUSD.csv");
            default:
                return null;
        }
    }

    public Map<LocalDate, AssetPairPrice> load(AssetPair assetPair, String filename) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        //.withHeader("Date", "High", "Low", "Mid", "Last", "Bid", "Ask", "Volume")
                        .withIgnoreHeaderCase()
                        .withTrim());
            ) {
                int i = 0;
                for (CSVRecord csvRecord : csvParser) {
                    if (i > 0) {
                        AssetPairPrice app = new AssetPairPrice();

                        app.setAssetPair(assetPair);
                        if (filename.contains("GDAX-ETH_EUR.csv")) { // starts GDAX

                            app.setDate(LocalDate.parse(csvRecord.get(0)));
                            app.setLast(csvRecord.get(1));
                            app.setHigh(csvRecord.get(2));
                            app.setLow(csvRecord.get(3));
                        } else if (filename.contains("ECB-EURUSD.csv")) { // starts with ECB
                            app.setDate(LocalDate.parse(csvRecord.get(0)));
                            app.setLast(csvRecord.get(1));
                        } else {
                            app.setDate(LocalDate.parse(csvRecord.get(0)));
                            app.setHigh(csvRecord.get(1)); // 1
                            app.setLow(csvRecord.get(2)); // 2
                            app.setMid(csvRecord.get(3)); // 3
                            app.setLast(csvRecord.get(4)); //4
                            app.setBid(csvRecord.get(5)); // 5
                            app.setAsk(csvRecord.get(6));
                        }

                        assetPairPrices.put(app.getDate(), app);
                    }
                    i ++;
                }
                return assetPairPrices;
        }
    }
}
