package hu.wob.assignment.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.LocationModel;
import hu.wob.assignment.repository.LocationRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service that handles dao connection to handle {@link LocationModel} entities in database.
 **/
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<String> findIdsThatAreInGivenCollection(Collection<String> locationIds) throws InvalidInputException {
        if (locationIds == null) {
            throw new InvalidInputException("locationIds parameter is null");
        }
        return locationRepository.findIdsThatAreInGivenCollection(locationIds);
    }

    public List<LocationModel> findThatAreInGivenCollection(Collection<String> locationIds) throws InvalidInputException {
        if (locationIds == null) {
            throw new InvalidInputException("locationIds parameter is null");
        }
        return locationRepository.findThatAreInGivenCollection(locationIds);
    }

    public void saveLocations(Collection<LocationModel> locations) {
        locationRepository.saveAll(locations);
    }
}
