package org.elis.coreproject.service;


import org.elis.coreproject.model.Palestra;
import org.elis.coreproject.model.dto.PalestraDto;
import org.elis.coreproject.repository.PalestraRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service per la gestione della palestra.
 */
@Slf4j
@Service
@Transactional
public class PalestraService {
    @Autowired
    private PalestraRepository palestraRepository;

    // ===========================================================================
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * @param palestraDto palestra da salvare
     * @return palestra salvata
     */
    public Palestra save(PalestraDto palestraDto) {
        // Creo una palestra
        try {
            Palestra palestra = new Palestra();
            BeanUtils.copyProperties(palestraDto, palestra);
            palestra.setAbilitato(true);
            return palestraRepository.save(palestra);
        } catch (Exception e) { // Se ci sono errori, lancio una eccezione
            log.error("errore salvataggio palestra", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id della palestra da eliminare
     */
    public void deleteById(Long id) {
        // Elimino la palestra con l'id specificato logicamente.
        Palestra palestra = palestraRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        palestra.setAbilitato(false);
        palestraRepository.save(palestra);
    }

    /**
     * @param id id della palestra da trovare
     * @return palestra trovata
     */
    public Palestra findById(Long id) {
        return palestraRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * @return lista di tutte le palestre
     */
    public List<Palestra> findAll() {
        return palestraRepository.findAll();
    }

    /**
     * @param palestraDto palestra aggiornata
     * @param id          id della palestra da aggiornare
     * @return palestra aggiornata
     */
    public Palestra update(PalestraDto palestraDto, Long id) {
        // Trovo la palestra con l'id specificato
        Optional<Palestra> palestraOld = palestraRepository.findById(id);
        if (palestraOld.isPresent()) {
            // Creo una palestra e copio i dati della palestra trovata
            copyNonNullProperties(palestraDto, palestraOld.get());
            palestraOld.get().setId(id);

            // Salvo la palestra
            return palestraRepository.save(palestraOld.get());
        } else { // Se non esiste, lancio una eccezione di not found
            throw new ResourceNotFoundException();
        }
    }

    /**
     * @param probe         oggetto palestra per filtrare le palestre
     * @param page          pagina di risultati
     * @param size          numero di risultati per pagina
     * @param sortField     campo per ordinare i risultati
     * @param sortDirection direzione di ordinamento
     * @return lista di palestre filtrate
     */
    public ResponseEntity<Page<Palestra>> filter(Palestra probe, Integer page, Integer size, String sortField, String sortDirection) {
        Pageable pageable;

        // Controllo se la palestra per filtrare è nulla
        if (probe == null) {
            probe = new Palestra();
        }

        // Controllo se il campo per ordinare è nullo
        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size); // Se è nullo, ordino per id
        } else {
            // Se non è nullo, ordino per il campo specificato
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }

        // Filtro le palestre
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<Palestra> example = Example.of(probe, matcher);

        return ResponseEntity.ok(palestraRepository.findAll(example, pageable));
    }
}