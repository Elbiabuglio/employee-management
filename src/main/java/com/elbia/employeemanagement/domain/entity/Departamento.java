package com.elbia.employeemanagement.domain.entity;

import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "TB_DEPARTAMENTO")
public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String descricao;
    private BigDecimal budget;
    private BigDecimal totalSalarios;
    @OneToMany(mappedBy = "departamento",cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getTotalSalarios() {
        return totalSalarios;
    }

    public void setTotalSalarios(BigDecimal totalSalarios) {
        this.totalSalarios = totalSalarios;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
        atualizarTotalSalarios();
    }

    private void atualizarTotalSalarios() {
        BigDecimal newTotal = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            newTotal = newTotal.add(funcionario.getSalario());
        }
        this.totalSalarios = newTotal;
    }

    public void contratarFuncionario(Funcionario funcionario) {
        if (totalSalarios.compareTo(budget) < 0) {
            funcionarios.add(funcionario);
            atualizarTotalSalarios();

        } else {

            throw new IllegalStateException("Não é possível contratar funcionarios, o total de salários é maior do que o orcamento");
        }
    }

    public void increaseSalarioFuncionario(Funcionario funcionario, BigDecimal increase) {
        if (totalSalarios.compareTo(budget) < 0) {
            funcionario.setSalario(funcionario.getSalario().add(increase));
            atualizarTotalSalarios();
        } else {
            throw new IllegalStateException("Não é possível aumentar salários, o total de salários é maior do que o budget");
        }
    }
}




