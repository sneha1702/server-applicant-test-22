package com.freenow.service.carselection;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarNotFoundException;
import com.freenow.exception.DriverNotFoundException;
import com.freenow.exception.DriverOfflineException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public synchronized void deselectCar(long driverId) throws DriverNotFoundException
    {
        Optional<DriverDO> optionalDriverDO = driverRepository.findById(driverId);
        if (optionalDriverDO.isPresent())
        {
            DriverDO selDriver = optionalDriverDO.get();
            CarDO selCar = selDriver.getCar();

            selCar.setSelected(false);
            selDriver.setCar(selCar);
            driverRepository.save(selDriver);

        }
        else
        {
            throw new DriverNotFoundException("Driver with Id " + driverId + " not found!");
        }
    }


    private synchronized void driverGetToSelectACar(DriverDO driverDO, Specification<CarDO> carDO)
        throws CarNotFoundException
    {
        Optional<CarDO> anyCar = carRepository.findAll(carDO).stream().filter(e -> e.getSelected() == null || e.getSelected().equals(false)).findAny();
        if (anyCar.isPresent())
        {
            CarDO firstCar = anyCar.get();
            firstCar.setSelected(true);
            driverDO.setCar(firstCar);

            driverRepository.save(driverDO);
            //        carRepository.save(firstCar);
        }
        else
        {
            throw new CarNotFoundException("Car is unavailable");
        }
    }
}
