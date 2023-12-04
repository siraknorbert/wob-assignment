package hu.wob.assignment.converter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.dto.ListingDto;
import hu.wob.assignment.enumeration.CurrencyNameEnum;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.ListingModel;
import hu.wob.assignment.pojo.ListingForeignEntityDataPojo;
import hu.wob.assignment.util.ListingTestUtil;

@ActiveProfiles("test")
@SpringBootTest
class ListingConverterTest {

    @Autowired
    private ListingConverter listingConverter;

    @Test
    void testConvertDtoCollectionToEntityList() throws InvalidInputException {
        // given
        List<ListingDto> expectedDtoList = createExpectedDtoListOf2Elements();
        ListingForeignEntityDataPojo foreignEntityData = ListingTestUtil.createListingForeignEntityDataPojo();

        // when
        List<ListingModel> actualEntityList = listingConverter.convertDtoCollectionToEntityList(expectedDtoList, foreignEntityData);

        // then
        Assertions.assertEquals(expectedDtoList.size(), actualEntityList.size());
        int assertedValues = numberOfObjectsWithEqualPropertyValues(expectedDtoList, actualEntityList);
        Assertions.assertEquals(expectedDtoList.size(), assertedValues);
    }

    @Test
    void testConvertDtoCollectionToEntityListThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> listingConverter.convertDtoCollectionToEntityList(null, null));
    }

    private List<ListingDto> createExpectedDtoListOf2Elements() {
        String expectedIdOne = TestDataConstants.UUID_1;
        String expectedIdTwo = TestDataConstants.UUID_2;
        ListingDto expectedDtoOne = ListingTestUtil.createListingDto(expectedIdOne, TestDataConstants.UUID_1, TestDataConstants.ID_1,
                TestDataConstants.ID_1);
        ListingDto expectedDtoTwo = ListingTestUtil.createListingDto(expectedIdTwo, TestDataConstants.UUID_2, TestDataConstants.ID_2,
                TestDataConstants.ID_2);
        return List.of(expectedDtoOne, expectedDtoTwo);
    }

    private int numberOfObjectsWithEqualPropertyValues(List<ListingDto> expectedDtoList, List<ListingModel> actualEntityList) {
        int assertedValues = 0;
        for (ListingDto expectedDto : expectedDtoList) {
            for (ListingModel actualEntity : actualEntityList) {
                if (arePropertyValuesEqual(actualEntity, expectedDto)) {
                    assertedValues++;
                }
            }
        }
        return assertedValues;
    }

    private boolean arePropertyValuesEqual(ListingModel model, ListingDto dto) {
        return Objects.equals(model.getId(), dto.getId()) && model.getTitle().equals(dto.getTitle())
                && model.getDescription().equals(dto.getDescription()) && model.getLocation().getId().equals(dto.getLocationId())
                && model.getListingPrice().equals(BigDecimal.valueOf(dto.getListingPrice()))
                && model.getCurrency().equals(CurrencyNameEnum.valueOf(dto.getCurrency())) && model.getQuantity().equals(dto.getQuantity())
                && model.getListingStatus().getId().equals(dto.getListingStatus()) && model.getMarketplace().getId().equals(dto.getMarketplace())
                && model.getUploadTime().isEqual(dto.getUploadTime()) && model.getOwnerEmailAddress().equals(dto.getOwnerEmailAddress());
    }
}
