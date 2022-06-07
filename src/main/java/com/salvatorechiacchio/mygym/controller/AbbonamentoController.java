package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Abbonamento;
import com.salvatorechiacchio.mygym.model.dto.AbbonamentoDto;
import com.salvatorechiacchio.mygym.model.dto.filter.AbbonamentoDtoFilter;
import com.salvatorechiacchio.mygym.service.AbbonamentoService;
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

@RequestMapping("/api/abbonamento")
@RestController
@Tag(name = "Abbonamento")
public class AbbonamentoController {

    @Autowired
    AbbonamentoService abbonamentoService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(abbonamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Abbonamento> findById(@PathVariable("id") Long id) {
        Abbonamento abbonamento = abbonamentoService.findById(id);
        return ResponseEntity.ok(abbonamento);
    }

    @PostMapping
    public ResponseEntity<Abbonamento> save(@RequestBody @Validated AbbonamentoDto abbonamentoDto) {
        return ResponseEntity.ok(abbonamentoService.save(abbonamentoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(abbonamentoService.findById(id)).orElseThrow(() -> {
            return new ResourceNotFoundException();
        });
        abbonamentoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Abbonamento abbonamento, @PathVariable("id") Long id) {
        abbonamentoService.update(abbonamento, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filter")
    ResponseEntity<Page<Abbonamento>> filter(
            @RequestBody(required = false) AbbonamentoDtoFilter probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return abbonamentoService.filter(probe, page, size, sortField, sortDirection);
    }
}