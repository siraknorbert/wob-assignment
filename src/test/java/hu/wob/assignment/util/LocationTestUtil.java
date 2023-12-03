package hu.wob.assignment.util;

import org.apache.commons.lang3.RandomStringUtils;

import hu.wob.assignment.dto.LocationDto;
import hu.wob.assignment.model.LocationModel;

public final class LocationTestUtil {

    public static LocationModel createLocationEntity(String id) {
        LocationModel entity = new LocationModel();
        entity.setId(id);
        entity.setManagerName(RandomStringUtils.random(12));
        entity.setPhone(RandomStringUtils.random(12));
        entity.setAddressPrimary(RandomStringUtils.random(24));
        entity.setAddressSecondary(RandomStringUtils.random(24));
        entity.setCountry(RandomStringUtils.random(12));
        entity.setTown(RandomStringUtils.random(12));
        entity.setPostalCode(RandomStringUtils.random(12));
        return entity;
    }

    public static LocationDto createLocationDto(String id) {
        LocationDto dto = new LocationDto();
        dto.setId(id);
        dto.setManagerName(RandomStringUtils.random(12));
        dto.setPhone(RandomStringUtils.random(12));
        dto.setAddressPrimary(RandomStringUtils.random(24));
        dto.setAddressSecondary(RandomStringUtils.random(24));
        dto.setCountry(RandomStringUtils.random(12));
        dto.setTown(RandomStringUtils.random(12));
        dto.setPostalCode(RandomStringUtils.random(12));
        return dto;
    }
}
