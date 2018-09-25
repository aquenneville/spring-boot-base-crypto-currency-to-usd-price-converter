package github.aq.priceconverter;

import static org.junit.Assert.*;

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
            List<AssetPairPrice> list = loader.load("src/test/resources/BITFINEX-ETHUSD.csv");
            assertTrue(((List) list).size() >0);
        } catch(IOException exc) {
            System.out.println(exc.getMessage());
        }
    }

    @Test
    public void testLoadForEthUsd() {
        AssetPairPricesLoader loader = new AssetPairPricesLoader();
        try {
            List<AssetPairPrice> list = loader.load(AssetPair.ETHUSD);
            assertTrue(((List) list).size() >0);
            System.out.println("test");
            AssetPairPrice app = (AssetPairPrice) list.get(0);
            System.out.println(app.getDate() + " " + app.getLast());
        } catch(IOException exc) {
            System.out.println(exc.getMessage());
        }

    }

}
