package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.dto.SensoreDto;
import com.salvatorechiacchio.mygym.mapper.SensoreMapper;
import com.salvatorechiacchio.mygym.model.Sensore;
import com.salvatorechiacchio.mygym.service.SensoreService;
import com.sun.tools.javac.util.DefinedBy.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/sensore")
@RestController
@Slf4j
@Api("sensore")
public class SensoreController {
    private final SensoreService sensoreService;

    public SensoreController(SensoreService sensoreService) {
        this.sensoreService = sensoreService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated SensoreDto sensoreDto) {
        sensoreService.save(sensoreDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensoreDto> findById(@PathVariable("id") Long id) {
        SensoreDto sensore = sensoreService.findById(id);
        return ResponseEntity.ok(sensore);
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

    @GetMapping("/page-query")
    public ResponseEntity<Page<SensoreDto>> pageQuery(SensoreDto sensoreDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SensoreDto> sensorePage = sensoreService.findByCondition(sensoreDto, pageable);
        return ResponseEntity.ok(sensorePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated SensoreDto sensoreDto, @PathVariable("id") Long id) {
        sensoreService.update(sensoreDto, id);
        return ResponseEntity.ok().build();
    }
}