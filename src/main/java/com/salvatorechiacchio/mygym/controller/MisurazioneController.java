package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.Misurazione;
import com.salvatorechiacchio.mygym.service.MisurazioneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/misurazione")
@RestController
@Slf4j
@Tag(name = "Misurazione")
public class MisurazioneController {

    @Autowired
    private MisurazioneService misurazioneService;

    /**
     * @param probe         misurazione con i campi da filtrare
     * @param page          numero di pagina
     * @param size          numero di elementi per pagina
     * @param sortField     campo per ordinamento
     * @param sortDirection direzione di ordinamento
     * @return la lista di misurazioni filtrate
     */
    @Operation(summary = "filter", description = "la lista di misurazioni filtrate")
    @PostMapping("/filter")
    ResponseEntity<Page<Misurazione>> filter(
            @RequestBody(required = false) Misurazione probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return misurazioneService.filter(probe, page, size, sortField, sortDirection);
    }
}
