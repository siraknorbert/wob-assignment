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

import hu.wob.assignment.client.MarketplaceApiClient;
import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.converter.MarketplaceConverter;
import hu.wob.assignment.dto.MarketplaceDto;
import hu.wob.assignment.helper.PartitionedQueryHelper;
import hu.wob.assignment.model.MarketplaceModel;
import hu.wob.assignment.service.MarketplaceService;
import hu.wob.assignment.util.MarketplaceTestUtil;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SyncMarketplacesProcessorTest {

    @Mock
    private MarketplaceApiClient marketplaceApiClient;

    @Mock
    private PartitionedQueryHelper partitionedQueryHelper;

    @Mock
    private MarketplaceService marketplaceService;

    @Mock
    private MarketplaceConverter marketplaceConverter;

    @InjectMocks
    private SyncMarketplacesProcessor syncMarketplacesProcessor;

    @Test
    void testSyncMarketplaceListAllInvocation() throws Exception {
        // given
        Integer existingId = TestDataConstants.ID_1;
        Integer nonExistingId = TestDataConstants.ID_2;
        List<Object> mockExistingMarketplaceIdList = List.of(existingId);

        MarketplaceDto testDtoOne = MarketplaceTestUtil.createEbayMarketplaceDto(existingId);
        MarketplaceDto testDtoTwo = MarketplaceTestUtil.createEbayMarketplaceDto(nonExistingId);

        List<MarketplaceDto> mockApiResponseDtoList = new ArrayList<>();
        mockApiResponseDtoList.add(testDtoOne);
        mockApiResponseDtoList.add(testDtoTwo);

        List<MarketplaceModel> mockEntityList = new ArrayList<>();
        mockEntityList.add(MarketplaceTestUtil.createEbayMarketplaceEntity(nonExistingId));

        // when
        Mockito.when(marketplaceApiClient.getMarketplaceList()).thenReturn(mockApiResponseDtoList);
        Mockito.when(partitionedQueryHelper.partitionedQuery(Mockito.anyCollection(), Mockito.any())).thenReturn(mockExistingMarketplaceIdList);
        Mockito.when(marketplaceConverter.convertDtoCollectionToEntityList(Mockito.anyCollection())).thenReturn(mockEntityList);
        String result = syncMarketplacesProcessor.syncMarketplaceList();

        // then
        Mockito.verify(marketplaceApiClient).getMarketplaceList();
        Mockito.verify(partitionedQueryHelper).partitionedQuery(Mockito.anyCollection(), Mockito.any());
        Mockito.verify(marketplaceConverter).convertDtoCollectionToEntityList(Mockito.anyCollection());
        Mockito.verify(marketplaceService).saveMarketplaces(Mockito.anyCollection());

        String expectedMessage = MessageFormat.format("{0} fetched marketplace entities were saved, {1} were already existing", mockEntityList.size(),
                mockExistingMarketplaceIdList.size());
        Assertions.assertEquals(expectedMessage, result);
    }
}
