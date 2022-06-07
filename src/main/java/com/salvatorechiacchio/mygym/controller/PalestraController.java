package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.dto.PalestraDto;
import com.salvatorechiacchio.mygym.mapper.PalestraMapper;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.service.PalestraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/palestra")
@RestController
@Slf4j
public class PalestraController {
    private final PalestraService palestraService;

    public PalestraController(PalestraService palestraService) {
        this.palestraService = palestraService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated PalestraDto palestraDto) {
        palestraService.save(palestraDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PalestraDto> findById(@PathVariable("id") Long id) {
        PalestraDto palestra = palestraService.findById(id);
        return ResponseEntity.ok(palestra);
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

    @GetMapping("/page-query")
    public ResponseEntity<Page<PalestraDto>> pageQuery(PalestraDto palestraDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PalestraDto> palestraPage = palestraService.findByCondition(palestraDto, pageable);
        return ResponseEntity.ok(palestraPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated PalestraDto palestraDto, @PathVariable("id") Long id) {
        palestraService.update(palestraDto, id);
        return ResponseEntity.ok().build();
    }
}