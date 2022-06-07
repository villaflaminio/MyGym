package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.SchedaAllenamento;
import com.salvatorechiacchio.mygym.model.dto.SchedaAllenamentoDto;
import com.salvatorechiacchio.mygym.service.SchedaAllenamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/schedaAllenamento")
@RestController
@Slf4j
public class SchedaAllenamentoController {
    @Autowired
    private SchedaAllenamentoService schedaAllenamentoService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(schedaAllenamentoService.findAll());
    }

    @PostMapping
    public ResponseEntity<SchedaAllenamento> save(@RequestBody @Validated SchedaAllenamentoDto schedaAllenamentoDto) {
        return ResponseEntity.ok(schedaAllenamentoService.save(schedaAllenamentoDto));
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
    public ResponseEntity<SchedaAllenamento> update(@RequestBody SchedaAllenamentoDto schedaAllenamentoDto, @PathVariable("id") Long id) {
        schedaAllenamentoService.update(schedaAllenamentoDto, id);
        return ResponseEntity.ok().build();
    }
}