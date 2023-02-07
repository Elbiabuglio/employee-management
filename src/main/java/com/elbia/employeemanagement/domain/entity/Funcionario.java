package com.elbia.employeemanagement.domain.entity;

import com.elbia.employeemanagement.domain.constants.EmployeeStatus;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "TB_FUNCIONARIO")
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String cpf;
    private EmployeeStatus status;
    private BigDecimal salario;
    private BigDecimal budget;
    private LocalDate rejectionDate;
    @ManyToOne
    @JoinColumn(name = "id_departamento",referencedColumnName = "id",nullable = false)
    private Departamento departamento;

    public void aplicarRegraDois () {
        BigDecimal totalSalarios = departamento.getTotalSalarios().add(budget);
        if (totalSalarios.compareTo(departamento.getBudget()) <= 0) {
            this.status = EmployeeStatus.ATIVO;

        } else {

            this.status = EmployeeStatus.REJEITADO_POR_FALTA_DE_RECURSOS;
            this.rejectionDate = LocalDate.now();
        }
    }
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public EmployeeStatus getStatus() {
        return getStatus();
    }

    public double getBudget() {
        return getBudget();
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Data getDataRejection() {
        return getDataRejection();
    }

    public void setBudget(BigDecimal newBudget) {
        BigDecimal totalSalarios = departamento.getTotalSalarios().subtract(budget).add(newBudget);
        if (totalSalarios.compareTo(departamento.getBudget()) <= 0) {
            this.budget = newBudget;
        } else {
            System.out.println("Não é possível aumentar o salário. Total de salários excede o orçamento do departamento.");
        }
    }
}

