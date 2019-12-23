package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "Car already in use!")
public class CarAlreadyInUseException extends Exception
{
    static final long serialVersionUID = -213545454555464565L;


    public CarAlreadyInUseException(String message)
    {
        super(message);
    }
}

