package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Sensore;
import com.salvatorechiacchio.mygym.model.dto.MisurazioneDTO;
import com.salvatorechiacchio.mygym.model.dto.SensoreDto;
import com.salvatorechiacchio.mygym.service.SensoreService;
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
 * Controller per gestire le richieste relative ai sensori
 */
@RequestMapping("/api/sensore")
@RestController
@Slf4j
@Tag(name = "Sensore")
public class SensoreController {
    @Autowired
    private SensoreService sensoreService;

    /**
     * @param sensoreDto DTO del sensore da inserire
     * @return il sensore inserito
     */
    @Operation(summary = "save", description = "Inserisce un nuovo sensore")
    @PostMapping
    public ResponseEntity<Sensore> save(@RequestBody @Validated SensoreDto sensoreDto) {
        return ResponseEntity.ok(sensoreService.save(sensoreDto));
    }

    /**
     * @param id id del sensore da recuperare
     * @return il sensore recuperato
     */
    @Operation(summary = "findById", description = "Recupera un sensore per id")
    @GetMapping("/{id}")
    public ResponseEntity<Sensore> findById(@PathVariable("id") Long id) {
        Sensore sensore = sensoreService.findById(id);
        return ResponseEntity.ok(sensore);
    }

    /**
     * @return tutti i sensori
     */
    @Operation(summary = "findAll", description = "Recupera tutti i sensori")
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(sensoreService.findAll());
    }

    /**
     * @param id id del sensore da eliminare
     */
    @Operation(summary = "delete", description = "Elimina un sensore")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(sensoreService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        sensoreService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param sensoreDto DTO del sensore con i campi aggiornati
     * @param id         del sensore da aggiornare
     * @return il sensore aggiornato
     */
    @Operation(summary = "update", description = "Aggiorna un sensore")
    @PutMapping("/{id}")
    public ResponseEntity<Sensore> update(@RequestBody SensoreDto sensoreDto, @PathVariable("id") Long id) {
        return ResponseEntity.ok(sensoreService.update(sensoreDto, id));
    }

    /**
     * @param misurazioneDTO DTO della misurazione da inserire
     */
    @Operation(summary = "nuovaRilevazione", description = "Inserisce una nuova misurazione")
    @PostMapping("/nuovaRilevazione")
    public ResponseEntity<Void> nuovaRilevazione(@RequestBody MisurazioneDTO misurazioneDTO) {
        return sensoreService.nuovaRilevazione(misurazioneDTO);
    }

    /**
     * @param probe         oggetto per filtrare i sensori
     * @param page          numero della pagina
     * @param size          numero di elementi per pagina
     * @param sortField     campo per ordinare i sensori
     * @param sortDirection direzione di ordinamento
     * @return I sensori filtrati
     */
    @Operation(summary = "filter", description = "I sensori filtrati")
    @PostMapping("/filter")
    ResponseEntity<Page<Sensore>> filter(
            @RequestBody(required = false) Sensore probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return sensoreService.filter(probe, page, size, sortField, sortDirection);
    }
}