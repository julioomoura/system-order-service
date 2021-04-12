package br.com.devdolls.sos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "USUARIO")
public class Usuario {
    @Id
    @Column(name = "usuario_id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "nome")
    private String nome;

    @OneToOne(mappedBy = "")
    private TipoUsuario tipo;
}
