package com.freenow.service.carselection;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.CarNotFoundException;
import com.freenow.exception.DriverNotFoundException;
import com.freenow.exception.DriverOfflineException;
import java.util.Collections;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import static com.freenow.dataaccessobject.CarCriteriaSpecs.withLicensePlate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarSelectionServiceTest
{

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private CarSelectionService carSelectionService;
    @Mock
    private DriverRepository driverRepo;
    @Mock
    private CarRepository carRepo;


    @Before
    public void setup()
    {
        carSelectionService = new DefaultCarSelectionService(driverRepo, carRepo);
    }


    @Test
    public void testWhenAnOffineDriverSelectsACar()
        throws DriverOfflineException, CarNotFoundException, DriverNotFoundException, CarAlreadyInUseException
    {
        // arrange

        when(driverRepo.findById(1L))
            .thenReturn(
                Optional.of(DriverDO.builder().id(1L).onlineStatus(OnlineStatus.OFFLINE).build()));

        expectedException.expect(DriverOfflineException.class);
        carSelectionService.selectCar(1L, Specification.where(withLicensePlate("AB123")));
    }


    @Test
    public void testWhenAnOnlineDriverSelectsAValidCar()
        throws CarNotFoundException, DriverOfflineException, DriverNotFoundException, CarAlreadyInUseException
    {
        // arrange

        when(driverRepo.findById(1L))
            .thenReturn(
                Optional.of(DriverDO.builder().id(1L).onlineStatus(OnlineStatus.ONLINE).build()));
        when(carRepo.findAll(any()))
            .thenReturn(
                Collections.singletonList(
                    CarDO.builder().id(1L).licensePlate("ABC123").selected(false).build()));
        // act
        carSelectionService.selectCar(1L, Specification.where(withLicensePlate("AB123")));

        // assert
        CarDO selCar = CarDO.builder().id(1L).licensePlate("ABC123").selected(true).build();
        //        verify(carRepo).save(selCar);
        verify(driverRepo)
            .save(DriverDO.builder().id(1L).onlineStatus(OnlineStatus.ONLINE).car(selCar).build());
    }


    @Test
    public void testWhenAnOnlineDriverSearchesForUnavailableCar()
        throws CarNotFoundException, DriverOfflineException, DriverNotFoundException, CarAlreadyInUseException
    {
        // arrange

        when(driverRepo.findById(1L))
            .thenReturn(
                Optional.of(DriverDO.builder().id(1L).onlineStatus(OnlineStatus.ONLINE).build()));
        when(carRepo.findAll(any())).thenReturn(Collections.emptyList());
        // act
        expectedException.expect(CarNotFoundException.class);
        carSelectionService.selectCar(1L, Specification.where(withLicensePlate("AB123")));

        // assert
        CarDO selCar = CarDO.builder().id(1L).licensePlate("ABC123").selected(true).build();
        verify(carRepo, never()).save(selCar);
        verify(driverRepo, never())
            .save(DriverDO.builder().id(1L).onlineStatus(OnlineStatus.ONLINE).car(selCar).build());
    }


    @Test
    public void testCarIsAvailableForSelectionWhenItsDeselected()
        throws CarNotFoundException, DriverOfflineException, DriverNotFoundException, CarAlreadyInUseException
    {

        when(driverRepo.findById(1L))
            .thenReturn(
                Optional.of(
                    DriverDO.builder()
                        .id(1L)
                        .onlineStatus(OnlineStatus.ONLINE)
                        .car(CarDO.builder().id(123L).licensePlate("ABC123").selected(true).build())
                        .build()));
        when(carRepo.findAll(any()))
            .thenReturn(
                Collections.singletonList(
                    CarDO.builder().id(123L).licensePlate("ABC123").selected(true).build()));

        carSelectionService.deselectCar(1L);

        when(carRepo.findAll(any()))
            .thenReturn(
                Collections.singletonList(
                    CarDO.builder().id(123L).licensePlate("ABC123").selected(false).build()));
        carSelectionService.selectCar(1L, Specification.where(withLicensePlate("ABC123")));

        // assert
        CarDO selCar = CarDO.builder().id(1L).licensePlate("ABC123").selected(true).build();
        verify(carRepo, never()).save(selCar);
        verify(driverRepo, never())
            .save(DriverDO.builder().id(1L).onlineStatus(OnlineStatus.ONLINE).car(selCar).build());
    }


}
