package com.salvatorechiacchio.mygym.model.dto.filter;

import lombok.Data;

import java.io.Serializable;

@Data
public class EsercizioDtoFilter implements Serializable {
    private Long id;
    private String nome;
    private String foto;
    private int tempoRecupero;
    private int numeroRipetizioni;
}