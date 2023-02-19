package com.elbia.employeemanagement.core.serviceimpl;

import com.elbia.employeemanagement.domain.entity.Departamento;
import com.elbia.employeemanagement.domain.entity.Funcionario;
import com.elbia.employeemanagement.domain.exceptions.CpfJaRegistradoException;
import com.elbia.employeemanagement.domain.exceptions.DepartamentoNaoEncontradoException;
import com.elbia.employeemanagement.domain.repository.DepartamentoRepository;
import com.elbia.employeemanagement.domain.repository.FuncionarioRepository;
import com.elbia.employeemanagement.domain.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.elbia.employeemanagement.core.utils.Validation.validaOrcamento;
import static com.elbia.employeemanagement.domain.constants.FuncionarioStatus.ATIVO;
import static com.elbia.employeemanagement.domain.constants.FuncionarioStatus.REJEITADO_POR_FALTA_DE_RECURSOS;

@Primary
@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;


    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Departamento pegarPorId(Long id){
        return departamentoRepository
                .findById(id)
                .orElseThrow(()->new DepartamentoNaoEncontradoException("Departamento não encontrado!"));
    }

    @Override
    public Departamento pegarPorIdComListaDeFuncionariosAtivos(Long id){
        var departamento = departamentoRepository
                .findById(id)
                .orElseThrow(()->new DepartamentoNaoEncontradoException("Departamento não encontrado!"));
        departamento.setFuncionarios(
                departamento.getFuncionarios().stream()
                        .filter(funcionario -> funcionario.getStatus() == ATIVO)
                        .collect(Collectors.toList()));
        return departamento;
    }

    @Override
    public List<Departamento> listarTodos(){
        return departamentoRepository.findAll();
    }

    @Override
    public Departamento salvarDepartamento(Departamento departamento){
        return departamentoRepository.save(departamento);
    }

    @Override
    public Funcionario adicionaFuncionario(Long idDepartamento,Funcionario funcionario){
        Departamento departamento = validacao(idDepartamento,funcionario);
        if(validaOrcamento(departamento,funcionario.getSalario())) {
            departamento.aumentaTotalSalarios(funcionario.getSalario());
            funcionario.setStatus(ATIVO);
            departamento.adicionaFuncionario(funcionario);
        } else{
            funcionario.setStatus(REJEITADO_POR_FALTA_DE_RECURSOS);
            funcionario.setRejectionDate(LocalDate.now());
        }
        return salvarFuncionario(departamento,funcionario);
    }

    private Departamento validacao(Long idDepartamento, Funcionario funcionario){
        Departamento departamento = departamentoRepository
                .findById(idDepartamento)
                .orElseThrow(()->new DepartamentoNaoEncontradoException("Departamento não encontrado!"));
        funcionario.setDepartamento(departamento);
        if(!funcionarioRepository.findByCpf(funcionario.getCpf()).isEmpty())
            throw new CpfJaRegistradoException("Por favor, o número de CPF já está registrado em nosso sistema");
        return departamento;
    }

    private Funcionario salvarFuncionario(Departamento departamento, Funcionario funcionario){
        departamento.adicionaFuncionario(funcionario);
        return departamentoRepository.save(departamento)
                .getFuncionarios().stream()
                .filter(func -> Objects.equals(func.getCpf(), funcionario.getCpf())).findFirst().get();
    }
}