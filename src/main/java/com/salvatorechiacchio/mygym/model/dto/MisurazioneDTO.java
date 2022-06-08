package com.salvatorechiacchio.mygym.model.dto;

import lombok.*;
import java.io.Serializable;

@Data
public class MisurazioneDTO implements Serializable {
    private Long idSensore;
    private Float misurazione;
}