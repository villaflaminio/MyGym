package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.model.dto.EsercizioDto;
import com.salvatorechiacchio.mygym.repository.EsercizioRepository;
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
 * Service per la gestione degli esercizi.
 */
@Slf4j
@Service
@Transactional
public class EsercizioService {
    @Autowired
    private EsercizioRepository esercizioRepository;

    /**
     * @param esercizioDto esercizio da salvare
     * @return esercizio salvato
     */
    public Esercizio save(EsercizioDto esercizioDto) {
        // Creo l'esercizio
        try {
            Esercizio esercizio = new Esercizio();
            BeanUtils.copyProperties(esercizioDto, esercizio);
            esercizio.setId(null);
            return esercizioRepository.save(esercizio);
        } catch (Exception e) { // Se ci sono errori, lancio una eccezione
            log.error("errore salvataggio esercizio", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id dell'esercizio da eliminare
     */
    public void deleteById(Long id) {
        esercizioRepository.deleteById(id);
    }

    /**
     * @return lista di tutti gli esercizi
     */
    public List<Esercizio> findAll() {
        return esercizioRepository.findAll();
    }

    /**
     * @param id id dell'esercizio da cercare
     * @return esercizio cercato
     */
    public Esercizio findById(Long id) {
        // Cerco l'esercizio con l'id specificato e se non esiste, lancio una eccezione
        return esercizioRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * @param esercizioDto esercizio modificato
     * @param id           id dell'esercizio da modificare
     * @return esercizio modificato
     */
    public Esercizio update(EsercizioDto esercizioDto, Long id) {
        // Cerco l'esercizio con l'id specificato e se non esiste, lancio una eccezione
        Optional<Esercizio> esercizioOld = esercizioRepository.findById(id);
        esercizioDto.setId(id);
        if (esercizioOld.isPresent()) {
            // Se esiste, lo modifico
            copyNonNullProperties(esercizioDto, esercizioOld.get());
            esercizioOld.get().setId(id);

            // Salvo l'esercizio
            return esercizioRepository.save(esercizioOld.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * +
     *
     * @param probe         esercizio utilizzato per filtrare
     * @param page          page della paginazione
     * @param size          size della paginazione
     * @param sortField     campo di ordinamento
     * @param sortDirection direzione di ordinamento
     * @return lista di esercizi filtrati
     */
    public ResponseEntity<Page<Esercizio>> filter(Esercizio probe, Integer page, Integer size, String sortField, String sortDirection) {
        Pageable pageable;

        // Controllo se l'esercizio da filtrare è nullo
        if (probe == null) {
            probe = new Esercizio(); // Se è nullo, creo un nuovo esercizio
        }

        // Controllo se il campo di ordinamento è nullo
        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size); // Se è nullo, ordino per id
        } else {
            // Se non è nullo, ordino per il campo specificato
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }

        // Filtro gli esercizi
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<Esercizio> example = Example.of(probe, matcher);

        return ResponseEntity.ok(esercizioRepository.findAll(example, pageable));
    }

    /**
     * @param idList lista di id degli esercizi da ottenere
     * @return lista di esercizi con gli id specificati
     */
    public List<Esercizio> getAllByIdList(List<Long> idList) {
        return esercizioRepository.findAllById(idList);
    }

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
}