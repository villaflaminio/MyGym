package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.dto.SchedaAllenamentoDto;
import com.salvatorechiacchio.mygym.mapper.SchedaAllenamentoMapper;
import com.salvatorechiacchio.mygym.model.SchedaAllenamento;
import com.salvatorechiacchio.mygym.service.SchedaAllenamentoService;
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

@RequestMapping("/scheda-allenamento")
@RestController
@Slf4j
@Api("scheda-allenamento")
public class SchedaAllenamentoController {
    private final SchedaAllenamentoService schedaAllenamentoService;

    public SchedaAllenamentoController(SchedaAllenamentoService schedaAllenamentoService) {
        this.schedaAllenamentoService = schedaAllenamentoService;
    }

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

    @GetMapping("/page-query")
    public ResponseEntity<Page<SchedaAllenamentoDto>> pageQuery(SchedaAllenamentoDto schedaAllenamentoDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SchedaAllenamentoDto> schedaAllenamentoPage = schedaAllenamentoService.findByCondition(schedaAllenamentoDto, pageable);
        return ResponseEntity.ok(schedaAllenamentoPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated SchedaAllenamentoDto schedaAllenamentoDto, @PathVariable("id") Long id) {
        schedaAllenamentoService.update(schedaAllenamentoDto, id);
        return ResponseEntity.ok().build();
    }
}