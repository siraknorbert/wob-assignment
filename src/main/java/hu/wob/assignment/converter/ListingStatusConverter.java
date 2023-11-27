package hu.wob.assignment.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import hu.wob.assignment.dto.ListingStatusDto;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.ListingStatusModel;
import lombok.AllArgsConstructor;

/**
 * Class for converting {@link ListingStatusModel} entity to {@link ListingStatusDto} DTO vice versa.
 */
@Component
@AllArgsConstructor
public class ListingStatusConverter {

    public List<ListingStatusModel> convertDtoCollectionToEntityList(Collection<ListingStatusDto> dtos) throws InvalidInputException {
        if (dtos == null) {
            throw new InvalidInputException("dtos parameter is null");
        }

        List<ListingStatusModel> entities = new ArrayList<>();
        for (ListingStatusDto dto : dtos) {
            entities.add(convertDtoToEntity(dto));
        }
        return entities;
    }

    private ListingStatusModel convertDtoToEntity(ListingStatusDto dto) {
        ListingStatusModel entity = new ListingStatusModel();
        entity.setId(dto.getId());
        entity.setStatusName(dto.getStatusName());
        return entity;
    }
}
