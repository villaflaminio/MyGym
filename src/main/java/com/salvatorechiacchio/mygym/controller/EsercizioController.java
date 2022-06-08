package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.model.dto.EsercizioDto;
import com.salvatorechiacchio.mygym.service.EsercizioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller per la gestione degli esercizi.
 * Permette di eseguire tutte le operazioni CRUD sugli esercizi.
 */
@RequestMapping("/api/esercizio")
@RestController
@Slf4j
@Tag(name = "Esercizio")
public class EsercizioController {
    @Autowired
    private EsercizioService esercizioService;

    /**
     * @return Tutti gli esercizi presenti nel database.
     */
    @Operation(summary = "findAll", description = "Ottieni tutti gli esercizi")
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(esercizioService.findAll());
    }

    /**
     * @param esercizioDto Dto contenente i dati dell'esercizio da salvare.
     * @return L'esercizio salvato.
     */
    @Operation(summary = "save", description = "Salva un esercizio")
    @PostMapping
    public ResponseEntity<Esercizio> save(@RequestBody @Validated EsercizioDto esercizioDto) {
        return ResponseEntity.ok(esercizioService.save(esercizioDto));
    }

    /**
     * @param id Identificativo dell'esercizio da ottenere.
     * @return L'esercizio con l'identificativo specificato.
     * @throws ResourceNotFoundException Se non esiste un esercizio con l'identificativo specificato.
     */
    @Operation(summary = "findById", description = "Ritorna esercizio con identificativo passato")
    @GetMapping("/{id}")
    public ResponseEntity<Esercizio> findById(@PathVariable("id") Long id) {
        Esercizio esercizio = esercizioService.findById(id);
        return ResponseEntity.ok(esercizio);
    }

    /**
     * @param id Identificativo dell'esercizio da eliminare.
     */
    @Operation(summary = "delete", description = "Elimina esercizio con identificativo passato")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(esercizioService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data!");
            return new ResourceNotFoundException();
        });
        esercizioService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param esercizioDto Dto contenente i dati dell'esercizio da aggiornare.
     * @param id           Identificativo dell'esercizio da aggiornare.
     * @return L'esercizio aggiornato.
     */
    @Operation(summary = "update", description = "Aggiorna esercizio con identificativo passato")
    @PutMapping("/{id}")
    public ResponseEntity<Esercizio> update(@RequestBody EsercizioDto esercizioDto, @PathVariable("id") Long id) {
        return ResponseEntity.ok(esercizioService.update(esercizioDto, id));
    }

    /**
     * @param probe         Dto contenente i dati dell'esercizio da cercare.
     * @param page          Pagina dei risultati.
     * @param size          Numero di risultati per pagina.
     * @param sortField     Campo per ordinare i risultati.
     * @param sortDirection Direzione di ordinamento.
     * @return I risultati paginati
     */
    @Operation(summary = "filter", description = "Filtra esercizi con i parametri specificati")
    @PostMapping("/filter")
    ResponseEntity<Page<Esercizio>> filter(
            @RequestBody(required = false) Esercizio probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return esercizioService.filter(probe, page, size, sortField, sortDirection);
    }
}