package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.dataaccessobject.CarCriteriaSpecs;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.ManufactureType;
import com.freenow.exception.CarNotFoundException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/cars")
public class CarController
{
    private final CarService carService;


    @GetMapping("/{carId}")
    public CarDTO getCar(@PathVariable long carId) throws EntityNotFoundException, CarNotFoundException
    {
        Optional<CarDO> carDO = carService.find(carId);
        if (carDO.isPresent())
        {
            return CarMapper.mapCarDTO(carDO.get());
        }
        else
        {
            throw new CarNotFoundException();
        }
    }


    @GetMapping
    public List<CarDTO> getCars() throws EntityNotFoundException, CarNotFoundException
    {
        return carService.findAll().stream().map(CarMapper::mapCarDTO).collect(Collectors.toList());
    }


    @GetMapping("/filter")
    public List<CarDTO> getCars(
        @RequestParam(required = false) String licensePlate,
        @RequestParam(required = false) String rating,
        @RequestParam(required = false) EngineType engineType,
        @RequestParam(required = false) Boolean convertible,
        @RequestParam(required = false) Boolean selected,
        @RequestParam(required = false) ManufactureType manufactureType)
    {
        Specification<CarDO> and = CarCriteriaSpecs.withLicensePlate(licensePlate)
            .and(CarCriteriaSpecs.withConvertible(convertible)
                .and(CarCriteriaSpecs.withEngineType(engineType))
                .and(CarCriteriaSpecs.withRating(rating).and(CarCriteriaSpecs.withSelected(selected).and(CarCriteriaSpecs.withManufactureType(manufactureType)))));
        return carService.findAll(and).stream().map(CarMapper::mapCarDTO).collect(Collectors.toList());
    }
}
