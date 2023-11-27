package hu.wob.assignment.processor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import hu.wob.assignment.client.ListingApiClient;
import hu.wob.assignment.converter.ListingConverter;
import hu.wob.assignment.dto.ListingDto;
import hu.wob.assignment.helper.ListingDataLoggingHelper;
import hu.wob.assignment.helper.ListingDataMappingHelper;
import hu.wob.assignment.helper.PartitionedQueryHelper;
import hu.wob.assignment.model.ListingModel;
import hu.wob.assignment.model.ListingStatusModel;
import hu.wob.assignment.model.LocationModel;
import hu.wob.assignment.model.MarketplaceModel;
import hu.wob.assignment.pojo.InvalidListingDataPojo;
import hu.wob.assignment.pojo.ListingForeignEntityDataPojo;
import hu.wob.assignment.pojo.ValidAndInvalidListingDataPojo;
import hu.wob.assignment.service.ListingService;
import hu.wob.assignment.service.ListingStatusService;
import hu.wob.assignment.service.LocationService;
import hu.wob.assignment.service.MarketplaceService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SyncListingsProcessor {

    private final ListingApiClient listingApiClient;
    private final ListingDataMappingHelper listingDataMappingHelper;
    private final ListingDataLoggingHelper listingDataLoggingHelper;
    private final PartitionedQueryHelper partitionedQueryHelper;
    private final ListingService listingService;
    private final MarketplaceService marketplaceService;
    private final ListingStatusService listingStatusService;
    private final LocationService locationService;
    private final ListingConverter listingConverter;

    public String syncListingList() throws Exception {
        String apiResponseString = listingApiClient.getListingList();
        ValidAndInvalidListingDataPojo validAndInvalidListingData = listingDataMappingHelper.mapToValidAndInvalidListingData(apiResponseString);

        List<ListingDto> validDtoList = validAndInvalidListingData.getValidListingDataList();
        List<InvalidListingDataPojo> invalidDtoList = validAndInvalidListingData.getInvalidListingDataList();

        Map<String, ListingDto> listingByIdMap = validDtoList.stream().collect(Collectors.toMap(ListingDto::getId, dto -> dto));
        List<String> listingIdList = new ArrayList<>(listingByIdMap.keySet());

        List<String> existingListingIdList = partitionedQueryHelper.partitionedQuery(listingIdList, listingService::findIdsThatAreInGivenCollection);
        existingListingIdList.forEach(listingByIdMap.keySet()::remove);

        ListingForeignEntityDataPojo listingForeignEntityData = getListingForeignEntityData(listingByIdMap.values());
        List<ListingModel> entityList = listingConverter.convertDtoCollectionToEntityList(listingByIdMap.values(), listingForeignEntityData);

        listingDataLoggingHelper.logInvalidListingDataToCsv(invalidDtoList);
        listingService.saveListings(entityList);

        return MessageFormat.format("{0} fetched listing entities were saved, {1} were already existing, {2} were invalid", entityList.size(),
                existingListingIdList.size(), invalidDtoList.size());
    }

    private ListingForeignEntityDataPojo getListingForeignEntityData(Collection<ListingDto> listingDtos) throws Exception {
        Set<Integer> uniqueMarketplaceIds = listingDtos.stream().map(ListingDto::getMarketplace).collect(Collectors.toSet());
        List<MarketplaceModel> marketplaceList = partitionedQueryHelper.partitionedQuery(uniqueMarketplaceIds,
                marketplaceService::findThatAreInGivenCollection);
        Map<Integer, MarketplaceModel> marketplaceByIdMap = marketplaceList.stream().collect(Collectors.toMap(MarketplaceModel::getId, dto -> dto));

        Set<Integer> uniqueListingStatusIds = listingDtos.stream().map(ListingDto::getListingStatus).collect(Collectors.toSet());
        List<ListingStatusModel> listingStatusList = partitionedQueryHelper.partitionedQuery(uniqueListingStatusIds,
                listingStatusService::findThatAreInGivenCollection);
        Map<Integer, ListingStatusModel> listingStatusByIdMap = listingStatusList.stream()
                .collect(Collectors.toMap(ListingStatusModel::getId, dto -> dto));

        Set<String> uniqueLocationIds = listingDtos.stream().map(ListingDto::getLocationId).collect(Collectors.toSet());
        List<LocationModel> locationList = partitionedQueryHelper.partitionedQuery(uniqueLocationIds, locationService::findThatAreInGivenCollection);
        Map<String, LocationModel> locationByIdMap = locationList.stream().collect(Collectors.toMap(LocationModel::getId, dto -> dto));

        return new ListingForeignEntityDataPojo(marketplaceByIdMap, listingStatusByIdMap, locationByIdMap);
    }
}
