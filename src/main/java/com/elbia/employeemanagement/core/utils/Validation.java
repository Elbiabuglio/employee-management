package com.elbia.employeemanagement.core.utils;

import com.elbia.employeemanagement.domain.entity.Departamento;

import java.math.BigDecimal;

public final class Validation {
    private Validation(){}

    public static boolean validaOrcamento(Departamento departamento, BigDecimal valor){
        BigDecimal totalDeSalarios = departamento.getTotalSalarios();
        int comparacao = totalDeSalarios.add(valor).compareTo(departamento.getBudget());
        return comparacao < 1;
    }
}
