package com.salvatorechiacchio.mygym.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

public class Abbonamento {
    @Id
    @GeneratedValue
    private Long id;


    @Column(name = "nome", length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String nome;

    private Float costo;

    @NotNull
    private LocalDate dataInizio;

    @NotNull
    private LocalDate dataFine;

    private Boolean pagato;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    @JsonBackReference()
    private User utente;

    @ManyToOne
    @JoinColumn()
    @JsonBackReference(value = "abbonamenti")
    private Palestra palestra;
}
