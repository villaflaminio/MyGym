package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Sensore;
import com.salvatorechiacchio.mygym.model.dto.SensoreDto;
import com.salvatorechiacchio.mygym.service.SensoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/sensore")
@RestController
@Slf4j
public class SensoreController {
    @Autowired
    private SensoreService sensoreService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated SensoreDto sensoreDto) {
        sensoreService.save(sensoreDto);
        return ResponseEntity.ok().build();
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
}