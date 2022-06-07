package com.salvatorechiacchio.mygym.model.dto.filter;

import lombok.Data;

import java.io.Serializable;

@Data
public class PalestraDtoFilter implements Serializable {
    private String nome;
    private String indirizzo;
    private String latitudine;
    private String longitudine;
    private Boolean abilitato ;
}