package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.Misurazione;
import com.salvatorechiacchio.mygym.repository.MisurazioneRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MisurazioneService {
    @Autowired
    private MisurazioneRepository misurazioneRepository;

    /**
     * @param probe         misurazione con i campi per filtrare
     * @param page          page da visualizzare
     * @param size          size della pagina
     * @param sortField     campo per ordinare
     * @param sortDirection direzione di ordinamento
     * @return lista di sensori filtrati
     */
    public ResponseEntity<Page<Misurazione>> filter(Misurazione probe, Integer page, Integer size, String sortField, String sortDirection) {
        Pageable pageable;

        // Controllo se la misurazione per filtrare è nulla
        if (probe == null) {
            probe = new Misurazione(); // Se è nulla lo creo
        }

        // Controllo se il campo per ordinare è nullo
        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size); // Se è nullo ordino per id
        } else {
            // Se non è nullo ordino per il campo specificato
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }

        // Filtro le misurazioni
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<Misurazione> example = Example.of(probe, matcher);

        return ResponseEntity.ok(misurazioneRepository.findAll(example, pageable));
    }
}
