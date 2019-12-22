package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class DefaultCarService implements CarService
{
    private final CarRepository carRepository;


    @Override
    public Optional<CarDO> find(Long id)
    {
        return carRepository.findById(id);
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
