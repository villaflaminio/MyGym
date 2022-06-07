package com.salvatorechiacchio.mygym.dto;


import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class PalestraDto extends AbstractDto<Long> {
    @Size(max = 50)
    private String nome;
    @Size(max = 50)
    private String indirizzo;
    private String latitudine;
    private String longitudine;

}