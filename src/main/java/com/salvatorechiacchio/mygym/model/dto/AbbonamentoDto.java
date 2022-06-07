package com.salvatorechiacchio.mygym.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AbbonamentoDto implements Serializable {
    private Long id;
    @NotNull
    @Size(min = 1, max = 50)
    private String nome;
    private Float costo;
    @NotNull
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Boolean pagato;
    private Long idUtente;
    private Long idPalestra;
}