package com.salvatorechiacchio.mygym.model.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class PalestraDto implements Serializable {
    @Size(max = 50)
    @NotNull
    private String nome;
    @Size(max = 50)
    @NotNull
    private String indirizzo;
    @NotNull
    private Double latitudine;
    @NotNull
    private Double longitudine;
    private Boolean abilitato;
}