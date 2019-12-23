package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.freenow.domainvalue.EngineType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "license plate can not be null!")
    private String licensePlate;

    @NotNull(message = "seat count can not be null!")
    private Integer seatCount;

    @NotNull(message = "convertible can not be null!")
    private Boolean convertible;

    @NotNull(message = "rating can not be null!")
    private Integer rating;

    @NotNull(message = "engineType can not be null!")
    private EngineType engineType;

    private Boolean selected;

    // license_plate, seat_count, convertible, rating, engine_type
}
