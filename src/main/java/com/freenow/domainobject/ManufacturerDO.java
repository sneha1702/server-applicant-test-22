package com.freenow.domainobject;

import com.freenow.domainvalue.ManufactureType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "manufacturer",
    uniqueConstraints = @UniqueConstraint(name = "uc_manu_id", columnNames = "id"))
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ManufacturerDO
{
    @Enumerated(EnumType.STRING)
    @Column(name = "manufacture_type", nullable = false)
    ManufactureType manufactureType;
    @Id
    @GeneratedValue
    private Long id;

    //    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //    @JoinColumn(name = "car_id", nullable = false)
    //    private List<CarDO> car;
}
