package br.com.devdolls.sos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "usuario_id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipo;

    @OneToMany(mappedBy = "os")
    private List<OrdemDeServico> ordensDeServico;
}
