package com.elbia.employeemanagement.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfJaRegistradoException extends RuntimeException{

    public CpfJaRegistradoException(String msg){
        super(msg);
    }
}