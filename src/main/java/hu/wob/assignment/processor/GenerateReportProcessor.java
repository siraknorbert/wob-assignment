package hu.wob.assignment.processor;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import hu.wob.assignment.client.FtpClient;
import hu.wob.assignment.enumeration.MarketplaceNameEnum;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.helper.JsonMappingHelper;
import hu.wob.assignment.pojo.ListingMetricsWithMonthPojo;
import hu.wob.assignment.pojo.ListingMetricsWithMonthlyReportsPojo;
import hu.wob.assignment.service.ListingService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenerateReportProcessor {

    private final ListingService listingService;
    private final JsonMappingHelper jsonMappingHelper;
    private final FtpClient ftpClient;

    public String generateTotalReport() throws Exception {
        ListingMetricsWithMonthlyReportsPojo listingMetrics = fetchListingMetrics();
        String jsonContent = jsonMappingHelper.mapToJSON(listingMetrics);
        String fileName = ftpClient.uploadToFTP(jsonContent, "totalReport");

        return MessageFormat.format("Total JSON report file with name [{0}] was successfully created and uploaded to FTP server", fileName);
    }

    private ListingMetricsWithMonthlyReportsPojo fetchListingMetrics() throws InvalidInputException {
        ListingMetricsWithMonthlyReportsPojo listingMetrics = new ListingMetricsWithMonthlyReportsPojo();

        // I could have fetched the SUM and AVG data in one query, but with a large number of records, it could cause performance issues or timeout.
        Long totalListingCount = listingService.getTotalListingCount();
        Optional<String> optionalBestListerEmailAddress = listingService.findOptionalBestListerEmailAddress();
        Map<String, Long> listingCountByMarketplaceName = listingService.countByMarketplaceForEachMarketplaceName();
        Map<String, BigDecimal> totalListingPriceByMarketplaceName = listingService.findAllTotalListingPriceByMarketplaceName();
        Map<String, BigDecimal> averageListingPriceByMarketplaceName = listingService.findAllAverageListingPriceByMarketplaceName();
        List<ListingMetricsWithMonthPojo> monthlyMonths = fetchListingMetricsPerMonths();

        listingMetrics.setTotalListingCount(totalListingCount);
        listingMetrics.setBestListerEmailAddress(optionalBestListerEmailAddress.orElse(null));
        listingMetrics.setTotalEBayListingCount(listingCountByMarketplaceName.getOrDefault(MarketplaceNameEnum.EBAY, 0L));
        listingMetrics.setTotalEBayListingPrice(totalListingPriceByMarketplaceName.getOrDefault(MarketplaceNameEnum.EBAY, BigDecimal.ZERO));
        listingMetrics.setAverageEBayListingPrice(averageListingPriceByMarketplaceName.getOrDefault(MarketplaceNameEnum.EBAY, BigDecimal.ZERO));
        listingMetrics.setTotalAmazonListingCount(listingCountByMarketplaceName.getOrDefault(MarketplaceNameEnum.AMAZON, 0L));
        listingMetrics.setTotalAmazonListingPrice(totalListingPriceByMarketplaceName.getOrDefault(MarketplaceNameEnum.AMAZON, BigDecimal.ZERO));
        listingMetrics.setAverageAmazonListingPrice(averageListingPriceByMarketplaceName.getOrDefault(MarketplaceNameEnum.AMAZON, BigDecimal.ZERO));
        listingMetrics.setMonthlyReports(monthlyMonths);

        return listingMetrics;
    }

    private List<ListingMetricsWithMonthPojo> fetchListingMetricsPerMonths() throws InvalidInputException {
        List<ListingMetricsWithMonthPojo> listingMetricsWithMonths = new ArrayList<>();

        List<String> distinctYearMonths = listingService.findDistinctYearMonthCombinations();
        for (String yearMonth : distinctYearMonths) {
            ListingMetricsWithMonthPojo monthlyReport = fetchListingMetricsOfMonth(yearMonth);
            listingMetricsWithMonths.add(monthlyReport);
        }

        return listingMetricsWithMonths;
    }

    private ListingMetricsWithMonthPojo fetchListingMetricsOfMonth(String month) throws InvalidInputException {
        ListingMetricsWithMonthPojo listingMetrics = new ListingMetricsWithMonthPojo();

        Long totalListingCount = listingService.findTotalListingCountForMonth(month);
        Optional<String> optionalBestListerEmailAddress = listingService.findOptionalBestListerEmailAddressForMonth(month);
        Map<String, Long> listingCountByMarketplaceName = listingService.findCountOfListingsByMarketplaceNameForMonth(month);
        Map<String, BigDecimal> totalListingPriceByMarketplaceName = listingService.findTotalListingPriceByMarketplaceNameForMonth(month);
        Map<String, BigDecimal> averageListingPriceByMarketplaceName = listingService.findAverageListingPriceByMarketplaceNameForMonth(month);

        listingMetrics.setTotalListingCount(totalListingCount);
        listingMetrics.setBestListerEmailAddress(optionalBestListerEmailAddress.orElse(null));
        listingMetrics.setTotalEBayListingCount(listingCountByMarketplaceName.getOrDefault(MarketplaceNameEnum.EBAY, 0L));
        listingMetrics.setTotalEBayListingPrice(totalListingPriceByMarketplaceName.getOrDefault(MarketplaceNameEnum.EBAY, BigDecimal.ZERO));
        listingMetrics.setAverageEBayListingPrice(averageListingPriceByMarketplaceName.getOrDefault(MarketplaceNameEnum.EBAY, BigDecimal.ZERO));
        listingMetrics.setTotalAmazonListingCount(listingCountByMarketplaceName.getOrDefault(MarketplaceNameEnum.AMAZON, 0L));
        listingMetrics.setTotalAmazonListingPrice(totalListingPriceByMarketplaceName.getOrDefault(MarketplaceNameEnum.AMAZON, BigDecimal.ZERO));
        listingMetrics.setAverageAmazonListingPrice(averageListingPriceByMarketplaceName.getOrDefault(MarketplaceNameEnum.AMAZON, BigDecimal.ZERO));
        listingMetrics.setMonth(month);

        return listingMetrics;
    }
}
