package github.aq.priceconverter;

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

            default:
                return null;
        }
    }

    public Map<LocalDate, AssetPairPrice> load(AssetPair assetPair, String filename) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filename));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withHeader("Date", "High", "Low", "Mid", "Last", "Bid", "Ask", "Volume")
                        .withIgnoreHeaderCase()
                        .withTrim());
            ) {
                int i = 0;
                for (CSVRecord csvRecord : csvParser) {
                    if (i > 0) {
                        AssetPairPrice app = new AssetPairPrice();
                        app.setDate(LocalDate.parse(csvRecord.get("Date")));
                        app.setAssetPair(assetPair);
                        app.setBid(csvRecord.get("Bid"));
                        app.setAsk(csvRecord.get("Ask"));
                        app.setHigh(csvRecord.get("High"));
                        app.setLow(csvRecord.get("Low"));
                        app.setMid(csvRecord.get("Mid"));
                        app.setLast(csvRecord.get("Last"));
                        assetPairPrices.put(app.getDate(), app);
                    }
                    i ++;
                }
                return assetPairPrices;
        }
    }
}
