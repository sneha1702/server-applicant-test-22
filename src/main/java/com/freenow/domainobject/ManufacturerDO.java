package com.freenow.domainobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freenow.domainvalue.ManufactureType;
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
    name = "manufacturer",
    uniqueConstraints = @UniqueConstraint(name = "uc_manu_id", columnNames = "serial_num"))
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

    @Column(name = "serial_num", nullable = false)
    @NotNull(message = "Serial Num cannot be null")
    private String serialNum;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = true)
    @JsonIgnore
    private List<CarDO> car = new ArrayList<>();
}
