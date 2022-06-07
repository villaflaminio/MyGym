package com.salvatorechiacchio.mygym.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class SchedaAllenamento {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "esercizi_schede_allenamento",
            joinColumns = @JoinColumn(name = "esercizio_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "scheda_allenamento_id",
                    referencedColumnName = "id"))
    private List<Esercizio> esercizi;


    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference(value = "schedaAllenamento")
    private User utente;


    @Column(name = "nome", length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String nome;

    @NotNull
    private LocalDate dataInizio;

    @NotNull
    private LocalDate dataFine;

}
