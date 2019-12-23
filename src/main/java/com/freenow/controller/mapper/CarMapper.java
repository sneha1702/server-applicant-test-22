package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;

public class CarMapper
{
    public static CarDO mapCarDO(CarDTO carDTO)
    {
        return carDTO == null ? null : CarDO.builder()
            .id(carDTO.getId())
            .selected(carDTO.getSelected())
            .rating(carDTO.getRating())
            .engineType(carDTO.getEngineType())
            .convertible(carDTO.getConvertible())
            .licensePlate(carDTO.getLicensePlate())
            .selected(carDTO.getSelected())
            .build();
    }


    public static CarDTO mapCarDTO(CarDO carDO)
    {
        return carDO == null ? null : CarDTO.builder()
            .id(carDO.getId())
            .selected(carDO.getSelected())
            .rating(carDO.getRating())
            .engineType(carDO.getEngineType())
            .convertible(carDO.getConvertible())
            .licensePlate(carDO.getLicensePlate())
            .selected(carDO.getSelected())
            .build();
    }
}
