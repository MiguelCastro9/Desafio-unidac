package com.desafioUnidac.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author Miguel Castro
 */
@Entity
@Data
@Table(name = "Pessoa")
public class Pessoa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", nullable = false, length = 45)
    @Size(min = 3, max = 45, message = "Seu nome deve conter entre {min} a {max} caracteres.")
    private String nome;
    
    @Column(name = "cpf", nullable = false)
    @CPF(message = "Por favor, informe um CPF válido.")
    private String cpf;
    
    @Column(name = "alimento", nullable = false, length = 45)
    @Size(min = 3, max = 45, message = "O nome do alimento deve conter entre {min} a {max} caracteres.")
    private String alimento;
    
}
