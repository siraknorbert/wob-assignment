package hu.wob.assignment.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import hu.wob.assignment.dto.ListingDto;
import hu.wob.assignment.enumeration.CurrencyNameEnum;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.ListingModel;
import hu.wob.assignment.pojo.ListingForeignEntityDataPojo;
import lombok.AllArgsConstructor;

/**
 * Class for converting {@link ListingModel} entity to {@link ListingDto} DTO vice versa.
 */
@Component
@AllArgsConstructor
public class ListingConverter {

    public List<ListingModel> convertDtoCollectionToEntityList(Collection<ListingDto> dtos, ListingForeignEntityDataPojo listingForeignEntityData)
            throws InvalidInputException {
        if (dtos == null) {
            throw new InvalidInputException("dtos parameter is null");
        }
        if (listingForeignEntityData == null) {
            throw new InvalidInputException("listingForeignEntityData parameter is null");
        }

        List<ListingModel> entities = new ArrayList<>();
        for (ListingDto dto : dtos) {
            entities.add(convertDtoToEntity(dto, listingForeignEntityData));
        }
        return entities;
    }

    private ListingModel convertDtoToEntity(ListingDto dto, ListingForeignEntityDataPojo listingForeignEntityData) {
        ListingModel entity = new ListingModel();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setLocation(listingForeignEntityData.getLocationByIdMap().get(dto.getLocationId()));
        entity.setListingPrice(BigDecimal.valueOf(dto.getListingPrice()));
        entity.setCurrency(CurrencyNameEnum.valueOf(dto.getCurrency()));
        entity.setQuantity(dto.getQuantity());
        entity.setListingStatus(listingForeignEntityData.getListingStatusByIdMap().get(dto.getListingStatus()));
        entity.setMarketplace(listingForeignEntityData.getMarketplaceByIdMap().get(dto.getMarketplace()));
        entity.setUploadTime(dto.getUploadTime());
        entity.setOwnerEmailAddress(dto.getOwnerEmailAddress());
        return entity;
    }
}
