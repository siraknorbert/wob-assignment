package hu.wob.assignment.repository;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.model.MarketplaceModel;
import hu.wob.assignment.util.MarketplaceTestUtil;

@DataJpaTest
@ActiveProfiles("test")
class MarketplaceRepositoryTest {

    @Autowired
    private MarketplaceRepository marketplaceRepository;

    @AfterEach
    void deleteAllDatabaseTestData() {
        marketplaceRepository.deleteAll();
    }

    @Test
    void testFindIdsThatAreInGivenCollectionWhenTwoIdsAreInAndOneIsOut() {
        // given
        Integer expectedIdOne = TestDataConstants.ID_1;
        Integer expectedIdTwo = TestDataConstants.ID_2;
        Integer notExpectedId = TestDataConstants.ID_3;
        List<Integer> expectedIds = List.of(expectedIdOne, expectedIdTwo);

        MarketplaceModel entityOne = MarketplaceTestUtil.createEbayMarketplaceEntity(expectedIdOne);
        MarketplaceModel entityTwo = MarketplaceTestUtil.createEbayMarketplaceEntity(expectedIdTwo);
        MarketplaceModel entityThree = MarketplaceTestUtil.createEbayMarketplaceEntity(notExpectedId);
        marketplaceRepository.saveAll(List.of(entityOne, entityTwo, entityThree));

        // when
        List<Integer> resultIdList = marketplaceRepository.findIdsThatAreInGivenCollection(expectedIds);

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

        MarketplaceModel entityOne = MarketplaceTestUtil.createEbayMarketplaceEntity(expectedIdOne);
        MarketplaceModel entityTwo = MarketplaceTestUtil.createEbayMarketplaceEntity(expectedIdTwo);
        MarketplaceModel entityThree = MarketplaceTestUtil.createEbayMarketplaceEntity(notExpectedId);
        marketplaceRepository.saveAll(List.of(entityOne, entityTwo, entityThree));

        // when
        List<MarketplaceModel> resultEntityList = marketplaceRepository.findThatAreInGivenCollection(expectedIds);

        // then
        Assertions.assertEquals(expectedIds.size(), resultEntityList.size());
        for (MarketplaceModel resultEntity : resultEntityList) {
            Assertions.assertTrue(expectedIds.contains(resultEntity.getId()));
        }
    }
}
