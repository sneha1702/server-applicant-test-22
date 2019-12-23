package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import com.freenow.exception.CarNotFoundException;
import com.freenow.exception.ConstraintsViolationException;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public interface CarService
{

    CarDO find(Long id) throws CarNotFoundException;

    List<CarDO> findAll();

    List<CarDO> findAll(Specification<CarDO> carDOSpecification);

    CarDO create(CarDO carDO) throws ConstraintsViolationException;

    void delete(long carId) throws CarNotFoundException;
}
