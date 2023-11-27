package hu.wob.assignment.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.wob.assignment.model.ListingModel;

/**
 * Repository that handles data accessing and persisting of {@link ListingModel} entities in database.
 */
@Repository
public interface ListingRepository extends JpaRepository<ListingModel, String> {

    @Query("SELECT l.id FROM ListingModel l WHERE l.id IN :listingIds")
    List<String> findIdsThatAreInGivenCollection(@Param("listingIds") Collection<String> listingIds);

    @Query("SELECT new org.apache.commons.lang3.tuple.ImmutablePair(l.marketplace.marketplaceName, COUNT(l.id)) "
            + "FROM ListingModel l GROUP BY l.marketplace.marketplaceName")
    List<Pair<String, Long>> countByMarketplaceForEachMarketplaceName();

    @Query("SELECT new org.apache.commons.lang3.tuple.ImmutablePair(l.marketplace.marketplaceName, SUM(l.listingPrice)) "
            + "FROM ListingModel l GROUP BY l.marketplace.marketplaceName")
    List<Pair<String, BigDecimal>> findAllTotalListingPriceByMarketplaceName();

    @Query("SELECT new org.apache.commons.lang3.tuple.ImmutablePair(l.marketplace.marketplaceName, CAST(AVG(l.listingPrice) AS java.math.BigDecimal)) "
            + "FROM ListingModel l GROUP BY l.marketplace.marketplaceName")
    List<Pair<String, BigDecimal>> findAllAverageListingPriceByMarketplaceName();

    @Query("SELECT DISTINCT CONCAT(YEAR(l.uploadTime), '-', MONTH(l.uploadTime)) "
            + "FROM ListingModel l ORDER BY CONCAT(YEAR(l.uploadTime), '-', MONTH(l.uploadTime))")
    List<String> findDistinctYearMonthCombinations();

    @Query("SELECT COUNT(l.id) FROM ListingModel l WHERE CONCAT(YEAR(l.uploadTime), '-', MONTH(l.uploadTime)) = :yearMonth")
    Long findTotalListingCountForMonth(@Param("yearMonth") String yearMonth);

    @Query("SELECT new org.apache.commons.lang3.tuple.ImmutablePair(l.marketplace.marketplaceName, COUNT(l.listingPrice)) "
            + "FROM ListingModel l WHERE CONCAT(YEAR(l.uploadTime), '-', MONTH(l.uploadTime)) = :yearMonth GROUP BY l.marketplace.marketplaceName")
    List<Pair<String, Long>> findCountOfListingsByMarketplaceNameForMonth(@Param("yearMonth") String yearMonth);

    @Query("SELECT new org.apache.commons.lang3.tuple.ImmutablePair(l.marketplace.marketplaceName, SUM(l.listingPrice)) "
            + "FROM ListingModel l WHERE CONCAT(YEAR(l.uploadTime), '-', MONTH(l.uploadTime)) = :yearMonth GROUP BY l.marketplace.marketplaceName")
    List<Pair<String, BigDecimal>> findTotalListingPriceByMarketplaceNameForMonth(@Param("yearMonth") String yearMonth);

    @Query("SELECT new org.apache.commons.lang3.tuple.ImmutablePair(l.marketplace.marketplaceName, CAST(AVG(l.listingPrice) AS java.math.BigDecimal)) "
            + "FROM ListingModel l WHERE CONCAT(YEAR(l.uploadTime), '-', MONTH(l.uploadTime)) = :yearMonth GROUP BY l.marketplace.marketplaceName")
    List<Pair<String, BigDecimal>> findAverageListingPriceByMarketplaceNameForMonth(@Param("yearMonth") String yearMonth);
}
