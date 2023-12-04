package hu.wob.assignment.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.dto.ListingDto;
import hu.wob.assignment.enumeration.CurrencyNameEnum;
import hu.wob.assignment.model.ListingModel;
import hu.wob.assignment.model.ListingStatusModel;
import hu.wob.assignment.model.LocationModel;
import hu.wob.assignment.model.MarketplaceModel;
import hu.wob.assignment.pojo.ListingForeignEntityDataPojo;

public final class ListingTestUtil {

    public static ListingModel createListingEntity(String id, LocationModel location, ListingStatusModel listingStatus,
            MarketplaceModel marketplace) {
        ListingModel entity = new ListingModel();
        entity.setId(id);
        entity.setTitle(RandomStringUtils.random(12));
        entity.setDescription(RandomStringUtils.random(36));
        entity.setLocation(location);
        entity.setListingPrice(BigDecimal.valueOf(RandomUtils.nextDouble()));
        entity.setCurrency(CurrencyNameEnum.EUR);
        entity.setQuantity(RandomUtils.nextInt());
        entity.setListingStatus(listingStatus);
        entity.setMarketplace(marketplace);
        entity.setUploadTime(LocalDate.now());
        entity.setOwnerEmailAddress(RandomStringUtils.random(24));
        return entity;
    }

    public static ListingDto createListingDto(String id, String locationId, Integer listingStatusId, Integer marketplaceId) {
        ListingDto dto = new ListingDto();
        dto.setId(id);
        dto.setTitle(RandomStringUtils.random(12));
        dto.setDescription(RandomStringUtils.random(36));
        dto.setLocationId(locationId);
        dto.setListingPrice(RandomUtils.nextDouble());
        dto.setCurrency(CurrencyNameEnum.EUR.toString());
        dto.setQuantity(RandomUtils.nextInt());
        dto.setListingStatus(listingStatusId);
        dto.setMarketplace(marketplaceId);
        dto.setUploadTime(LocalDate.now());
        dto.setOwnerEmailAddress(RandomStringUtils.random(24));
        return dto;
    }

    public static ListingForeignEntityDataPojo createListingForeignEntityDataPojo() {
        Map<Integer, MarketplaceModel> marketplaceByIdMap = new HashMap<>();
        marketplaceByIdMap.put(TestDataConstants.ID_1, MarketplaceTestUtil.createEbayMarketplaceEntity(TestDataConstants.ID_1));
        marketplaceByIdMap.put(TestDataConstants.ID_2, MarketplaceTestUtil.createEbayMarketplaceEntity(TestDataConstants.ID_2));
        marketplaceByIdMap.put(TestDataConstants.ID_3, MarketplaceTestUtil.createEbayMarketplaceEntity(TestDataConstants.ID_3));

        Map<Integer, ListingStatusModel> listingStatusByIdMap = new HashMap<>();
        listingStatusByIdMap.put(TestDataConstants.ID_1, ListingStatusTestUtil.createActiveListingStatusEntity(TestDataConstants.ID_1));
        listingStatusByIdMap.put(TestDataConstants.ID_2, ListingStatusTestUtil.createActiveListingStatusEntity(TestDataConstants.ID_2));
        listingStatusByIdMap.put(TestDataConstants.ID_3, ListingStatusTestUtil.createActiveListingStatusEntity(TestDataConstants.ID_3));

        Map<String, LocationModel> locationByIdMap = new HashMap<>();
        locationByIdMap.put(TestDataConstants.UUID_1, LocationTestUtil.createLocationEntity(TestDataConstants.UUID_1));
        locationByIdMap.put(TestDataConstants.UUID_2, LocationTestUtil.createLocationEntity(TestDataConstants.UUID_2));
        locationByIdMap.put(TestDataConstants.UUID_3, LocationTestUtil.createLocationEntity(TestDataConstants.UUID_3));

        return new ListingForeignEntityDataPojo(marketplaceByIdMap, listingStatusByIdMap, locationByIdMap);
    }
}
