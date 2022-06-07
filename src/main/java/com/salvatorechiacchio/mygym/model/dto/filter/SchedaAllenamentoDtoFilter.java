package com.salvatorechiacchio.mygym.model.dto.filter;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SchedaAllenamentoDtoFilter implements Serializable {
    private Long id;
    private String nome;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private long idUtente;
}