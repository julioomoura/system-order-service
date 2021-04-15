package br.com.devdolls.sos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity(name = "feedback")
public class Feedback {
    @Id
    @Column(name = "feedback_id")
    private Integer id;

    @Column(name = "desc", columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario autor;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataDeCriacao;

    @ManyToOne
    @JoinColumn(name = "os", nullable = false)
    private OrdemDeServico ordemDeServico;
}
