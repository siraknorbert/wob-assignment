package hu.wob.assignment.pojo;

import java.math.BigDecimal;

public class ListingMetricsWithMonthPojo extends AbstractListingMetricsPojo {

    private String month;

    public ListingMetricsWithMonthPojo() {
        super();
    }

    public ListingMetricsWithMonthPojo(long totalListingCount, long totalEBayListingCount, BigDecimal totalEBayListingPrice,
            BigDecimal averageEBayListingPrice, long totalAmazonListingCount, BigDecimal totalAmazonListingPrice,
            BigDecimal averageAmazonListingPrice, String bestListerEmailAddress, String month) {
        super(totalListingCount, totalEBayListingCount, totalEBayListingPrice, averageEBayListingPrice, totalAmazonListingCount,
                totalAmazonListingPrice, averageAmazonListingPrice, bestListerEmailAddress);
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
