package com.salvatorechiacchio.mygym.dto;

import com.salvatorechiacchio.mygym.annotation.CheckDate;
import com.salvatorechiacchio.mygym.model.User;

import javax.validation.constraints.Size;

@ApiModel()
public class SchedaAllenamentoDto extends AbstractDto<Long> {
    private Long id;
    private List<Esercizio> esercizi;
    private User utente;
    @Size(max = 50)
    private String nome;
    @CheckDate
    private LocalDate dataInizio;
    @CheckDate
    private LocalDate dataFine;

    public SchedaAllenamentoDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setEsercizi(java.util.List<com.salvatorechiacchio.mygym.model.Esercizio> esercizi) {
        this.esercizi = esercizi;
    }

    public java.util.List<com.salvatorechiacchio.mygym.model.Esercizio> getEsercizi() {
        return this.esercizi;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }

    public User getUtente() {
        return this.utente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setDataInizio(java.time.LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public java.time.LocalDate getDataInizio() {
        return this.dataInizio;
    }

    public void setDataFine(java.time.LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public java.time.LocalDate getDataFine() {
        return this.dataFine;
    }
}