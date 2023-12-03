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
import hu.wob.assignment.model.MarketplaceModel;
import hu.wob.assignment.repository.MarketplaceRepository;
import hu.wob.assignment.util.MarketplaceTestUtil;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MarketplaceServiceTest {

    @Mock
    private MarketplaceRepository marketplaceRepository;

    @InjectMocks
    private MarketplaceService marketplaceService;

    @Test
    void testFindIdsThatAreInGivenCollectionNoException() throws InvalidInputException {
        // given
        Integer idOne = TestDataConstants.ID_1;
        Integer idTwo = TestDataConstants.ID_2;
        List<Integer> expectedIdList = List.of(idOne, idTwo);

        // when
        Mockito.when(marketplaceRepository.findIdsThatAreInGivenCollection(expectedIdList)) //
                .thenReturn(expectedIdList);
        List<Integer> actualIdList = marketplaceService.findIdsThatAreInGivenCollection(expectedIdList);

        // then
        Assertions.assertEquals(expectedIdList.size(), actualIdList.size());
        for (Integer id : actualIdList) {
            Assertions.assertTrue(expectedIdList.contains(id));
        }
    }

    @Test
    void testFindIdsThatAreInGivenCollectionThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> marketplaceService.findIdsThatAreInGivenCollection(null));
    }

    @Test
    void testFindThatAreInGivenCollectionNoException() throws InvalidInputException {
        // given
        MarketplaceModel marketplaceOne = MarketplaceTestUtil.createEbayMarketplaceEntity(TestDataConstants.ID_1);
        MarketplaceModel marketplaceTwo = MarketplaceTestUtil.createEbayMarketplaceEntity(TestDataConstants.ID_2);
        List<MarketplaceModel> expectedEntityList = List.of(marketplaceOne, marketplaceTwo);
        List<Integer> expectedIdList = List.of(marketplaceOne.getId(), marketplaceTwo.getId());

        // when
        Mockito.when(marketplaceRepository.findThatAreInGivenCollection(expectedIdList)) //
                .thenReturn(expectedEntityList);
        List<MarketplaceModel> actualEntityList = marketplaceService.findThatAreInGivenCollection(expectedIdList);

        // then
        Assertions.assertEquals(expectedEntityList.size(), actualEntityList.size());
        for (MarketplaceModel entity : actualEntityList) {
            Assertions.assertTrue(expectedIdList.contains(entity.getId()));
        }
    }

    @Test
    void testFindThatAreInGivenCollectionThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> marketplaceService.findThatAreInGivenCollection(null));
    }

    @Test
    void testSaveMarketplacesRepositorySaveAllInvocation() {
        // given
        MarketplaceModel entityOne = MarketplaceTestUtil.createEbayMarketplaceEntity(TestDataConstants.ID_1);
        MarketplaceModel entityTwo = MarketplaceTestUtil.createEbayMarketplaceEntity(TestDataConstants.ID_2);
        List<MarketplaceModel> inputList = List.of(entityOne, entityTwo);

        // when
        marketplaceService.saveMarketplaces(inputList);

        // then
        Mockito.verify(marketplaceRepository, Mockito.times(1)).saveAll(inputList);
    }
}
