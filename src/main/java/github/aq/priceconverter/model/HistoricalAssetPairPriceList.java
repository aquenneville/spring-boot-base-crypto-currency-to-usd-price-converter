package github.aq.priceconverter.model;

import java.time.LocalDate;
import java.util.Map;

public class HistoricalAssetPairPriceList {
    
    Map<LocalDate, AssetPairPrice> assetPairPriceList;
    AssetPair assetPair;
    
    public Map<LocalDate, AssetPairPrice> getAssetPairPriceList() {
        return assetPairPriceList;
    }
    
    public void setAssetPairPriceList(Map<LocalDate, AssetPairPrice> assetPairPriceList) {
        this.assetPairPriceList = assetPairPriceList;
    }
    
    public AssetPair getAssetPair() {
        return assetPair;
    }
    
    public void setAssetPair(AssetPair assetPair) {
        this.assetPair = assetPair;
    }
    
}
