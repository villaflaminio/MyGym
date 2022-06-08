package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.model.dto.PalestraDto;
import com.salvatorechiacchio.mygym.service.PalestraService;
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
 * Controller per la gestione delle palestre.
 * Permette di eseguire tutte le operazioni CRUD sulle palestre.
 */
@RequestMapping("/api/palestra")
@RestController
@Slf4j
@Tag(name = "Palestra")
public class PalestraController {
    @Autowired
    private PalestraService palestraService;

    /**
     * @return Tutte le palestre presenti nel database.
     */
    @Operation(summary = "findAll", description = "Ottieni tutte le palestre")
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(palestraService.findAll());
    }

    /**
     * @param id Identificativo della palestra da ottenere.
     * @return La palestra con l'identificativo specificato.
     */
    @Operation(summary = "findById", description = "Ritorna palestra con identificativo passato")
    @GetMapping("/{id}")
    public ResponseEntity<Palestra> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(palestraService.findById(id));
    }

    /**
     * @param palestraDto Dto contenente i dati della palestra da inserire.
     * @return La palestra inserita.
     */
    @Operation(summary = "save", description = "Inserisce una palestra")
    @PostMapping
    public ResponseEntity<Palestra> save(@RequestBody @Validated PalestraDto palestraDto) {
        return ResponseEntity.ok(palestraService.save(palestraDto));
    }

    /**
     * @param id Identificativo della palestra da eliminare.
     */
    @Operation(summary = "delete", description = "Elimina palestra con identificativo passato")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(palestraService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        palestraService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param palestraDto Dto contenente i dati della palestra da modificare.
     * @param id          Identificativo della palestra da modificare.
     * @return La palestra modificata.
     */
    @Operation(summary = "update", description = "Modifica palestra con identificativo passato")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody PalestraDto palestraDto, @PathVariable("id") Long id) {
        palestraService.update(palestraDto, id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param probe         Dto contenente i dati della palestra da filtrare.
     * @param page          Pagina da visualizzare
     * @param size          Numero di elementi per pagina
     * @param sortField     Campo per ordinamento
     * @param sortDirection Direzione di ordinamento
     * @return La pagina di risultati della ricerca.
     */
    @Operation(summary = "filter", description = "Filtra le palestre")
    @PostMapping("/filter")
    ResponseEntity<Page<Palestra>> filter(
            @RequestBody(required = false) Palestra probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return palestraService.filter(probe, page, size, sortField, sortDirection);
    }
}