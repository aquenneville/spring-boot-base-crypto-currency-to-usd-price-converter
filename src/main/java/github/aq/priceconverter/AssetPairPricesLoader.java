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
    
    public List<AssetPairPrice> load() throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get("BITFINEX-ETHUSD.csv"));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withHeader("Date", "High", "Low", "Mid", "Last", "Bid", "Ask", "Volume")
                        .withIgnoreHeaderCase()
                        .withTrim());
            ) {
                for (CSVRecord csvRecord : csvParser) {
                    AssetPairPrice app = new AssetPairPrice();
                    app.setDate(csvRecord.get("Date"));
                    app.setLast(csvRecord.get("Last"));
                    assetPairPrices.add(app);
                }
                return assetPairPrices;
        }
    }
}
