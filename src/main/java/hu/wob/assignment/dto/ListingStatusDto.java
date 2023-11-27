package hu.wob.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import hu.wob.assignment.enumeration.ListingStatusNameEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListingStatusDto {

    @NotNull
    @Min(1)
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @JsonProperty("status_name")
    private ListingStatusNameEnum statusName;
}
