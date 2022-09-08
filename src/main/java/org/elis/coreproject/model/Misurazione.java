package org.elis.coreproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Misurazione {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private Float misurazione;

    @ManyToOne
    @JoinColumn(name = "sensore_id", nullable = false)
    @JsonBackReference()
    private Sensore sensore;
}