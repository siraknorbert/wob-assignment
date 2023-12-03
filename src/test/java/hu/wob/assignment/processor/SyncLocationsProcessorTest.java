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

import hu.wob.assignment.client.LocationApiClient;
import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.converter.LocationConverter;
import hu.wob.assignment.dto.LocationDto;
import hu.wob.assignment.helper.PartitionedQueryHelper;
import hu.wob.assignment.model.LocationModel;
import hu.wob.assignment.service.LocationService;
import hu.wob.assignment.util.LocationTestUtil;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SyncLocationsProcessorTest {

    @Mock
    private LocationApiClient locationApiClient;

    @Mock
    private PartitionedQueryHelper partitionedQueryHelper;

    @Mock
    private LocationService locationService;

    @Mock
    private LocationConverter locationConverter;

    @InjectMocks
    private SyncLocationsProcessor syncLocationsProcessor;

    @Test
    void testSyncLocationListAllInvocation() throws Exception {
        // given
        String existingId = TestDataConstants.UUID_1;
        String nonExistingId = TestDataConstants.UUID_2;
        List<Object> mockExistingLocationIdList = List.of(existingId);

        LocationDto testDtoOne = LocationTestUtil.createLocationDto(existingId);
        LocationDto testDtoTwo = LocationTestUtil.createLocationDto(nonExistingId);

        List<LocationDto> mockApiResponseDtoList = new ArrayList<>();
        mockApiResponseDtoList.add(testDtoOne);
        mockApiResponseDtoList.add(testDtoTwo);

        List<LocationModel> mockEntityList = new ArrayList<>();
        mockEntityList.add(LocationTestUtil.createLocationEntity(nonExistingId));

        // when
        Mockito.when(locationApiClient.getLocationList()).thenReturn(mockApiResponseDtoList);
        Mockito.when(partitionedQueryHelper.partitionedQuery(Mockito.anyCollection(), Mockito.any())).thenReturn(mockExistingLocationIdList);
        Mockito.when(locationConverter.convertDtoCollectionToEntityList(Mockito.anyCollection())).thenReturn(mockEntityList);
        String result = syncLocationsProcessor.syncLocationList();

        // then
        Mockito.verify(locationApiClient).getLocationList();
        Mockito.verify(partitionedQueryHelper).partitionedQuery(Mockito.anyCollection(), Mockito.any());
        Mockito.verify(locationConverter).convertDtoCollectionToEntityList(Mockito.anyCollection());
        Mockito.verify(locationService).saveLocations(Mockito.anyCollection());

        String expectedMessage = MessageFormat.format("{0} fetched location entities were saved, {1} were already existing", mockEntityList.size(),
                mockExistingLocationIdList.size());
        Assertions.assertEquals(expectedMessage, result);
    }
}
