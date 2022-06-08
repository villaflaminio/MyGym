package com.salvatorechiacchio.mygym.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Sensore {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "sensore")
    @JsonBackReference(value = "sensore")
    private Palestra palestra;

    @Column(name = "nome", length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    private String nome;

    @OneToMany(mappedBy = "sensore")
    private List<Misurazione> misurazioni;
}
