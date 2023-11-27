package hu.wob.assignment.pojo;

import java.math.BigDecimal;
import java.util.List;

public class ListingMetricsWithMonthlyReportsPojo extends AbstractListingMetricsPojo {

    List<ListingMetricsWithMonthPojo> monthlyReports;

    public ListingMetricsWithMonthlyReportsPojo() {
        super();
    }

    public ListingMetricsWithMonthlyReportsPojo(long totalListingCount, long totalEBayListingCount, BigDecimal totalEBayListingPrice,
            BigDecimal averageEBayListingPrice, long totalAmazonListingCount, BigDecimal totalAmazonListingPrice,
            BigDecimal averageAmazonListingPrice, String bestListerEmailAddress, List<ListingMetricsWithMonthPojo> monthlyReports) {
        super(totalListingCount, totalEBayListingCount, totalEBayListingPrice, averageEBayListingPrice, totalAmazonListingCount,
                totalAmazonListingPrice, averageAmazonListingPrice, bestListerEmailAddress);
        this.monthlyReports = monthlyReports;
    }

    public List<ListingMetricsWithMonthPojo> getMonthlyReports() {
        return monthlyReports;
    }

    public void setMonthlyReports(List<ListingMetricsWithMonthPojo> monthlyReports) {
        this.monthlyReports = monthlyReports;
    }
}
