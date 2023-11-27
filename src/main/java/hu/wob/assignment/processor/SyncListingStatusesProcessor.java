package hu.wob.assignment.processor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import hu.wob.assignment.client.ListingStatusApiClient;
import hu.wob.assignment.converter.ListingStatusConverter;
import hu.wob.assignment.dto.ListingStatusDto;
import hu.wob.assignment.helper.PartitionedQueryHelper;
import hu.wob.assignment.model.ListingStatusModel;
import hu.wob.assignment.service.ListingStatusService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SyncListingStatusesProcessor {

    private final ListingStatusApiClient listingStatusApiClient;
    private final PartitionedQueryHelper partitionedQueryHelper;
    private final ListingStatusService listingStatusService;
    private final ListingStatusConverter listingStatusConverter;

    public String syncListingStatusList() throws Exception {
        List<ListingStatusDto> dtoList = listingStatusApiClient.getListingStatusList();

        Map<Integer, ListingStatusDto> listingStatusByIdMap = dtoList.stream().collect(Collectors.toMap(ListingStatusDto::getId, dto -> dto));
        List<Integer> listingStatusIdList = new ArrayList<>(listingStatusByIdMap.keySet());

        List<Integer> existingListingStatusIdList = partitionedQueryHelper.partitionedQuery(listingStatusIdList,
                listingStatusService::findIdsThatAreInGivenCollection);
        existingListingStatusIdList.forEach(listingStatusByIdMap.keySet()::remove);

        List<ListingStatusModel> entityList = listingStatusConverter.convertDtoCollectionToEntityList(listingStatusByIdMap.values());
        listingStatusService.saveListingStatuses(entityList);

        return MessageFormat.format("{0} fetched listingStatus entities were saved, {1} were already existing", entityList.size(),
                existingListingStatusIdList.size());
    }
}
