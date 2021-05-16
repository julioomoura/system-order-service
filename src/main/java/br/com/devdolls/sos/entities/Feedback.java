package br.com.devdolls.sos.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity(name = "feedback")
@Data
public class Feedback {
    @Id
    @Column(name = "feedback_id")
    private Integer id;

    @Column(name = "desc", columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "autor", nullable = false)
    private Usuario autor;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataDeCriacao;

    @ManyToOne
    @JoinColumn(name = "os", nullable = false)
    private OrdemDeServico ordemDeServico;
}
