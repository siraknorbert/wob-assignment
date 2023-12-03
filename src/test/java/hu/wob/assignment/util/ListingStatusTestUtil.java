package hu.wob.assignment.util;

import hu.wob.assignment.dto.ListingStatusDto;
import hu.wob.assignment.enumeration.ListingStatusNameEnum;
import hu.wob.assignment.model.ListingStatusModel;

public final class ListingStatusTestUtil {

    public static ListingStatusModel createActiveListingStatusEntity(Integer id) {
        ListingStatusModel entity = new ListingStatusModel();
        entity.setId(id);
        entity.setStatusName(ListingStatusNameEnum.ACTIVE);
        return entity;
    }

    public static ListingStatusDto createActiveListingStatusDto(Integer id) {
        ListingStatusDto dto = new ListingStatusDto();
        dto.setId(id);
        dto.setStatusName(ListingStatusNameEnum.ACTIVE);
        return dto;
    }
}
