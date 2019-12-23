package com.freenow.controller.mapper;

import com.freenow.datatransferobject.DriverDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.freenow.controller.mapper.CarMapper.mapCarDO;
import static com.freenow.controller.mapper.CarMapper.mapCarDTO;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        // return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
        return DriverDO.builder()
            .username(driverDTO.getUsername())
            .password(driverDTO.getPassword())
            .onlineStatus(driverDTO.getOnlineStatus())
            .car(mapCarDO(driverDTO.getCar()))
            .build();
    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO)

    {
        DriverDTO builder = DriverDTO.builder()
            .id(driverDO.getId())
            .username(driverDO.getUsername())
            .password(driverDO.getPassword())
            .onlineStatus(driverDO.getOnlineStatus())
            .coordinate(driverDO.getCoordinate())
            .car(mapCarDTO(driverDO.getCar()))
            .build();

        //        DriverDTO.DriverDTOBuilder driverDTOBuilder =
        //            DriverDTO.newBuilder()
        //                .setId(driverDO.getId())
        //        .setPassword(driverDO.getPassword())
        //        .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            builder.setCoordinate(coordinate);
        }

        //        return driverDTOBuilder.createDriverDTO();
        return builder;
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream().map(DriverMapper::makeDriverDTO).collect(Collectors.toList());
    }
}
