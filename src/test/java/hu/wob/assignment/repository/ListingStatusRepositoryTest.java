package hu.wob.assignment.repository;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.model.ListingStatusModel;
import hu.wob.assignment.util.ListingStatusTestUtil;

@DataJpaTest
@ActiveProfiles("test")
class ListingStatusRepositoryTest {

    @Autowired
    private ListingStatusRepository listingStatusRepository;

    @AfterEach
    void deleteAllDatabaseTestData() {
        listingStatusRepository.deleteAll();
    }

    @Test
    void testFindIdsThatAreInGivenCollectionWhenTwoIdsAreInAndOneIsOut() {
        // given
        Integer expectedIdOne = TestDataConstants.ID_1;
        Integer expectedIdTwo = TestDataConstants.ID_2;
        Integer notExpectedId = TestDataConstants.ID_3;
        List<Integer> expectedIds = List.of(expectedIdOne, expectedIdTwo);

        ListingStatusModel entityOne = ListingStatusTestUtil.createActiveListingStatusEntity(expectedIdOne);
        ListingStatusModel entityTwo = ListingStatusTestUtil.createActiveListingStatusEntity(expectedIdTwo);
        ListingStatusModel entityThree = ListingStatusTestUtil.createActiveListingStatusEntity(notExpectedId);
        listingStatusRepository.saveAll(List.of(entityOne, entityTwo, entityThree));

        // when
        List<Integer> resultIdList = listingStatusRepository.findIdsThatAreInGivenCollection(expectedIds);

        // then
        Assertions.assertEquals(expectedIds.size(), resultIdList.size());
        for (Integer id : resultIdList) {
            Assertions.assertTrue(expectedIds.contains(id));
        }
    }

    @Test
    void testFindThatAreInGivenCollectionWhenTwoIdsAreInAndOneIsOut() {
        // given
        Integer expectedIdOne = TestDataConstants.ID_1;
        Integer expectedIdTwo = TestDataConstants.ID_2;
        Integer notExpectedId = TestDataConstants.ID_3;
        List<Integer> expectedIds = List.of(expectedIdOne, expectedIdTwo);

        ListingStatusModel entityOne = ListingStatusTestUtil.createActiveListingStatusEntity(expectedIdOne);
        ListingStatusModel entityTwo = ListingStatusTestUtil.createActiveListingStatusEntity(expectedIdTwo);
        ListingStatusModel entityThree = ListingStatusTestUtil.createActiveListingStatusEntity(notExpectedId);
        listingStatusRepository.saveAll(List.of(entityOne, entityTwo, entityThree));

        // when
        List<ListingStatusModel> resultEntityList = listingStatusRepository.findThatAreInGivenCollection(expectedIds);

        // then
        Assertions.assertEquals(expectedIds.size(), resultEntityList.size());
        for (ListingStatusModel resultEntity : resultEntityList) {
            Assertions.assertTrue(expectedIds.contains(resultEntity.getId()));
        }
    }
}
