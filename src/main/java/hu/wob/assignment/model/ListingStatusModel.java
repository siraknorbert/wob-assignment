package hu.wob.assignment.model;

import hu.wob.assignment.enumeration.ListingStatusNameEnum;
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
@Table(name = "listing_status")
public class ListingStatusModel {

    @Id
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_name", nullable = false)
    private ListingStatusNameEnum statusName;
}
