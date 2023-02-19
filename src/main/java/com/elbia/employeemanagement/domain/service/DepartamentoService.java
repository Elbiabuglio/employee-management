package com.elbia.employeemanagement.domain.service;

import com.elbia.employeemanagement.domain.entity.Departamento;
import com.elbia.employeemanagement.domain.entity.Funcionario;

import java.util.List;

public interface DepartamentoService {
    public Departamento pegarPorId(Long id);

    public Departamento pegarPorIdComListaDeFuncionariosAtivos(Long id);

    public List<Departamento> listarTodos();

    public Departamento salvarDepartamento(Departamento departamento);

    public Funcionario adicionaFuncionario(Long idDepartamento, Funcionario funcionario);

}
