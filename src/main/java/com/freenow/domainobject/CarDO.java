package com.freenow.domainobject;

import com.freenow.domainvalue.EngineType;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = true)
    private List<DriverDO> driver = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = true)
    private ManufacturerDO manufacturer;

    @Column
    private Boolean selected;

    @Column
    private Boolean deleted;
}
