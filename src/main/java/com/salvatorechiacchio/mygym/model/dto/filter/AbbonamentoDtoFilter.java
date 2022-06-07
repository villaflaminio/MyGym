package com.salvatorechiacchio.mygym.model.dto.filter;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AbbonamentoDtoFilter implements Serializable {
    private Long id;
    private String nome;
    private Float costo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Boolean pagato;
    private Long idUtente;
    private Long idPalestra;
}