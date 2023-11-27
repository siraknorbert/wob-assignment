package hu.wob.assignment.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.ListingModel;
import hu.wob.assignment.repository.ListingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

/**
 * Service that handles dao connection to handle {@link ListingModel} entities in database.
 **/
@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> findIdsThatAreInGivenCollection(Collection<String> listingIds) throws InvalidInputException {
        if (listingIds == null) {
            throw new InvalidInputException("listingIds parameter is null");
        }
        return listingRepository.findIdsThatAreInGivenCollection(listingIds);
    }

    public void saveListings(Collection<ListingModel> listings) {
        listingRepository.saveAll(listings);
    }

    public Long getTotalListingCount() {
        return listingRepository.count();
    }

    public Map<String, Long> countByMarketplaceForEachMarketplaceName() {
        return listingRepository.countByMarketplaceForEachMarketplaceName().stream().collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    public Map<String, BigDecimal> findAllTotalListingPriceByMarketplaceName() {
        return listingRepository.findAllTotalListingPriceByMarketplaceName().stream().collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    public Map<String, BigDecimal> findAllAverageListingPriceByMarketplaceName() {
        return listingRepository.findAllAverageListingPriceByMarketplaceName().stream().collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    public Optional<String> findOptionalBestListerEmailAddress() {
        String jpqlQuery = "SELECT l.ownerEmailAddress FROM ListingModel l GROUP BY l.ownerEmailAddress ORDER BY COUNT(l.id) DESC";
        List<String> result = entityManager.createQuery(jpqlQuery, String.class).setMaxResults(1).getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<String> findDistinctYearMonthCombinations() {
        return listingRepository.findDistinctYearMonthCombinations();
    }

    public Long findTotalListingCountForMonth(String yearMonth) throws InvalidInputException {
        if (StringUtils.isBlank(yearMonth)) {
            throw new InvalidInputException("yearMonth parameter is blank");
        }
        return listingRepository.findTotalListingCountForMonth(yearMonth);
    }

    public Map<String, Long> findCountOfListingsByMarketplaceNameForMonth(String yearMonth) throws InvalidInputException {
        if (StringUtils.isBlank(yearMonth)) {
            throw new InvalidInputException("yearMonth parameter is blank");
        }
        return listingRepository.findCountOfListingsByMarketplaceNameForMonth(yearMonth).stream()
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    public Map<String, BigDecimal> findTotalListingPriceByMarketplaceNameForMonth(String yearMonth) throws InvalidInputException {
        if (StringUtils.isBlank(yearMonth)) {
            throw new InvalidInputException("yearMonth parameter is blank");
        }
        return listingRepository.findTotalListingPriceByMarketplaceNameForMonth(yearMonth).stream()
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    public Map<String, BigDecimal> findAverageListingPriceByMarketplaceNameForMonth(String yearMonth) throws InvalidInputException {
        if (StringUtils.isBlank(yearMonth)) {
            throw new InvalidInputException("yearMonth parameter is blank");
        }
        return listingRepository.findAverageListingPriceByMarketplaceNameForMonth(yearMonth).stream()
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    public Optional<String> findOptionalBestListerEmailAddressForMonth(String yearMonth) {
        String jpqlQuery = "SELECT l.ownerEmailAddress FROM ListingModel l "
                + "WHERE CONCAT(YEAR(l.uploadTime), '-', MONTH(l.uploadTime)) = :yearMonth "
                + "GROUP BY l.ownerEmailAddress ORDER BY COUNT(l.id) DESC";

        List<String> result = entityManager.createQuery(jpqlQuery, String.class).setParameter("yearMonth", yearMonth).setMaxResults(1)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
