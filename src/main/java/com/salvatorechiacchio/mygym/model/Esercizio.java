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
public class Esercizio {
    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "esercizi")
    private List<SchedaAllenamento> schedeAllenamento;
}
