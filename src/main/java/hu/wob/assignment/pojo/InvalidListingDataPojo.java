package hu.wob.assignment.pojo;

public class InvalidListingDataPojo {

    private final String id;
    private final String invalidField;
    private final String violationDetails;

    public InvalidListingDataPojo(String id, String invalidField, String violationDetails) {
        this.id = id;
        this.invalidField = invalidField;
        this.violationDetails = violationDetails;
    }

    public String getId() {
        return id;
    }

    public String getInvalidField() {
        return invalidField;
    }

    public String getViolationDetails() {
        return violationDetails;
    }
}
