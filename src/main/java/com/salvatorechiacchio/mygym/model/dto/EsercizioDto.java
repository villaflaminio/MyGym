package com.salvatorechiacchio.mygym.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class EsercizioDto implements Serializable {
    private Long id;
    @Size(max = 50)
    private String nome;
    private String foto;
    private int tempoRecupero;
    private int numeroRipetizioni;
}