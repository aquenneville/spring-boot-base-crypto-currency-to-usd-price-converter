package github.aq.priceconverter;

import java.util.List;

public class HistoricalAssetPairPriceList {
    
    List<AssetPairPrice> assetPairPriceList;
    AssetPair assetPair;
    
    public List<AssetPairPrice> getAssetPairPriceList() {
        return assetPairPriceList;
    }
    public void setAssetPairPriceList(List<AssetPairPrice> assetPairPriceList) {
        this.assetPairPriceList = assetPairPriceList;
    }
    public AssetPair getAssetPair() {
        return assetPair;
    }
    public void setAssetPair(AssetPair assetPair) {
        this.assetPair = assetPair;
    }
    
}
