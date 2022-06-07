package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.dto.PalestraDto;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.service.PalestraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/palestra")
@RestController
@Slf4j
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
    public ResponseEntity<Void> save(@RequestBody @Validated PalestraDto palestraDto) {
        palestraService.save(palestraDto);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> update(@RequestBody @Validated PalestraDto palestraDto, @PathVariable("id") Long id) {
        palestraService.update(palestraDto, id);
        return ResponseEntity.ok().build();
    }
}