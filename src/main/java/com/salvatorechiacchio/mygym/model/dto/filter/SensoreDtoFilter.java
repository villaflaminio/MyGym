package com.salvatorechiacchio.mygym.model.dto.filter;

import lombok.Data;

import java.io.Serializable;

@Data
public class SensoreDtoFilter implements Serializable {
    private Long id;
    private String nome;
    private Float misurazione;
    private long idPalestra;
}