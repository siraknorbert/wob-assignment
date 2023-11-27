package hu.wob.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import hu.wob.assignment.constant.IRegexpConstants;
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
public class LocationDto {

    @NotNull
    @Pattern(regexp = IRegexpConstants.UUID)
    @Size(max = 36)
    @JsonProperty("id")
    private String id;

    @NotNull
    @Size(max = 128)
    @JsonProperty("manager_name")
    private String managerName;

    @NotNull
    @Size(max = 32)
    @JsonProperty("phone")
    private String phone;

    @NotNull
    @Size(max = 128)
    @JsonProperty("address_primary")
    private String addressPrimary;

    @Size(max = 128)
    @JsonProperty("address_secondary")
    private String addressSecondary;

    @NotNull
    @Size(max = 64)
    @JsonProperty("country")
    private String country;

    @NotNull
    @Size(max = 64)
    @JsonProperty("town")
    private String town;

    @Size(max = 32)
    @JsonProperty("postal_code")
    private String postalCode;
}
