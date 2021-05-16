package br.com.devdolls.sos.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "os")
@Data
public class OrdemDeServico {

    @Id
    @Column(name = "os_id")
    private Integer id;

    @Column(name = "desc", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_abertura", nullable = false)
    private LocalDate dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDate dataFechamento;

    @Column(name = "data_inicio_atend")
    private LocalDate dataInicioAtendimento;

    @Column(name = "prazo")
    private Integer prazoParaConclusao;

    @ManyToOne
    @JoinColumn(name = "cliente", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "dev", nullable = false)
    private Usuario desenvolvedor;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;

    @Column(name = "justificativa", columnDefinition = "TEXT")
    private String justificativa;

    @Column(name = "assunto", columnDefinition = "TEXT")
    private String assunto;

    @OneToMany(mappedBy = "ordemDeServico")
    private List<Feedback> feedbacks;

}
