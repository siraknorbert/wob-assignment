package hu.wob.assignment.converter;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.dto.MarketplaceDto;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.MarketplaceModel;
import hu.wob.assignment.util.MarketplaceTestUtil;

@ActiveProfiles("test")
@SpringBootTest
class MarketplaceConverterTest {

    @Autowired
    private MarketplaceConverter marketplaceConverter;

    @Test
    void testConvertDtoCollectionToEntityList() throws InvalidInputException {
        // given
        Integer expectedIdOne = TestDataConstants.ID_1;
        Integer expectedIdTwo = TestDataConstants.ID_2;
        MarketplaceDto expectedDtoOne = MarketplaceTestUtil.createEbayMarketplaceDto(expectedIdOne);
        MarketplaceDto expectedDtoTwo = MarketplaceTestUtil.createEbayMarketplaceDto(expectedIdTwo);
        List<MarketplaceDto> expectedDtoList = List.of(expectedDtoOne, expectedDtoTwo);

        // when
        List<MarketplaceModel> actualEntityList = marketplaceConverter.convertDtoCollectionToEntityList(expectedDtoList);

        // then
        Assertions.assertEquals(expectedDtoList.size(), actualEntityList.size());
        int assertedValues = numberOfObjectsWithEqualPropertyValues(expectedDtoList, actualEntityList);
        Assertions.assertEquals(expectedDtoList.size(), assertedValues);
    }

    @Test
    void testConvertDtoCollectionToEntityListThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> marketplaceConverter.convertDtoCollectionToEntityList(null));
    }

    private int numberOfObjectsWithEqualPropertyValues(List<MarketplaceDto> expectedDtoList, List<MarketplaceModel> actualEntityList) {
        int assertedValues = 0;
        for (MarketplaceDto expectedDto : expectedDtoList) {
            for (MarketplaceModel actualEntity : actualEntityList) {
                if (arePropertyValuesEqual(actualEntity, expectedDto)) {
                    assertedValues++;
                }
            }
        }
        return assertedValues;
    }

    private boolean arePropertyValuesEqual(MarketplaceModel model, MarketplaceDto dto) {
        return Objects.equals(model.getId(), dto.getId()) && model.getMarketplaceName().equals(dto.getMarketplaceName());
    }
}
