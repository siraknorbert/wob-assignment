package hu.wob.assignment.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.ListingStatusModel;
import hu.wob.assignment.repository.ListingStatusRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service that handles dao connection to handle {@link ListingStatusModel} entities in database.
 **/
@Service
@RequiredArgsConstructor
public class ListingStatusService {

    private final ListingStatusRepository listingStatusRepository;

    public List<Integer> findIdsThatAreInGivenCollection(Collection<Integer> listingStatusIds) throws InvalidInputException {
        if (listingStatusIds == null) {
            throw new InvalidInputException("listingStatusIds parameter is null");
        }
        return listingStatusRepository.findIdsThatAreInGivenCollection(listingStatusIds);
    }

    public List<ListingStatusModel> findThatAreInGivenCollection(Collection<Integer> listingStatusIds) throws InvalidInputException {
        if (listingStatusIds == null) {
            throw new InvalidInputException("listingStatusIds parameter is null");
        }
        return listingStatusRepository.findThatAreInGivenCollection(listingStatusIds);
    }

    public void saveListingStatuses(Collection<ListingStatusModel> listingStatuses) {
        listingStatusRepository.saveAll(listingStatuses);
    }
}
