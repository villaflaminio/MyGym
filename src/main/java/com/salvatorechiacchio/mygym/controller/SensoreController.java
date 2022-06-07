package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.SchedaAllenamento;
import com.salvatorechiacchio.mygym.model.Sensore;
import com.salvatorechiacchio.mygym.model.dto.SensoreDto;
import com.salvatorechiacchio.mygym.model.dto.filter.SchedaAllenamentoDtoFilter;
import com.salvatorechiacchio.mygym.model.dto.filter.SensoreDtoFilter;
import com.salvatorechiacchio.mygym.service.SensoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/sensore")
@RestController
@Slf4j
@Tag(name = "Sensore")
public class SensoreController {
    @Autowired
    private SensoreService sensoreService;

    @PostMapping
    public ResponseEntity<Sensore> save(@RequestBody @Validated SensoreDto sensoreDto) {
        return ResponseEntity.ok(sensoreService.save(sensoreDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensore> findById(@PathVariable("id") Long id) {
        Sensore sensore = sensoreService.findById(id);
        return ResponseEntity.ok(sensore);
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(sensoreService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(sensoreService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        sensoreService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody SensoreDto sensoreDto, @PathVariable("id") Long id) {
        sensoreService.update(sensoreDto, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filter")
    ResponseEntity<Page<Sensore>> filter(
            @RequestBody(required = false) SensoreDtoFilter probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return sensoreService.filter(probe, page, size, sortField, sortDirection);
    }
}