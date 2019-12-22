package com.freenow.domainobject;

import com.freenow.domainvalue.EngineType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "car",
    uniqueConstraints =
    @UniqueConstraint(
        name = "uc_license_plate",
        columnNames = {"license_plate"}))
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarDO
{

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "license_plate", nullable = false)
    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    @Column(nullable = false)
    private Boolean convertible;

    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine_type", nullable = false)
    private EngineType engineType;

    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER)
    private DriverDO driver;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private ManufacturerDO manufacturer;

    @Column(nullable = false)
    private Boolean selected;
}
