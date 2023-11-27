package hu.wob.assignment.processor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import hu.wob.assignment.client.MarketplaceApiClient;
import hu.wob.assignment.converter.MarketplaceConverter;
import hu.wob.assignment.dto.MarketplaceDto;
import hu.wob.assignment.helper.PartitionedQueryHelper;
import hu.wob.assignment.model.MarketplaceModel;
import hu.wob.assignment.service.MarketplaceService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SyncMarketplacesProcessor {

    private final MarketplaceApiClient marketplaceApiClient;
    private final PartitionedQueryHelper partitionedQueryHelper;
    private final MarketplaceService marketplaceService;
    private final MarketplaceConverter marketplaceConverter;

    public String syncMarketplaceList() throws Exception {
        List<MarketplaceDto> dtoList = marketplaceApiClient.getMarketplaceList();

        Map<Integer, MarketplaceDto> marketplaceByIdMap = dtoList.stream().collect(Collectors.toMap(MarketplaceDto::getId, dto -> dto));
        List<Integer> marketplaceIdList = new ArrayList<>(marketplaceByIdMap.keySet());

        List<Integer> existingMarketplaceIdList = partitionedQueryHelper.partitionedQuery(marketplaceIdList,
                marketplaceService::findIdsThatAreInGivenCollection);
        existingMarketplaceIdList.forEach(marketplaceByIdMap.keySet()::remove);

        List<MarketplaceModel> entityList = marketplaceConverter.convertDtoCollectionToEntityList(marketplaceByIdMap.values());
        marketplaceService.saveMarketplaces(entityList);

        return MessageFormat.format("{0} fetched marketplace entities were saved, {1} were already existing", entityList.size(),
                existingMarketplaceIdList.size());
    }
}
