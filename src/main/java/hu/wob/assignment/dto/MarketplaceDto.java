package hu.wob.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import hu.wob.assignment.enumeration.MarketplaceNameEnum;
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
public class MarketplaceDto {

    @NotNull
    @Min(1)
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @JsonProperty("marketplace_name")
    private MarketplaceNameEnum marketplaceName;
}
