package hu.wob.assignment.processor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import hu.wob.assignment.client.LocationApiClient;
import hu.wob.assignment.converter.LocationConverter;
import hu.wob.assignment.dto.LocationDto;
import hu.wob.assignment.helper.PartitionedQueryHelper;
import hu.wob.assignment.model.LocationModel;
import hu.wob.assignment.service.LocationService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SyncLocationsProcessor {

    private final LocationApiClient locationApiClient;
    private final PartitionedQueryHelper partitionedQueryHelper;
    private final LocationService locationService;
    private final LocationConverter locationConverter;

    public String syncLocationList() throws Exception {
        List<LocationDto> dtoList = locationApiClient.getLocationList();

        Map<String, LocationDto> locationByIdMap = dtoList.stream().collect(Collectors.toMap(LocationDto::getId, dto -> dto));
        List<String> locationIdList = new ArrayList<>(locationByIdMap.keySet());

        List<String> existingLocationIdList = partitionedQueryHelper.partitionedQuery(locationIdList,
                locationService::findIdsThatAreInGivenCollection);
        existingLocationIdList.forEach(locationByIdMap.keySet()::remove);

        List<LocationModel> entityList = locationConverter.convertDtoCollectionToEntityList(locationByIdMap.values());
        locationService.saveLocations(entityList);

        return MessageFormat.format("{0} fetched location entities were saved, {1} were already existing", entityList.size(),
                existingLocationIdList.size());
    }
}
