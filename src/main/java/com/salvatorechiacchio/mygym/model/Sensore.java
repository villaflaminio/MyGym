package com.salvatorechiacchio.mygym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Sensore {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "sensore")
    private Palestra palestra;

}
