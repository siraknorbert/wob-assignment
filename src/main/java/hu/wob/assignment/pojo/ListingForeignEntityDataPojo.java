package hu.wob.assignment.pojo;

import java.util.Map;

import hu.wob.assignment.model.ListingStatusModel;
import hu.wob.assignment.model.LocationModel;
import hu.wob.assignment.model.MarketplaceModel;

public class ListingForeignEntityDataPojo {

    private final Map<Integer, MarketplaceModel> marketplaceByIdMap;
    private final Map<Integer, ListingStatusModel> listingStatusByIdMap;
    private final Map<String, LocationModel> locationByIdMap;

    public ListingForeignEntityDataPojo(Map<Integer, MarketplaceModel> marketplaceByIdMap, Map<Integer, ListingStatusModel> listingStatusByIdMap,
            Map<String, LocationModel> locationByIdMap) {
        this.marketplaceByIdMap = marketplaceByIdMap;
        this.listingStatusByIdMap = listingStatusByIdMap;
        this.locationByIdMap = locationByIdMap;
    }

    public Map<Integer, MarketplaceModel> getMarketplaceByIdMap() {
        return marketplaceByIdMap;
    }

    public Map<Integer, ListingStatusModel> getListingStatusByIdMap() {
        return listingStatusByIdMap;
    }

    public Map<String, LocationModel> getLocationByIdMap() {
        return locationByIdMap;
    }
}
