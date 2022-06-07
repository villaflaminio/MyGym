package com.salvatorechiacchio.mygym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class SchedaAllenamento {

    @JsonIgnore
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
    private User utente;
}
