package com.elbia.employeemanagement.domain.entity;

import com.elbia.employeemanagement.domain.constants.FuncionarioStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_FUNCIONARIO")
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    @Column(name = "cpf", nullable = false,unique = true)
    private String cpf;
    private FuncionarioStatus status;
    private BigDecimal salario;
    private LocalDate rejectionDate;
    @ManyToOne
    @JoinColumn(name = "id_departamento",referencedColumnName = "id",nullable = false)
    private Departamento departamento;

    public void aumentaSalario(BigDecimal valor){
        this.salario = salario.add(valor);
    }
}
