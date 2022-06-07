package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.model.dto.PalestraDto;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.model.dto.filter.EsercizioDtoFilter;
import com.salvatorechiacchio.mygym.model.dto.filter.PalestraDtoFilter;
import com.salvatorechiacchio.mygym.service.PalestraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/palestra")
@RestController
@Slf4j
@Tag(name = "Palestra")
public class PalestraController {
    @Autowired
    private  PalestraService palestraService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(palestraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Palestra> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(palestraService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Palestra> save(@RequestBody @Validated PalestraDto palestraDto) {
        return ResponseEntity.ok(palestraService.save(palestraDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(palestraService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        palestraService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody PalestraDto palestraDto, @PathVariable("id") Long id) {
        palestraService.update(palestraDto, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filter")
    ResponseEntity<Page<Palestra>> filter(
            @RequestBody(required = false) PalestraDtoFilter probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return palestraService.filter(probe, page, size, sortField, sortDirection);
    }
}