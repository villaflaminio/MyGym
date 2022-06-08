package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.SchedaAllenamento;
import com.salvatorechiacchio.mygym.model.dto.SchedaAllenamentoDto;
import com.salvatorechiacchio.mygym.service.SchedaAllenamentoService;
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
 * Controller per la gestione delle schede di allenamento.
 * Permette di eseguire tutte le operazioni CRUD sulle schede di allenamento.
 */
@RequestMapping("/api/schedaAllenamento")
@RestController
@Slf4j
@Tag(name = "Scheda Allenamento")
public class SchedaAllenamentoController {
    @Autowired
    private SchedaAllenamentoService schedaAllenamentoService;

    /**
     * @return Tutte le schede di allenamento presenti nel database.
     */
    @Operation(summary = "findAll", description = "Ottieni tutte le schede di allenamento")
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(schedaAllenamentoService.findAll());
    }

    /**
     * @param schedaAllenamentoDto Dto contenente i dati della scheda di allenamento da salvare.
     * @return La scheda di allenamento salvata.
     */
    @Operation(summary = "save", description = "Salva una scheda di allenamento")
    @PostMapping
    public ResponseEntity<SchedaAllenamento> save(@RequestBody @Validated SchedaAllenamentoDto schedaAllenamentoDto) {
        return ResponseEntity.ok(schedaAllenamentoService.save(schedaAllenamentoDto));
    }

    /**
     * @param id Identificativo della scheda di allenamento da ottenere.
     * @return La scheda di allenamento con l'identificativo specificato.
     */
    @Operation(summary = "findById", description = "Ottieni la scheda di allenamento specificata dall'id")
    @GetMapping("/{id}")
    public ResponseEntity<SchedaAllenamento> findById(@PathVariable("id") Long id) {
        SchedaAllenamento schedaAllenamento = schedaAllenamentoService.findById(id);
        return ResponseEntity.ok(schedaAllenamento);
    }

    /**
     * @param id Identificativo della scheda di allenamento da eliminare.
     */
    @Operation(summary = "delete", description = "Elimina la scheda di allenamento specificata dall'id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(schedaAllenamentoService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        schedaAllenamentoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param schedaAllenamentoDto Dto contenente i dati della scheda di allenamento da aggiornare.
     * @param id Identificativo della scheda di allenamento da aggiornare.
     * @return La scheda di allenamento aggiornata.
     */
    @Operation(summary = "update", description = "Aggiorna la scheda di allenamento specificata dall'id")
    @PutMapping("/{id}")
    public ResponseEntity<SchedaAllenamento> update(@RequestBody SchedaAllenamentoDto schedaAllenamentoDto, @PathVariable("id") Long id) {
        schedaAllenamentoService.update(schedaAllenamentoDto, id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param probe Dto contenente i dati dell'esercizio da cercare.
     * @param page La pagina da cercare.
     * @param size La dimensione della pagina.
     * @param sortField Il campo per cui ordinare.
     * @param sortDirection La direzione di ordinamento.
     * @return La pagina di risultati della ricerca.
     */
    @Operation(summary = "filter", description = "Filtra schede di allenamento con i parametri specificati")
    @PostMapping("/filter")
    ResponseEntity<Page<SchedaAllenamento>> filter(
            @RequestBody(required = false) SchedaAllenamento probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return schedaAllenamentoService.filter(probe, page, size, sortField, sortDirection);
    }
}