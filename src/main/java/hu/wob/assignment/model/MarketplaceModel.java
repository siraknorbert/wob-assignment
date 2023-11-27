package hu.wob.assignment.model;

import hu.wob.assignment.enumeration.MarketplaceNameEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "marketplace")
public class MarketplaceModel {

    @Id
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marketplace_name", nullable = false)
    private MarketplaceNameEnum marketplaceName;
}
