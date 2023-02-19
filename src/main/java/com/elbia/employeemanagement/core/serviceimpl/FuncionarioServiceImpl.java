package com.elbia.employeemanagement.core.serviceimpl;

import com.elbia.employeemanagement.domain.entity.Departamento;
import com.elbia.employeemanagement.domain.entity.Funcionario;
import com.elbia.employeemanagement.domain.exceptions.FaltaOrcamentoException;
import com.elbia.employeemanagement.domain.exceptions.FuncionarioNaoEncontradoException;
import com.elbia.employeemanagement.domain.repository.FuncionarioRepository;
import com.elbia.employeemanagement.domain.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.elbia.employeemanagement.core.utils.Validation.validaOrcamento;

@Service
@Primary
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario aumentaSalario(Long id, BigDecimal valor){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(()-> new FuncionarioNaoEncontradoException("Esse funcionário não está em nossa base de dados"));
        Departamento departamento =funcionario.getDepartamento();
        if(validaOrcamento(funcionario.getDepartamento(),valor)) {
            departamento.aumentaTotalSalarios(valor);
            funcionario.aumentaSalario(valor);
            departamento.adicionaFuncionario(funcionario);
            return funcionarioRepository.save(funcionario);
        }
        throw new FaltaOrcamentoException("Orçamento insuficiente para realizar aumento");
    }

}
