package com.freenow.controller;

import com.freenow.controller.mapper.DriverMapper;
import com.freenow.dataaccessobject.CarCriteriaSpecs;
import com.freenow.dataaccessobject.DriverCriteriaSpecs;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.EngineType;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarNotFoundException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.DriverNotFoundException;
import com.freenow.exception.DriverOfflineException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.carselection.CarSelectionService;
import com.freenow.service.driver.DriverService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
@AllArgsConstructor
public class DriverController
{

    private final DriverService driverService;
    private final CarSelectionService carSelectionService;


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @PutMapping("/selectCar/{driverId}")
    public void selectCar(
        @PathVariable long driverId, @RequestParam(required = false) String licensePlate)
        throws CarNotFoundException, DriverOfflineException, DriverNotFoundException

    {
        carSelectionService.selectCar(driverId, CarCriteriaSpecs.withLicensePlate(licensePlate));
    }


    @PutMapping("/deselectCar/{driverId}")
    public void deselectCar(
        @PathVariable long driverId)
        throws DriverNotFoundException

    {
        carSelectionService.deselectCar(driverId);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }


    @GetMapping("/filter")
    public List<DriverDTO> findDrivers(
        @RequestParam(required = false) OnlineStatus onlineStatus,
        @RequestParam(required = false
        ) String username,
        @RequestParam(required = false) String licensePlate,
        @RequestParam(required = false) String rating,
        @RequestParam(required = false) EngineType engineType,
        @RequestParam(required = false) Boolean convertible,
        @RequestParam(required = false) Boolean selected)
    {
        Specification<DriverDO> and = DriverCriteriaSpecs.withUsername(username).
            and(DriverCriteriaSpecs.withOnlineStatus(onlineStatus)).
            and(DriverCriteriaSpecs.withLicensePlate(licensePlate))
            .and(DriverCriteriaSpecs.withConvertible(convertible)
                .and(DriverCriteriaSpecs.withEngineType(engineType)).and(DriverCriteriaSpecs.withRating(rating).and(DriverCriteriaSpecs.withSelected(selected))));
        return DriverMapper.makeDriverDTOList(driverService.find(and));
    }


}
