package com.salvatorechiacchio.mygym.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class SensoreDto implements Serializable {
    private Long id;
    @Size(max = 50)
    private String nome;
    private Float misurazione;
    private long idPalestra;
}