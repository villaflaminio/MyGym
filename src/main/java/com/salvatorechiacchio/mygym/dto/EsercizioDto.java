package com.salvatorechiacchio.mygym.dto;

import javax.validation.constraints.Size;

@ApiModel()
public class EsercizioDto extends AbstractDto<Long> {
    private Long id;
    private List<SchedaAllenamento> schedeAllenamento;
    @Size(max = 50)
    private String nome;
    private String foto;
    private int tempoRecupero;
    private int numeroRipetizioni;

    public EsercizioDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setSchedeAllenamento(java.util.List<com.salvatorechiacchio.mygym.model.SchedaAllenamento> schedeAllenamento) {
        this.schedeAllenamento = schedeAllenamento;
    }

    public java.util.List<com.salvatorechiacchio.mygym.model.SchedaAllenamento> getSchedeAllenamento() {
        return this.schedeAllenamento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setTempoRecupero(int tempoRecupero) {
        this.tempoRecupero = tempoRecupero;
    }

    public int getTempoRecupero() {
        return this.tempoRecupero;
    }

    public void setNumeroRipetizioni(int numeroRipetizioni) {
        this.numeroRipetizioni = numeroRipetizioni;
    }

    public int getNumeroRipetizioni() {
        return this.numeroRipetizioni;
    }
}