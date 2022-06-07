package com.salvatorechiacchio.mygym.model.dto;


import com.salvatorechiacchio.mygym.dto.AbstractDto;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
@Data
public class SchedaAllenamentoDto implements Serializable {
    private Long id;
    @Size(max = 50)
    private String nome;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private long idUtente;
}