package github.aq.priceconverter;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestAssetPairPricesLoader {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testLoad() {
        AssetPairPricesLoader loader = new AssetPairPricesLoader();
        try {
            Map<LocalDate, AssetPairPrice> map = loader.load(AssetPair.ETHUSD, "src/test/resources/BITFINEX-ETHUSD.csv");
            assertTrue(map.size() >0);

        } catch(IOException exc) {
            System.out.println(exc.getMessage());
        }
    }

    @Test
    public void testLoadForEthUsd() {
        AssetPairPricesLoader loader = new AssetPairPricesLoader();
        try {
            Map<LocalDate, AssetPairPrice> map = loader.load(AssetPair.ETHUSD);
            assertTrue(map.size() >0);
            System.out.println("test");
            AssetPairPrice app = (AssetPairPrice) map.get(0);

            System.out.println(app.getDate() + " " + app.getLast());
        } catch(IOException exc) {
            System.out.println(exc.getMessage());
        }

    }

}
