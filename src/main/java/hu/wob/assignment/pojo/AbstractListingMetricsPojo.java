package hu.wob.assignment.pojo;

import java.math.BigDecimal;

public abstract class AbstractListingMetricsPojo {

    private long totalListingCount;
    private long totalEBayListingCount;
    private BigDecimal totalEBayListingPrice;
    private BigDecimal averageEBayListingPrice;
    private long totalAmazonListingCount;
    private BigDecimal totalAmazonListingPrice;
    private BigDecimal averageAmazonListingPrice;
    private String bestListerEmailAddress;

    protected AbstractListingMetricsPojo() {
    }

    protected AbstractListingMetricsPojo(long totalListingCount, long totalEBayListingCount, BigDecimal totalEBayListingPrice,
            BigDecimal averageEBayListingPrice, long totalAmazonListingCount, BigDecimal totalAmazonListingPrice,
            BigDecimal averageAmazonListingPrice, String bestListerEmailAddress) {
        this.totalListingCount = totalListingCount;
        this.totalEBayListingCount = totalEBayListingCount;
        this.totalEBayListingPrice = totalEBayListingPrice;
        this.averageEBayListingPrice = averageEBayListingPrice;
        this.totalAmazonListingCount = totalAmazonListingCount;
        this.totalAmazonListingPrice = totalAmazonListingPrice;
        this.averageAmazonListingPrice = averageAmazonListingPrice;
        this.bestListerEmailAddress = bestListerEmailAddress;
    }

    public long getTotalListingCount() {
        return totalListingCount;
    }

    public void setTotalListingCount(long totalListingCount) {
        this.totalListingCount = totalListingCount;
    }

    public long getTotalEBayListingCount() {
        return totalEBayListingCount;
    }

    public void setTotalEBayListingCount(long totalEBayListingCount) {
        this.totalEBayListingCount = totalEBayListingCount;
    }

    public BigDecimal getTotalEBayListingPrice() {
        return totalEBayListingPrice;
    }

    public void setTotalEBayListingPrice(BigDecimal totalEBayListingPrice) {
        this.totalEBayListingPrice = totalEBayListingPrice;
    }

    public BigDecimal getAverageEBayListingPrice() {
        return averageEBayListingPrice;
    }

    public void setAverageEBayListingPrice(BigDecimal averageEBayListingPrice) {
        this.averageEBayListingPrice = averageEBayListingPrice;
    }

    public long getTotalAmazonListingCount() {
        return totalAmazonListingCount;
    }

    public void setTotalAmazonListingCount(long totalAmazonListingCount) {
        this.totalAmazonListingCount = totalAmazonListingCount;
    }

    public BigDecimal getTotalAmazonListingPrice() {
        return totalAmazonListingPrice;
    }

    public void setTotalAmazonListingPrice(BigDecimal totalAmazonListingPrice) {
        this.totalAmazonListingPrice = totalAmazonListingPrice;
    }

    public BigDecimal getAverageAmazonListingPrice() {
        return averageAmazonListingPrice;
    }

    public void setAverageAmazonListingPrice(BigDecimal averageAmazonListingPrice) {
        this.averageAmazonListingPrice = averageAmazonListingPrice;
    }

    public String getBestListerEmailAddress() {
        return bestListerEmailAddress;
    }

    public void setBestListerEmailAddress(String bestListerEmailAddress) {
        this.bestListerEmailAddress = bestListerEmailAddress;
    }
}
