package com.elbia.employeemanagement.domain.service;

import com.elbia.employeemanagement.domain.entity.Funcionario;

import java.math.BigDecimal;

public interface FuncionarioService {

    public Funcionario aumentaSalario(Long id, BigDecimal valor);

}