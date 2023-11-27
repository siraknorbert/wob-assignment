package hu.wob.assignment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import hu.wob.assignment.constant.IRegexpConstants;
import hu.wob.assignment.enumeration.CurrencyNameEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "listing")
public class ListingModel {

    @Id
    @Pattern(regexp = IRegexpConstants.UUID)
    @Size(max = 36)
    private String id;

    @NotNull
    @Column(name = "title", nullable = false)
    @Size(max = 64)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    @Size(max = 128)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private LocationModel location;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = false)
    @Digits(integer = 9, fraction = 2)
    @Column(name = "listing_price", nullable = false)
    private BigDecimal listingPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyNameEnum currency;

    @NotNull
    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_status_id", nullable = false)
    private ListingStatusModel listingStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marketplace_id", nullable = false)
    private MarketplaceModel marketplace;

    @NotNull
    @DateTimeFormat(pattern = "M/d/yyyy")
    @Column(name = "upload_time")
    private LocalDate uploadTime;

    @NotNull
    @Email
    @Column(name = "owner_email_address", nullable = false)
    private String ownerEmailAddress;
}
