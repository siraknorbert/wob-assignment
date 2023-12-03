package hu.wob.assignment.converter;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.dto.ListingStatusDto;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.ListingStatusModel;
import hu.wob.assignment.util.ListingStatusTestUtil;

@ActiveProfiles("test")
@SpringBootTest
class ListingStatusConverterTest {

    @Autowired
    private ListingStatusConverter listingStatusConverter;

    @Test
    void testConvertDtoCollectionToEntityList() throws InvalidInputException {
        // given
        Integer expectedIdOne = TestDataConstants.ID_1;
        Integer expectedIdTwo = TestDataConstants.ID_2;
        ListingStatusDto expectedDtoOne = ListingStatusTestUtil.createActiveListingStatusDto(expectedIdOne);
        ListingStatusDto expectedDtoTwo = ListingStatusTestUtil.createActiveListingStatusDto(expectedIdTwo);
        List<ListingStatusDto> expectedDtoList = List.of(expectedDtoOne, expectedDtoTwo);

        // when
        List<ListingStatusModel> actualEntityList = listingStatusConverter.convertDtoCollectionToEntityList(expectedDtoList);

        // then
        Assertions.assertEquals(expectedDtoList.size(), actualEntityList.size());
        int assertedValues = numberOfObjectsWithEqualPropertyValues(expectedDtoList, actualEntityList);
        Assertions.assertEquals(expectedDtoList.size(), assertedValues);
    }

    @Test
    void testConvertDtoCollectionToEntityListThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> listingStatusConverter.convertDtoCollectionToEntityList(null));
    }

    private int numberOfObjectsWithEqualPropertyValues(List<ListingStatusDto> expectedDtoList, List<ListingStatusModel> actualEntityList) {
        int assertedValues = 0;
        for (ListingStatusDto expectedDto : expectedDtoList) {
            for (ListingStatusModel actualEntity : actualEntityList) {
                if (arePropertyValuesEqual(actualEntity, expectedDto)) {
                    assertedValues++;
                }
            }
        }
        return assertedValues;
    }

    private boolean arePropertyValuesEqual(ListingStatusModel model, ListingStatusDto dto) {
        return Objects.equals(model.getId(), dto.getId()) && model.getStatusName().equals(dto.getStatusName());
    }
}
