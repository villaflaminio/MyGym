package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.model.dto.EsercizioDto;
import com.salvatorechiacchio.mygym.service.EsercizioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/esercizio")
@RestController
@Slf4j
public class EsercizioController {
    @Autowired
    private EsercizioService esercizioService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(esercizioService.findAll());
    }

    @PostMapping
    public ResponseEntity<Esercizio> save(@RequestBody @Validated EsercizioDto esercizioDto) {
        return ResponseEntity.ok(esercizioService.save(esercizioDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Esercizio> findById(@PathVariable("id") Long id) {
        Esercizio esercizio = esercizioService.findById(id);
        return ResponseEntity.ok(esercizio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(esercizioService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        esercizioService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody EsercizioDto esercizioDto, @PathVariable("id") Long id) {
        esercizioService.update(esercizioDto, id);
        return ResponseEntity.ok().build();
    }
}