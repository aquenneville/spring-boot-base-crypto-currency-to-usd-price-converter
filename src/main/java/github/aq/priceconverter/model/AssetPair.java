package github.aq.priceconverter.model;

import java.util.Arrays;

public enum AssetPair {
    ETHUSD, ETHEUR, ETHBTC, BTCUSD, EURUSD, XVGETH, TRONETH, NEOETH, ETCETH;

    public static boolean contains(String test) {

        return Arrays.stream(AssetPair.values()).anyMatch((t) -> t.name().equals(test));

    }
}
