package com.salvatorechiacchio.mygym.model.dto.filter;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AbbonamentoDtoFilter implements Serializable {
    private Long id;
    private String nome;
    private float costo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private boolean pagato;
    private long idUtente;
    private long idPalestra;
}