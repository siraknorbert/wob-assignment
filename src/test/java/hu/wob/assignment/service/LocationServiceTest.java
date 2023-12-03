package hu.wob.assignment.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.LocationModel;
import hu.wob.assignment.repository.LocationRepository;
import hu.wob.assignment.util.LocationTestUtil;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    void testFindIdsThatAreInGivenCollectionNoException() throws InvalidInputException {
        // given
        String idOne = TestDataConstants.UUID_1;
        String idTwo = TestDataConstants.UUID_2;
        List<String> expectedIdList = List.of(idOne, idTwo);

        // when
        Mockito.when(locationRepository.findIdsThatAreInGivenCollection(expectedIdList)) //
                .thenReturn(expectedIdList);
        List<String> actualIdList = locationService.findIdsThatAreInGivenCollection(expectedIdList);

        // then
        Assertions.assertEquals(expectedIdList.size(), actualIdList.size());
        for (String id : actualIdList) {
            Assertions.assertTrue(expectedIdList.contains(id));
        }
    }

    @Test
    void testFindIdsThatAreInGivenCollectionThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> locationService.findIdsThatAreInGivenCollection(null));
    }

    @Test
    void testFindThatAreInGivenCollectionNoException() throws InvalidInputException {
        // given
        LocationModel locationOne = LocationTestUtil.createLocationEntity(TestDataConstants.UUID_1);
        LocationModel locationTwo = LocationTestUtil.createLocationEntity(TestDataConstants.UUID_2);
        List<LocationModel> expectedEntityList = List.of(locationOne, locationTwo);
        List<String> expectedIdList = List.of(locationOne.getId(), locationTwo.getId());

        // when
        Mockito.when(locationRepository.findThatAreInGivenCollection(expectedIdList)) //
                .thenReturn(expectedEntityList);
        List<LocationModel> actualEntityList = locationService.findThatAreInGivenCollection(expectedIdList);

        // then
        Assertions.assertEquals(expectedEntityList.size(), actualEntityList.size());
        for (LocationModel entity : actualEntityList) {
            Assertions.assertTrue(expectedIdList.contains(entity.getId()));
        }
    }

    @Test
    void testFindThatAreInGivenCollectionThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> locationService.findThatAreInGivenCollection(null));
    }

    @Test
    void testSaveLocationsRepositorySaveAllInvocation() {
        // given
        LocationModel entityOne = LocationTestUtil.createLocationEntity(TestDataConstants.UUID_1);
        LocationModel entityTwo = LocationTestUtil.createLocationEntity(TestDataConstants.UUID_2);
        List<LocationModel> inputList = List.of(entityOne, entityTwo);

        // when
        locationService.saveLocations(inputList);

        // then
        Mockito.verify(locationRepository, Mockito.times(1)).saveAll(inputList);
    }
}
