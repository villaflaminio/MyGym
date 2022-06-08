package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Abbonamento;
import com.salvatorechiacchio.mygym.model.dto.AbbonamentoDto;
import com.salvatorechiacchio.mygym.model.dto.filter.AbbonamentoDtoFilter;
import com.salvatorechiacchio.mygym.service.AbbonamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller per la gestione degli abbonamenti.
 * Permette di eseguire tutte le operazioni CRUD sugli abbonamenti.
 */
@RequestMapping("/api/abbonamento")
@RestController
@Tag(name = "Abbonamento")
public class AbbonamentoController {

    @Autowired
    AbbonamentoService abbonamentoService;

    /**
     * @return Tutti gli abbonamenti presenti nel database.
     */
    @Operation(summary = "findAll", description = "Ottieni tutti gli abbonamenti")
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(abbonamentoService.findAll());
    }

    /**
     * @param id Identificativo dell'abbonamento da ottenere.
     * @return L'abbonamento con l'identificativo specificato.
     */
    @Operation(summary = "findById", description = "Ritorna abbonamento con identificativo passato")
    @GetMapping("/{id}")
    public ResponseEntity<Abbonamento> findById(@PathVariable("id") Long id) {
        Abbonamento abbonamento = abbonamentoService.findById(id);
        return ResponseEntity.ok(abbonamento);
    }

    /**
     * @param abbonamentoDto Dto contenente i dati dell'abbonamento da inserire.
     * @return L'abbonamento inserito.
     */
    @Operation(summary = "save", description = "Crea un nuovo abbonamento")
    @PostMapping
    public ResponseEntity<Abbonamento> save(@RequestBody @Validated AbbonamentoDto abbonamentoDto) {
        return ResponseEntity.ok(abbonamentoService.save(abbonamentoDto));
    }

    /**
     * @param id Identificativo dell'abbonamento da eliminare.
     */
    @Operation(summary = "delete", description = "Elimina un abbonamento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(abbonamentoService.findById(id)).orElseThrow(() -> {
            return new ResourceNotFoundException();
        });
        abbonamentoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param abbonamentoDto Dto contenente i dati dell'abbonamento da aggiornare.
     * @param id Identificativo dell'abbonamento da aggiornare.
     * @return L'abbonamento aggiornato.
     */
    @Operation(summary = "update", description = "Aggiorna un abbonamento")
    @PutMapping("/{id}")
    public ResponseEntity<Abbonamento> update(@RequestBody AbbonamentoDto  abbonamentoDto, @PathVariable("id") Long id) {
        return ResponseEntity.ok( abbonamentoService.update(abbonamentoDto, id));
    }

    /**
     * @param probe Dto contenente i dati dell'abbonamento da filtrare.
     * @param page Pagina dei risultati.
     * @param size Numero di risultati per pagina.
     * @param sortField Campo per ordinare i risultati.
     * @param sortDirection Direzione di ordinamento.
     * @return Lista di abbonamenti filtrati.
     */
    @Operation(summary = "filter", description = "Filtra gli abbonamenti")
    @PostMapping("/filter")
    ResponseEntity<Page<Abbonamento>> filter(
            @RequestBody(required = false) Abbonamento probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return abbonamentoService.filter(probe, page, size, sortField, sortDirection);
    }
}