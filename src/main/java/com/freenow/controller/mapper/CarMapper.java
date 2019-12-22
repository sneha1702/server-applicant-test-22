package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import java.util.stream.Collectors;

public class CarMapper
{
    public static CarDO mapCarDO(CarDTO carDTO)
    {
        return CarDO.builder()
            .id(carDTO.getId())
            .selected(carDTO.getSelected())
            .rating(carDTO.getRating())
            .engineType(carDTO.getEngineType())
            .convertible(carDTO.getConvertible())
            .licensePlate(carDTO.getLicensePlate())
            .driver(carDTO.getDrivers().stream().map(DriverMapper::makeDriverDO).collect(Collectors.toList()))
            .build();
    }


    public static CarDTO mapCarDTO(CarDO carDO)
    {
        return CarDTO.builder()
            .id(carDO.getId())
            .selected(carDO.getSelected())
            .rating(carDO.getRating())
            .engineType(carDO.getEngineType())
            .convertible(carDO.getConvertible())
            .licensePlate(carDO.getLicensePlate())
            .drivers(carDO.getDriver().stream().map(DriverMapper::makeDriverDTO).collect(Collectors.toList()))
            .build();
    }
}
