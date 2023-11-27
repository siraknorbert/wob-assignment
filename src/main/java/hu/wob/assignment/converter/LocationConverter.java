package hu.wob.assignment.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import hu.wob.assignment.dto.LocationDto;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.LocationModel;
import lombok.AllArgsConstructor;

/**
 * Class for converting {@link LocationModel} entity to {@link LocationDto} DTO vice versa.
 */
@Component
@AllArgsConstructor
public class LocationConverter {

    public List<LocationModel> convertDtoCollectionToEntityList(Collection<LocationDto> dtos) throws InvalidInputException {
        if (dtos == null) {
            throw new InvalidInputException("dtos parameter is null");
        }

        List<LocationModel> entities = new ArrayList<>();
        for (LocationDto dto : dtos) {
            entities.add(convertDtoToEntity(dto));
        }
        return entities;
    }

    private LocationModel convertDtoToEntity(LocationDto dto) {
        LocationModel entity = new LocationModel();
        entity.setId(dto.getId());
        entity.setManagerName(dto.getManagerName());
        entity.setPhone(dto.getPhone());
        entity.setAddressPrimary(dto.getAddressPrimary());
        entity.setAddressSecondary(dto.getAddressSecondary());
        entity.setCountry(dto.getCountry());
        entity.setTown(dto.getTown());
        entity.setPostalCode(dto.getPostalCode());
        return entity;
    }
}
