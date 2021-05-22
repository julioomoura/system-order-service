package br.com.devdolls.sos.entities;

import br.com.devdolls.sos.entities.enums.Status;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"desc\"", columnDefinition = "TEXT")
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

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "dev")
    private Usuario desenvolvedor;

    @Column(name = "justificativa", columnDefinition = "TEXT")
    private String justificativa;

    @Column(name = "assunto", columnDefinition = "TEXT")
    private String assunto;

    @OneToMany(mappedBy = "ordemDeServico")
    private List<Feedback> feedbacks;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;
}
