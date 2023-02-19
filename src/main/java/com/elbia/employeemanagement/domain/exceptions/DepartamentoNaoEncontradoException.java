package com.elbia.employeemanagement.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartamentoNaoEncontradoException extends RuntimeException{

    public DepartamentoNaoEncontradoException(String msg){
        super(msg);
    }
}