package com.freenow.service.car;

import com.freenow.dataaccessobject.CarCriteriaSpecs;
import com.freenow.datatransferobject.CarDTO;
import java.util.List;
import java.util.Optional;

public interface CarService
{
    List<CarDTO> listAll();

    Optional<CarDTO> searchByCriteria(CarCriteriaSpecs carCriteria);
}
