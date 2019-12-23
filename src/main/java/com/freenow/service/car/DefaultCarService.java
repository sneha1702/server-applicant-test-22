package com.freenow.service.car;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.dataaccessobject.CarRepository;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.CarNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DefaultCarService implements CarService
{
    private final CarRepository carRepository;


    @Override
    public CarDTO find(Long id) throws CarNotFoundException
    {
        Optional<CarDO> carDO = carRepository.findById(id
        );
        if (carDO.isPresent())
        {
            return CarMapper.mapCarDTO(carDO.get());
        }
        else
        {
            throw new CarNotFoundException("Car not found!");
        }
    }


    @Override
    public List<CarDO> findAll()
    {
        Iterable<CarDO> it = carRepository.findAll();
        return StreamSupport.stream(it.spliterator(), false)
            .collect(Collectors.toList());
    }


    @Override
    public List<CarDO> findAll(Specification<CarDO> carDOSpecification)
    {
        return carRepository.findAll(carDOSpecification);
    }
}
