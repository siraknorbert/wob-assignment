package hu.wob.assignment.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import hu.wob.assignment.constant.IRegexpConstants;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListingDto {

    @NotNull
    @Pattern(regexp = IRegexpConstants.UUID)
    @Size(max = 36)
    @JsonProperty("id")
    private String id;

    @NotNull
    @Size(max = 64)
    @JsonProperty("title")
    private String title;

    @NotNull
    @Size(max = 128)
    @JsonProperty("description")
    private String description;

    @NotNull
    @Pattern(regexp = IRegexpConstants.UUID)
    @Size(max = 36)
    @JsonProperty("location_id")
    private String locationId;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = false)
    @Digits(integer = 9, fraction = 2)
    @JsonProperty("listing_price")
    private Double listingPrice;

    @NotNull
    @Size(min = 3, max = 3)
    @JsonProperty("currency")
    private String currency;

    @NotNull
    @Min(1)
    @JsonProperty("quantity")
    private Integer quantity;

    @NotNull
    @Min(1)
    @JsonProperty("listing_status")
    private Integer listingStatus;

    @NotNull
    @Min(1)
    @JsonProperty("marketplace")
    private Integer marketplace;

    @NotNull
    @DateTimeFormat(pattern = "M/d/yyyy")
    @JsonProperty("upload_time")
    private LocalDate uploadTime;

    @NotNull
    @Email
    @JsonProperty("owner_email_address")
    private String ownerEmailAddress;
}
