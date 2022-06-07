package com.salvatorechiacchio.mygym.dto;

import com.salvatorechiacchio.mygym.model.Sensore;

import javax.validation.constraints.Size;

@ApiModel()
public class PalestraDto extends AbstractDto<Long> {
    private Long id;
    private List<Abbonamento> abbonamenti;
    private Sensore sensore;
    @Size(max = 50)
    private String nome;
    @Size(max = 50)
    private String indirizzo;
    private String latitudine;
    private String longitudine;

    public PalestraDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setAbbonamenti(java.util.List<com.salvatorechiacchio.mygym.model.Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    public java.util.List<com.salvatorechiacchio.mygym.model.Abbonamento> getAbbonamenti() {
        return this.abbonamenti;
    }

    public void setSensore(Sensore sensore) {
        this.sensore = sensore;
    }

    public Sensore getSensore() {
        return this.sensore;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLatitudine() {
        return this.latitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public String getLongitudine() {
        return this.longitudine;
    }
}