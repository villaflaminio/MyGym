package org.elis.coreproject.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MisurazioneDTO implements Serializable {
    private Long idSensore;
    private Float misurazione;
}