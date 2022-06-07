package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.model.SchedaAllenamento;
import com.salvatorechiacchio.mygym.model.dto.SchedaAllenamentoDto;
import com.salvatorechiacchio.mygym.model.dto.filter.EsercizioDtoFilter;
import com.salvatorechiacchio.mygym.model.dto.filter.SchedaAllenamentoDtoFilter;
import com.salvatorechiacchio.mygym.service.SchedaAllenamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/scheda-allenamento")
@RestController
@Slf4j
@Tag(name = "SchedaAllenamento")
public class SchedaAllenamentoController {
    @Autowired
    private SchedaAllenamentoService schedaAllenamentoService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(schedaAllenamentoService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated SchedaAllenamentoDto schedaAllenamentoDto) {
        schedaAllenamentoService.save(schedaAllenamentoDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedaAllenamento> findById(@PathVariable("id") Long id) {
        SchedaAllenamento schedaAllenamento = schedaAllenamentoService.findById(id);
        return ResponseEntity.ok(schedaAllenamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(schedaAllenamentoService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        schedaAllenamentoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody SchedaAllenamento schedaAllenamento, @PathVariable("id") Long id) {
        schedaAllenamentoService.update(schedaAllenamento, id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/filter")
    ResponseEntity<Page<SchedaAllenamento>> filter(
            @RequestBody(required = false) SchedaAllenamentoDtoFilter probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return schedaAllenamentoService.filter(probe, page, size, sortField, sortDirection);
    }
}