package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.CarNotFoundException;
import com.freenow.exception.ConstraintsViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DefaultCarService implements CarService
{
    private final CarRepository carRepository;


    @Override
    public CarDO find(Long id) throws CarNotFoundException
    {
        Optional<CarDO> carDO = carRepository.findById(id);
        if (carDO.isPresent())
        {
            CarDO val = carDO.get();
            if (val.getDeleted() == null || !val.getDeleted())
            {
                return val;
            }
        }
        throw new CarNotFoundException("Car not found!");
    }


    @Override
    public List<CarDO> findAll()
    {
        Iterable<CarDO> it = carRepository.findAll();
        return StreamSupport.stream(it.spliterator(), false).filter(e -> e.getDeleted() == null || !e.getDeleted())
            .collect(Collectors.toList());
    }


    @Override
    public List<CarDO> findAll(Specification<CarDO> carDOSpecification)
    {
        return carRepository.findAll(carDOSpecification);
    }


    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException
    {
        CarDO c;
        try
        {
            c = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new ConstraintsViolationException(e.getMessage());
        }
        return c;
    }


    @Override
    public synchronized void delete(long carId) throws CarNotFoundException
    {
        CarDO ca = find(carId);
        ca.setDeleted(true);
        carRepository.save(ca);
    }
}
