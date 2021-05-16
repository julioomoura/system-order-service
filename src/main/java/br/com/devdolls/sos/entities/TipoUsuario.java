package br.com.devdolls.sos.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tipo_usuario")
@Data
public class TipoUsuario {
    @Id
    @Column(name = "tipo_id")
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;
}
