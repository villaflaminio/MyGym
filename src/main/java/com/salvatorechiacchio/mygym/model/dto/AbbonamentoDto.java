package com.salvatorechiacchio.mygym.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AbbonamentoDto implements Serializable {
    private final Long id;
    @NotNull
    @Size(min = 1, max = 50)
    private String nome;
    private float costo;
    @NotNull
    private LocalDate dataInizio;
    @NotNull
    private LocalDate dataFine;
    private boolean pagato;

    private long idUtente;
    private long idPalestra;
}
