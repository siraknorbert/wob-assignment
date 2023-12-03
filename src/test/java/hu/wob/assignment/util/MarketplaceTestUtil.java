package hu.wob.assignment.util;

import hu.wob.assignment.dto.MarketplaceDto;
import hu.wob.assignment.enumeration.MarketplaceNameEnum;
import hu.wob.assignment.model.MarketplaceModel;

public final class MarketplaceTestUtil {

    public static MarketplaceModel createEbayMarketplaceEntity(Integer id) {
        MarketplaceModel entity = new MarketplaceModel();
        entity.setId(id);
        entity.setMarketplaceName(MarketplaceNameEnum.EBAY);
        return entity;
    }

    public static MarketplaceDto createEbayMarketplaceDto(Integer id) {
        MarketplaceDto dto = new MarketplaceDto();
        dto.setId(id);
        dto.setMarketplaceName(MarketplaceNameEnum.EBAY);
        return dto;
    }
}
