package github.aq.priceconverter.model;

import github.aq.priceconverter.service.AssetPairPricesLoader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HistoricalAssetPairPriceDictionary {

    Map<AssetPair, HistoricalAssetPairPriceList> historicalPrices;

    public HistoricalAssetPairPriceDictionary() {
        historicalPrices = new HashMap<>();
    }

    public AssetPair getBaseAssetPair(AssetPair assetPair) {
        if (assetPair.toString().toUpperCase().endsWith("ETH")) {
            assetPair = AssetPair.ETHUSD;
        }
        if (assetPair.toString().toUpperCase().endsWith("BTC")) {
            assetPair = AssetPair.BTCUSD;
        }
        if (assetPair.toString().toUpperCase().endsWith("EUR")) {
            assetPair = AssetPair.EURUSD;
        }
        return assetPair;
    }

    public void addHistoricalBaseAssetPairPrices(AssetPair assetPair) throws IOException {
        AssetPair baseAssetPair = getBaseAssetPair(assetPair);
        if (!historicalPrices.containsKey(baseAssetPair)) {
            AssetPairPricesLoader loader = new AssetPairPricesLoader();
            Map<LocalDate, AssetPairPrice> historicalAssetPrices = loader.load(baseAssetPair);
            if (historicalAssetPrices != null) {
                HistoricalAssetPairPriceList historicalAssetPriceList = new HistoricalAssetPairPriceList();
                historicalAssetPriceList.setAssetPairPriceList(historicalAssetPrices);
                historicalAssetPriceList.setAssetPair(baseAssetPair);
                historicalPrices.put(baseAssetPair, historicalAssetPriceList);
            }
        }
    }

    public boolean hasAssetPair(AssetPair assetPair) {
        AssetPair baseAssetPair = getBaseAssetPair(assetPair);
        return historicalPrices.containsKey(baseAssetPair);
    }

    public boolean hasAssetPairAndPrice(AssetPair assetPair, String date) {
        AssetPair baseAssetPair = getBaseAssetPair(assetPair);
        return hasAssetPair(baseAssetPair) ?
                historicalPrices.get(baseAssetPair).getAssetPairPriceList().containsKey(LocalDate.parse(date)): false;
    }

    public AssetPairPrice getAssetPairPrice(AssetPair assetPair, String date) {
        AssetPair baseAssetPair = getBaseAssetPair(assetPair);
        return historicalPrices.get(baseAssetPair).getAssetPairPriceList().get(LocalDate.parse(date));
    }
}
