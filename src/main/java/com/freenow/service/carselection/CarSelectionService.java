package com.freenow.service.carselection;

import com.freenow.domainobject.CarDO;
import com.freenow.exception.CarNotFoundException;
import com.freenow.exception.DriverNotFoundException;
import com.freenow.exception.DriverOfflineException;
import org.springframework.data.jpa.domain.Specification;

public interface CarSelectionService {
  void selectCar(Long driverId, Specification<CarDO> carDO)
      throws DriverOfflineException, CarNotFoundException, DriverNotFoundException;

  void deselectCar(long driverId);
}
