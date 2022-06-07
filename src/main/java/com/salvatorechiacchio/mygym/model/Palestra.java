package com.salvatorechiacchio.mygym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Palestra {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;


    @OneToMany(mappedBy = "palestra")
    private List<Abbonamento> abbonamenti;


    @OneToOne
    @JoinColumn(name = "sensore_id")
    @RestResource(path = "sensorePalestra", rel="sensore")
    private Sensore sensore;
}
