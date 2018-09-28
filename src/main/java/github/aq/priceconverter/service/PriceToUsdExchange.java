package github.aq.priceconverter.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import github.aq.priceconverter.model.AssetPair;
import github.aq.priceconverter.model.AssetPairPrice;
import github.aq.priceconverter.model.Order;

import java.util.HashMap;
import java.time.LocalDate;

public class PriceToUsdExchange {


    private Map<AssetPair, AssetPair> assetPairPaths;
    private AssetPair initialAssetPair;

    public PriceToUsdExchange(AssetPair initialAssetPair) {
        this.initialAssetPair = initialAssetPair;
        assetPairPaths = new HashMap<>();
        assetPairPaths.put(AssetPair.ETHBTC, AssetPair.BTCUSD);
        assetPairPaths.put(AssetPair.ETHEUR, AssetPair.EURUSD);
    }

    public Order convertPriceToUsd(LocalDate date, BigDecimal amount) throws IOException {

        AssetPair assetPairExchange = assetPairPaths.get(initialAssetPair);
        AssetPairPricesLoader appl = new AssetPairPricesLoader();
        Map<LocalDate, AssetPairPrice> historicalAssetPrices = appl.load(assetPairExchange);
        if (historicalAssetPrices.containsKey(LocalDate.parse(date.toString()))) {
            AssetPairPrice app = historicalAssetPrices.get(date);
            Order order = new Order();
            app.setAssetPair(assetPairExchange);
            order.setApp(app);
            order.setAmount(amount);
            return order;
        } else {
            throw new IllegalStateException("The price does not exist for this date");
        }
    }
}
