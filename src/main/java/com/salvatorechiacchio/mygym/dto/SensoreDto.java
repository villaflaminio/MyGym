package com.salvatorechiacchio.mygym.dto;

import com.salvatorechiacchio.mygym.model.Palestra;

import javax.validation.constraints.Size;

@ApiModel()
public class SensoreDto extends AbstractDto<Long> {
    private Long id;
    private Palestra palestra;
    @Size(max = 50)
    private String nome;
    private Float misurazione;

    public SensoreDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setPalestra(Palestra palestra) {
        this.palestra = palestra;
    }

    public Palestra getPalestra() {
        return this.palestra;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setMisurazione(Float misurazione) {
        this.misurazione = misurazione;
    }

    public Float getMisurazione() {
        return this.misurazione;
    }
}