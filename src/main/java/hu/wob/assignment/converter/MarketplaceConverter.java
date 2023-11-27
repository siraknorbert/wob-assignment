package hu.wob.assignment.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import hu.wob.assignment.dto.MarketplaceDto;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.MarketplaceModel;
import lombok.AllArgsConstructor;

/**
 * Class for converting {@link MarketplaceModel} entity to {@link MarketplaceDto} DTO vice versa.
 */
@Component
@AllArgsConstructor
public class MarketplaceConverter {

    public List<MarketplaceModel> convertDtoCollectionToEntityList(Collection<MarketplaceDto> dtos) throws InvalidInputException {
        if (dtos == null) {
            throw new InvalidInputException("dtos parameter is null");
        }

        List<MarketplaceModel> entities = new ArrayList<>();
        for (MarketplaceDto dto : dtos) {
            entities.add(convertDtoToEntity(dto));
        }
        return entities;
    }

    private MarketplaceModel convertDtoToEntity(MarketplaceDto dto) {
        MarketplaceModel entity = new MarketplaceModel();
        entity.setId(dto.getId());
        entity.setMarketplaceName(dto.getMarketplaceName());
        return entity;
    }
}
