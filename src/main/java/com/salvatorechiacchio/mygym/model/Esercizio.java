package com.salvatorechiacchio.mygym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Esercizio {
    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "esercizi")
    private List<SchedaAllenamento> schedeAllenamento;

    @Column(name = "nome", length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String nome;

    @Lob
    private String foto;

    @NotNull
    private int tempoRecupero;

    @NotNull
    private int numeroRipetizioni;


}
