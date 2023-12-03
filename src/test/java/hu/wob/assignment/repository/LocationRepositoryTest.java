package hu.wob.assignment.repository;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.model.LocationModel;
import hu.wob.assignment.util.LocationTestUtil;

@DataJpaTest
@ActiveProfiles("test")
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @AfterEach
    void deleteAllDatabaseTestData() {
        locationRepository.deleteAll();
    }

    @Test
    void testFindIdsThatAreInGivenCollectionWhenTwoIdsAreInAndOneIsOut() {
        // given
        String expectedIdOne = TestDataConstants.UUID_1;
        String expectedIdTwo = TestDataConstants.UUID_2;
        String notExpectedId = TestDataConstants.UUID_3;
        List<String> expectedIds = List.of(expectedIdOne, expectedIdTwo);

        LocationModel entityOne = LocationTestUtil.createLocationEntity(expectedIdOne);
        LocationModel entityTwo = LocationTestUtil.createLocationEntity(expectedIdTwo);
        LocationModel entityThree = LocationTestUtil.createLocationEntity(notExpectedId);
        locationRepository.saveAll(List.of(entityOne, entityTwo, entityThree));

        // when
        List<String> resultIdList = locationRepository.findIdsThatAreInGivenCollection(expectedIds);

        // then
        Assertions.assertEquals(expectedIds.size(), resultIdList.size());
        for (String id : resultIdList) {
            Assertions.assertTrue(expectedIds.contains(id));
        }
    }

    @Test
    void testFindThatAreInGivenCollectionWhenTwoIdsAreInAndOneIsOut() {
        // given
        String expectedIdOne = TestDataConstants.UUID_1;
        String expectedIdTwo = TestDataConstants.UUID_2;
        String notExpectedId = TestDataConstants.UUID_3;
        List<String> expectedIds = List.of(expectedIdOne, expectedIdTwo);

        LocationModel entityOne = LocationTestUtil.createLocationEntity(expectedIdOne);
        LocationModel entityTwo = LocationTestUtil.createLocationEntity(expectedIdTwo);
        LocationModel entityThree = LocationTestUtil.createLocationEntity(notExpectedId);
        locationRepository.saveAll(List.of(entityOne, entityTwo, entityThree));

        // when
        List<LocationModel> resultEntityList = locationRepository.findThatAreInGivenCollection(expectedIds);

        // then
        Assertions.assertEquals(expectedIds.size(), resultEntityList.size());
        for (LocationModel resultEntity : resultEntityList) {
            Assertions.assertTrue(expectedIds.contains(resultEntity.getId()));
        }
    }
}
