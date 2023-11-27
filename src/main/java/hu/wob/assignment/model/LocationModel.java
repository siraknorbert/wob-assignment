package hu.wob.assignment.model;

import hu.wob.assignment.constant.IRegexpConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "location")
public class LocationModel {

    @Id
    @Pattern(regexp = IRegexpConstants.UUID)
    @Size(max = 36)
    private String id;

    @NotNull
    @Size(max = 128)
    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @NotNull
    @Size(max = 32)
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Size(max = 128)
    @Column(name = "address_primary", nullable = false)
    private String addressPrimary;

    @Size(max = 128)
    @Column(name = "address_secondary")
    private String addressSecondary;

    @NotNull
    @Size(max = 64)
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Size(max = 64)
    @Column(name = "town", nullable = false)
    private String town;

    @Size(max = 32)
    @Column(name = "postal_code")
    private String postalCode;
}
