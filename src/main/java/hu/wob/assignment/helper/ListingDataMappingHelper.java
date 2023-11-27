package hu.wob.assignment.helper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.wob.assignment.dto.ListingDto;
import hu.wob.assignment.exception.DataMappingException;
import hu.wob.assignment.pojo.InvalidListingDataPojo;
import hu.wob.assignment.pojo.ValidAndInvalidListingDataPojo;
import hu.wob.assignment.util.ParseUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Helper class for mapping listing data
 */
@Component
public class ListingDataMappingHelper {

    private final Map<String, String> camelCaseBySnakeCaseMap = new HashMap<>();

    public ListingDataMappingHelper() {
        populateCamelCaseFieldBySnakeCaseFieldMap();
    }

    public ValidAndInvalidListingDataPojo mapToValidAndInvalidListingData(String nonValidatedDtoList) throws DataMappingException {
        List<Map<String, Object>> apiResponse = mapApiResponseStringEx(nonValidatedDtoList);

        List<ListingDto> validListingDataList = new ArrayList<>();
        List<InvalidListingDataPojo> invalidListingDataList = new ArrayList<>();

        for (Map<String, Object> apiResponseElement : apiResponse) {
            Optional<InvalidListingDataPojo> optionalInvalidListingData = mapToOptionalInvalidListingData(apiResponseElement);
            if (optionalInvalidListingData.isPresent()) {
                invalidListingDataList.add(optionalInvalidListingData.get());
            } else {
                ListingDto validListingData = mapToListingDtoEx(apiResponseElement);
                validListingDataList.add(validListingData);
            }
        }

        return new ValidAndInvalidListingDataPojo(validListingDataList, invalidListingDataList);
    }

    private void populateCamelCaseFieldBySnakeCaseFieldMap() {
        Class<ListingDto> listingDtoClass = ListingDto.class;
        Field[] fields = listingDtoClass.getDeclaredFields();

        for (Field field : fields) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null && jsonProperty.value().contains("_")) {
                camelCaseBySnakeCaseMap.put(jsonProperty.value(), field.getName());
            }
        }
    }

    private List<Map<String, Object>> mapApiResponseStringEx(String apiResponseString) throws DataMappingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(apiResponseString, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new DataMappingException("Error occurred during the mapping of string api response", e);
        }
    }

    private Optional<InvalidListingDataPojo> mapToOptionalInvalidListingData(Map<String, Object> apiResponseElement) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        for (Map.Entry<String, Object> entry : apiResponseElement.entrySet()) {
            String fieldName = camelCaseBySnakeCaseMap.containsKey(entry.getKey()) ? camelCaseBySnakeCaseMap.get(entry.getKey()) : entry.getKey();
            Object fieldValue = entry.getValue();
            boolean parsingErrorOccurred = false;

            try {
                switch (fieldName) {
                case "listingPrice" -> fieldValue = Double.parseDouble(String.valueOf(fieldValue));
                case "quantity", "listingStatus", "marketplace" -> fieldValue = Integer.parseInt(String.valueOf(fieldValue));
                case "uploadTime" -> {
                    if (fieldValue != null) {
                        fieldValue = LocalDate.parse(String.valueOf(fieldValue), DateTimeFormatter.ofPattern("M/d/yyyy"));
                    }
                }
                }
            } catch (Exception e) {
                parsingErrorOccurred = true;
            }

            if (parsingErrorOccurred) {
                StringBuilder errorDetails = new StringBuilder();
                errorDetails.append("Validation failed for field '").append(fieldName).append("' with value: '").append(fieldValue)
                        .append("' - Field value has invalid type");
                InvalidListingDataPojo invalidListingDataPojo = new InvalidListingDataPojo((String) apiResponseElement.get("id"), fieldName,
                        errorDetails.toString());

                return Optional.of(invalidListingDataPojo);
            } else {
                Set<ConstraintViolation<ListingDto>> violations = validator.validateValue(ListingDto.class, fieldName, fieldValue);

                if (!violations.isEmpty()) {
                    StringBuilder errorDetails = new StringBuilder();
                    errorDetails.append("Validation failed for field '").append(fieldName).append("' with value: '").append(fieldValue).append("': ");
                    for (ConstraintViolation<ListingDto> violation : violations) {
                        errorDetails.append(violation.getMessage()).append("; ");
                    }

                    InvalidListingDataPojo invalidListingDataPojo = new InvalidListingDataPojo((String) apiResponseElement.get("id"), fieldName,
                            errorDetails.toString());

                    return Optional.of(invalidListingDataPojo);
                }
            }
        }

        return Optional.empty();
    }

    private ListingDto mapToListingDtoEx(Map<String, Object> apiResponseElement) throws DataMappingException {
        ListingDto listingDto = new ListingDto();

        try {
            listingDto.setId(ParseUtil.getStringValue(apiResponseElement.get("id")));
            listingDto.setTitle(ParseUtil.getStringValue(apiResponseElement.get("title")));
            listingDto.setDescription(ParseUtil.getStringValue(apiResponseElement.get("description")));
            listingDto.setLocationId(ParseUtil.getStringValue(apiResponseElement.get("location_id")));
            listingDto.setListingPrice(ParseUtil.getDoubleValue(apiResponseElement.get("listing_price")));
            listingDto.setCurrency(ParseUtil.getStringValue(apiResponseElement.get("currency")));
            listingDto.setQuantity(ParseUtil.getIntegerValue(apiResponseElement.get("quantity")));
            listingDto.setListingStatus(ParseUtil.getIntegerValue(apiResponseElement.get("listing_status")));
            listingDto.setMarketplace(ParseUtil.getIntegerValue(apiResponseElement.get("marketplace")));
            listingDto.setUploadTime(ParseUtil.getLocalDateValue(apiResponseElement.get("upload_time"), "M/d/yyyy"));
            listingDto.setOwnerEmailAddress(ParseUtil.getStringValue(apiResponseElement.get("owner_email_address")));

            return listingDto;
        } catch (Exception e) {
            throw new DataMappingException("An error occurred during mapping of apiResponseElement to ListingDto type", e);
        }
    }
}
