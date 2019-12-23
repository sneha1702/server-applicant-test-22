package com.freenow.service.car;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.CarNotFoundException;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public interface CarService
{

    CarDTO find(Long id) throws CarNotFoundException;

    List<CarDO> findAll();

    List<CarDO> findAll(Specification<CarDO> carDOSpecification);
}
