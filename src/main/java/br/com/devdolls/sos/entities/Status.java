package br.com.devdolls.sos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "status")
public class Status {
    @Id
    @Column(name = "status_id")
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;
}
