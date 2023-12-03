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
import hu.wob.assignment.model.ListingStatusModel;
import hu.wob.assignment.repository.ListingStatusRepository;
import hu.wob.assignment.util.ListingStatusTestUtil;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ListingStatusServiceTest {

    @Mock
    private ListingStatusRepository listingStatusRepository;

    @InjectMocks
    private ListingStatusService listingStatusService;

    @Test
    void testFindIdsThatAreInGivenCollectionNoException() throws InvalidInputException {
        // given
        Integer idOne = TestDataConstants.ID_1;
        Integer idTwo = TestDataConstants.ID_2;
        List<Integer> expectedIdList = List.of(idOne, idTwo);

        // when
        Mockito.when(listingStatusRepository.findIdsThatAreInGivenCollection(expectedIdList)) //
                .thenReturn(expectedIdList);
        List<Integer> actualIdList = listingStatusService.findIdsThatAreInGivenCollection(expectedIdList);

        // then
        Assertions.assertEquals(expectedIdList.size(), actualIdList.size());
        for (Integer id : actualIdList) {
            Assertions.assertTrue(expectedIdList.contains(id));
        }
    }

    @Test
    void testFindIdsThatAreInGivenCollectionThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> listingStatusService.findIdsThatAreInGivenCollection(null));
    }

    @Test
    void testFindThatAreInGivenCollectionNoException() throws InvalidInputException {
        // given
        ListingStatusModel listingStatusOne = ListingStatusTestUtil.createActiveListingStatusEntity(TestDataConstants.ID_1);
        ListingStatusModel listingStatusTwo = ListingStatusTestUtil.createActiveListingStatusEntity(TestDataConstants.ID_2);
        List<ListingStatusModel> expectedEntityList = List.of(listingStatusOne, listingStatusTwo);
        List<Integer> expectedIdList = List.of(listingStatusOne.getId(), listingStatusTwo.getId());

        // when
        Mockito.when(listingStatusRepository.findThatAreInGivenCollection(expectedIdList)) //
                .thenReturn(expectedEntityList);
        List<ListingStatusModel> actualEntityList = listingStatusService.findThatAreInGivenCollection(expectedIdList);

        // then
        Assertions.assertEquals(expectedEntityList.size(), actualEntityList.size());
        for (ListingStatusModel entity : actualEntityList) {
            Assertions.assertTrue(expectedIdList.contains(entity.getId()));
        }
    }

    @Test
    void testFindThatAreInGivenCollectionThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> listingStatusService.findThatAreInGivenCollection(null));
    }

    @Test
    void testSaveListingStatusesRepositorySaveAllInvocation() {
        // given
        ListingStatusModel entityOne = ListingStatusTestUtil.createActiveListingStatusEntity(TestDataConstants.ID_1);
        ListingStatusModel entityTwo = ListingStatusTestUtil.createActiveListingStatusEntity(TestDataConstants.ID_2);
        List<ListingStatusModel> inputList = List.of(entityOne, entityTwo);

        // when
        listingStatusService.saveListingStatuses(inputList);

        // then
        Mockito.verify(listingStatusRepository, Mockito.times(1)).saveAll(inputList);
    }
}
