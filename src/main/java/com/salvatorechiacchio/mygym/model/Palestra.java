package com.salvatorechiacchio.mygym.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Palestra {

    @Id
    @GeneratedValue
    private Long id;


    @OneToMany(mappedBy = "palestra")
    private List<Abbonamento> abbonamenti;


    @OneToOne
    @JoinColumn(name = "sensore_id", referencedColumnName = "id")
    private Sensore sensore;


    @Column(name = "nome", length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String nome;

    @Column(name = "indirizzo", length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String indirizzo;

    @NotNull
    private Double latitudine;

    @NotNull
    private Double longitudine;

    private Boolean abilitato;
}
