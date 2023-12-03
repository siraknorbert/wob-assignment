package hu.wob.assignment.processor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.client.ListingStatusApiClient;
import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.converter.ListingStatusConverter;
import hu.wob.assignment.dto.ListingStatusDto;
import hu.wob.assignment.helper.PartitionedQueryHelper;
import hu.wob.assignment.model.ListingStatusModel;
import hu.wob.assignment.service.ListingStatusService;
import hu.wob.assignment.util.ListingStatusTestUtil;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SyncListingStatusesProcessorTest {

    @Mock
    private ListingStatusApiClient listingStatusApiClient;

    @Mock
    private PartitionedQueryHelper partitionedQueryHelper;

    @Mock
    private ListingStatusService listingStatusService;

    @Mock
    private ListingStatusConverter listingStatusConverter;

    @InjectMocks
    private SyncListingStatusesProcessor syncListingStatusesProcessor;

    @Test
    void testSyncListingStatusListAllInvocation() throws Exception {
        // given
        Integer existingId = TestDataConstants.ID_1;
        Integer nonExistingId = TestDataConstants.ID_2;
        List<Object> mockExistingListingStatusIdList = List.of(existingId);

        ListingStatusDto testDtoOne = ListingStatusTestUtil.createActiveListingStatusDto(existingId);
        ListingStatusDto testDtoTwo = ListingStatusTestUtil.createActiveListingStatusDto(nonExistingId);

        List<ListingStatusDto> mockApiResponseDtoList = new ArrayList<>();
        mockApiResponseDtoList.add(testDtoOne);
        mockApiResponseDtoList.add(testDtoTwo);

        List<ListingStatusModel> mockEntityList = new ArrayList<>();
        mockEntityList.add(ListingStatusTestUtil.createActiveListingStatusEntity(nonExistingId));

        // when
        Mockito.when(listingStatusApiClient.getListingStatusList()).thenReturn(mockApiResponseDtoList);
        Mockito.when(partitionedQueryHelper.partitionedQuery(Mockito.anyCollection(), Mockito.any())).thenReturn(mockExistingListingStatusIdList);
        Mockito.when(listingStatusConverter.convertDtoCollectionToEntityList(Mockito.anyCollection())).thenReturn(mockEntityList);
        String result = syncListingStatusesProcessor.syncListingStatusList();

        // then
        Mockito.verify(listingStatusApiClient).getListingStatusList();
        Mockito.verify(partitionedQueryHelper).partitionedQuery(Mockito.anyCollection(), Mockito.any());
        Mockito.verify(listingStatusConverter).convertDtoCollectionToEntityList(Mockito.anyCollection());
        Mockito.verify(listingStatusService).saveListingStatuses(Mockito.anyCollection());

        String expectedMessage = MessageFormat.format("{0} fetched listingStatus entities were saved, {1} were already existing",
                mockEntityList.size(), mockExistingListingStatusIdList.size());
        Assertions.assertEquals(expectedMessage, result);
    }
}
