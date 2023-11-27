package hu.wob.assignment.pojo;

import java.util.List;

import hu.wob.assignment.dto.ListingDto;

public class ValidAndInvalidListingDataPojo {

    private final List<ListingDto> validListingDataList;
    private final List<InvalidListingDataPojo> invalidListingDataList;

    public ValidAndInvalidListingDataPojo(List<ListingDto> validListingDataList, List<InvalidListingDataPojo> invalidListingDataList) {
        this.validListingDataList = validListingDataList;
        this.invalidListingDataList = invalidListingDataList;
    }

    public List<ListingDto> getValidListingDataList() {
        return validListingDataList;
    }

    public List<InvalidListingDataPojo> getInvalidListingDataList() {
        return invalidListingDataList;
    }
}
