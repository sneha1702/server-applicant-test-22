package com.freenow.service.carselection;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarNotFoundException;
import com.freenow.exception.DriverNotFoundException;
import com.freenow.exception.DriverOfflineException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.freenow.dataaccessobject.CarCriteriaSpecs.withSelected;

@Service
@AllArgsConstructor
public class DefaultCarSelectionService implements CarSelectionService
{

    private final DriverRepository driverRepository;
    private final CarRepository carRepository;


    @Override
    public void selectCar(Long driverId, Specification<CarDO> carDO)
        throws DriverOfflineException, CarNotFoundException, DriverNotFoundException
    {
        Optional<DriverDO> optionalDriverDO = driverRepository.findById(driverId);
        if (optionalDriverDO.isPresent())
        {
            DriverDO selDriver = optionalDriverDO.get();
            if (selDriver.getOnlineStatus() == OnlineStatus.ONLINE)
            {
                driverGetToSelectACar(selDriver, carDO);
            }
            else
            {
                throw new DriverOfflineException("Cannot allow driver to select car. DriverId " + driverId);
            }
        }
        else
        {
            throw new DriverNotFoundException("Driver with Id " + driverId + " not found!");
        }
    }


    @Override
    public void deselectCar(long driverId)
    {
    }


    private synchronized void driverGetToSelectACar(DriverDO driverDO, Specification<CarDO> carDO)
        throws CarNotFoundException
    {
        List<CarDO> all = carRepository.findAll(carDO.and(withSelected(Boolean.FALSE)));
        if (all.size() == 0)
        {
            throw new CarNotFoundException();
        }
        CarDO firstCar = all.get(0);
        driverDO.setCar(firstCar);
        firstCar.setSelected(true);

        driverRepository.save(driverDO);
        carRepository.save(firstCar);
    }
}
