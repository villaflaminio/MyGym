package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.dto.EsercizioDto;
import com.salvatorechiacchio.mygym.service.EsercizioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/esercizio")
@RestController
@Slf4j
public class EsercizioController {
    private final EsercizioService esercizioService;

    public EsercizioController(EsercizioService esercizioService) {
        this.esercizioService = esercizioService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated EsercizioDto esercizioDto) {
        esercizioService.save(esercizioDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EsercizioDto> findById(@PathVariable("id") Long id) {
        EsercizioDto esercizio = esercizioService.findById(id);
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
    public ResponseEntity<Void> update(@RequestBody @Validated EsercizioDto esercizioDto, @PathVariable("id") Long id) {
        esercizioService.update(esercizioDto, id);
        return ResponseEntity.ok().build();
    }
}