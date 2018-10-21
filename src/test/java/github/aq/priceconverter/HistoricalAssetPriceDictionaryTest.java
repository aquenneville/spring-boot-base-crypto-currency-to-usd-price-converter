package github.aq.priceconverter;

import github.aq.priceconverter.model.AssetPair;
import github.aq.priceconverter.model.HistoricalAssetPairPriceDictionary;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HistoricalAssetPriceDictionaryTest {

    @Test
    public void testAddAssetPairWithMissingHistoricalPrices() throws IOException {
        HistoricalAssetPairPriceDictionary dictionary = new HistoricalAssetPairPriceDictionary();
        dictionary.addHistoricalBaseAssetPairPrices(AssetPair.XVGETH);
        assertTrue(dictionary.hasAssetPair(AssetPair.XVGETH));
    }

    @Test
    public void testAddAssetPairWithExistingHistoricalPrices() throws IOException {
        HistoricalAssetPairPriceDictionary dictionary = new HistoricalAssetPairPriceDictionary();
        dictionary.addHistoricalBaseAssetPairPrices(AssetPair.ETHBTC);
        assertTrue(dictionary.hasAssetPair(AssetPair.ETHBTC));
    }

    @Test
    public void testGetBaseAssetPairOfXvgEth() {
        AssetPair baseAssetPair = new HistoricalAssetPairPriceDictionary().getBaseAssetPair(AssetPair.XVGETH);
        assertTrue(baseAssetPair == AssetPair.ETHUSD);
    }

    @Test
    public void testGetBaseAssetPairOfEthBtc() {
        AssetPair baseAssetPair = new HistoricalAssetPairPriceDictionary().getBaseAssetPair(AssetPair.ETHBTC);
        assertTrue(baseAssetPair == AssetPair.BTCUSD);
    }

    @Test
    public void testGetBaseAssetPairOfEthEur() {
        AssetPair baseAssetPair = new HistoricalAssetPairPriceDictionary().getBaseAssetPair(AssetPair.ETHEUR);
        assertTrue(baseAssetPair == AssetPair.EURUSD);
    }
}
