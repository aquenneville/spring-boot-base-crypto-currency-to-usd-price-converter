package github.aq.priceconverter.model;

import java.time.LocalDate;

public class AssetPairPrice {
    
    private AssetPair assetPair;
    private LocalDate date;
    private String high;
    private String low;
    private String mid;
    private String last;
    private String bid;
    private String ask;
    private String volume;
    
    public AssetPair getAssetPair() {
        return assetPair;
    }
    
    public void setAssetPair(AssetPair assetPair) {
        this.assetPair = assetPair;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getHigh() {
        return high;
    }
    
    public void setHigh(String high) {
        this.high = high;
    }
    
    public String getLow() {
        return low;
    }
    
    public void setLow(String low) {
        this.low = low;
    }
    
    public String getMid() {
        return mid;
    }
    
    public void setMid(String mid) {
        this.mid = mid;
    }
    
    public String getLast() {
        return last;
    }
    
    public void setLast(String last) {
        this.last = last;
    }
    
    public String getBid() {
        return bid;
    }
    
    public void setBid(String bid) {
        this.bid = bid;
    }
    
    public String getAsk() {
        return ask;
    }
    
    public void setAsk(String ask) {
        this.ask = ask;
    }
    
    public String getVolume() {
        return volume;
    }
    
    public void setVolume(String volume) {
        this.volume = volume;
    }
}
