package hu.wob.assignment.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.MarketplaceModel;
import hu.wob.assignment.repository.MarketplaceRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service that handles dao connection to handle {@link MarketplaceModel} entities in database.
 **/
@Service
@RequiredArgsConstructor
public class MarketplaceService {

    private final MarketplaceRepository marketplaceRepository;

    public List<Integer> findIdsThatAreInGivenCollection(Collection<Integer> marketplaceIds) throws InvalidInputException {
        if (marketplaceIds == null) {
            throw new InvalidInputException("marketplaceIds parameter is null");
        }
        return marketplaceRepository.findIdsThatAreInGivenCollection(marketplaceIds);
    }

    public List<MarketplaceModel> findThatAreInGivenCollection(Collection<Integer> marketplaceIds) throws InvalidInputException {
        if (marketplaceIds == null) {
            throw new InvalidInputException("marketplaceIds parameter is null");
        }
        return marketplaceRepository.findThatAreInGivenCollection(marketplaceIds);
    }

    public void saveMarketplaces(Collection<MarketplaceModel> marketplaces) {
        marketplaceRepository.saveAll(marketplaces);
    }
}
