package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Car not found!")
public class CarNotFoundException extends Exception
{
    static final long serialVersionUID = -2133243435435464565L;


    public CarNotFoundException(String message)
    {
        super(message);
    }
}
