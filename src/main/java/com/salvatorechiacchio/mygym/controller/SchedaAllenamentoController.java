package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.dto.SchedaAllenamentoDto;
import com.salvatorechiacchio.mygym.service.SchedaAllenamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/scheda-allenamento")
@RestController
@Slf4j
public class SchedaAllenamentoController {
    @Autowired
    private SchedaAllenamentoService schedaAllenamentoService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated SchedaAllenamentoDto schedaAllenamentoDto) {
        schedaAllenamentoService.save(schedaAllenamentoDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedaAllenamentoDto> findById(@PathVariable("id") Long id) {
        SchedaAllenamentoDto schedaAllenamento = schedaAllenamentoService.findById(id);
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
    public ResponseEntity<Void> update(@RequestBody @Validated SchedaAllenamentoDto schedaAllenamentoDto, @PathVariable("id") Long id) {
        schedaAllenamentoService.update(schedaAllenamentoDto, id);
        return ResponseEntity.ok().build();
    }
}