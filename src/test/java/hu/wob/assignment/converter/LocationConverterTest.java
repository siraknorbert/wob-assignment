package hu.wob.assignment.converter;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hu.wob.assignment.constant.TestDataConstants;
import hu.wob.assignment.dto.LocationDto;
import hu.wob.assignment.exception.InvalidInputException;
import hu.wob.assignment.model.LocationModel;
import hu.wob.assignment.util.LocationTestUtil;

@ActiveProfiles("test")
@SpringBootTest
class LocationConverterTest {

    @Autowired
    private LocationConverter locationConverter;

    @Test
    void testConvertDtoCollectionToEntityList() throws InvalidInputException {
        // given
        String expectedIdOne = TestDataConstants.UUID_1;
        String expectedIdTwo = TestDataConstants.UUID_2;
        LocationDto expectedDtoOne = LocationTestUtil.createLocationDto(expectedIdOne);
        LocationDto expectedDtoTwo = LocationTestUtil.createLocationDto(expectedIdTwo);
        List<LocationDto> expectedDtoList = List.of(expectedDtoOne, expectedDtoTwo);

        // when
        List<LocationModel> actualEntityList = locationConverter.convertDtoCollectionToEntityList(expectedDtoList);

        // then
        Assertions.assertEquals(expectedDtoList.size(), actualEntityList.size());
        int assertedValues = numberOfObjectsWithEqualPropertyValues(expectedDtoList, actualEntityList);
        Assertions.assertEquals(expectedDtoList.size(), assertedValues);
    }

    @Test
    void testConvertDtoCollectionToEntityListThrowsInvalidInputExceptionForNullInput() {
        // given when then
        Assertions.assertThrows(InvalidInputException.class, () -> locationConverter.convertDtoCollectionToEntityList(null));
    }

    private int numberOfObjectsWithEqualPropertyValues(List<LocationDto> expectedDtoList, List<LocationModel> actualEntityList) {
        int assertedValues = 0;
        for (LocationDto expectedDto : expectedDtoList) {
            for (LocationModel actualEntity : actualEntityList) {
                if (arePropertyValuesEqual(actualEntity, expectedDto)) {
                    assertedValues++;
                }
            }
        }
        return assertedValues;
    }

    private boolean arePropertyValuesEqual(LocationModel model, LocationDto dto) {
        return Objects.equals(model.getId(), dto.getId()) && model.getManagerName().equals(dto.getManagerName())
                && model.getPhone().equals(dto.getPhone()) && model.getAddressPrimary().equals(dto.getAddressPrimary())
                && model.getAddressSecondary().equals(dto.getAddressSecondary()) && model.getCountry().equals(dto.getCountry())
                && model.getTown().equals(dto.getTown()) && model.getPostalCode().equals(dto.getPostalCode());
    }
}
