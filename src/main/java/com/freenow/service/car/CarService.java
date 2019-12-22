package com.freenow.service.car;

import com.freenow.domainobject.CarDO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

public interface CarService
{

    Optional<CarDO> find(Long id);

    List<CarDO> findAll();

    List<CarDO> findAll(Specification<CarDO> carDOSpecification);
}
