package com.salvatorechiacchio.mygym.model.dto;


import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class PalestraDto implements Serializable {
    @Size(max = 50)
    private String nome;
    @Size(max = 50)
    private String indirizzo;
    private String latitudine;
    private String longitudine;

    private long idSensore;

}