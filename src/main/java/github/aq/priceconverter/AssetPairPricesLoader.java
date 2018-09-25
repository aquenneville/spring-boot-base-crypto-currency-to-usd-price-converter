package github.aq.priceconverter;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class AssetPairPricesLoader {

    private List<AssetPairPrice> assetPairPrices;
    
    public AssetPairPricesLoader() {
        assetPairPrices = new ArrayList<>();
    }

    public List<AssetPairPrice> load(AssetPair assetPair) throws IOException {
        switch(assetPair) {
            case ETHUSD:
                return load("src/main/resources/BITFINEX-ETHUSD.csv");

            case BTCUSD:
                return load("src/main/resources/BCHARTS-KRAKENUSD.csv");
            default:
                return null;
        }
    }

    public List<AssetPairPrice> load(String filename) throws IOException {
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
                        app.setDate(csvRecord.get("Date"));
                        app.setLast(csvRecord.get("Last"));
                        assetPairPrices.add(app);

                    }
                    i ++;
                }
                return assetPairPrices;
        }
    }
}
