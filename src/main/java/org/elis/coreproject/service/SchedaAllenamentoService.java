package org.elis.coreproject.service;

import org.elis.coreproject.model.SchedaAllenamento;
import org.elis.coreproject.model.User;
import org.elis.coreproject.model.dto.SchedaAllenamentoDto;
import org.elis.coreproject.repository.SchedaAllenamentoRepository;
import org.elis.coreproject.repository.UserRepository;
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
 * Service per la gestione delle schede di allenamento.
 */
@Slf4j
@Service
@Transactional
public class SchedaAllenamentoService {
    @Autowired
    private SchedaAllenamentoRepository schedaAllenamentoRepository;

    @Autowired
    private UserRepository userRepository;

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
     * @param schedaAllenamentoDto scheda di allenamento da salvare
     * @return scheda di allenamento salvata
     */
    public SchedaAllenamento save(SchedaAllenamentoDto schedaAllenamentoDto) {
        try {
            // Ottengo utente per la relativa scheda di allenamento.
            User user = userRepository.findById(schedaAllenamentoDto.getIdUtente()).orElseThrow(() -> new Exception("user non trovato"));

            // Creo la scheda di allenamento
            SchedaAllenamento schedaAllenamento = new SchedaAllenamento();

            // Copio i dati della scheda di allenamento
            BeanUtils.copyProperties(schedaAllenamentoDto, schedaAllenamento);

            // Imposto l'utente della scheda di allenamento.
            schedaAllenamento.setUtente(user);

            return schedaAllenamentoRepository.save(schedaAllenamento);
        } catch (Exception e) { // Se ci sono errori, lancio una eccezione
            log.error("errore salvataggio scheda allenamento", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id della scheda di allenamento da eliminare
     */
    public void deleteById(Long id) {
        // Ottengo la scheda di allenamento
        SchedaAllenamento schedaAllenamento = schedaAllenamentoRepository.findById(id).orElseThrow(() -> {
            log.error("Unable to delete non-existent data!");
            return new ResourceNotFoundException();
        });

        // Elimino l'utente collegato alla scheda di allenamento
        schedaAllenamento.setUtente(null);

        // Elimino la scheda di allenamento
        schedaAllenamentoRepository.delete(schedaAllenamento);
    }

    /**
     * @return lista di schede di allenamento
     */
    public List<SchedaAllenamento> findAll() {
        return schedaAllenamentoRepository.findAll();
    }

    /**
     * @param id id della scheda di allenamento da ottenere
     * @return scheda di allenamento con id specificato
     */
    public SchedaAllenamento findById(Long id) {
        return schedaAllenamentoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * @param schedaAllenamentoDto scheda di allenamento con i campi aggiornati
     * @param id                   id della scheda di allenamento da aggiornare
     * @return scheda di allenamento aggiornata
     */
    public SchedaAllenamento update(SchedaAllenamentoDto schedaAllenamentoDto, Long id) {
        // Ottengo la scheda di allenamento da aggiornare
        Optional<SchedaAllenamento> schedaAllenamentoOld = schedaAllenamentoRepository.findById(id);
        schedaAllenamentoDto.setId(id);
        try {
            // Se la scheda di allenamento esiste, la aggiorno
            if (schedaAllenamentoOld.isPresent()) {
                copyNonNullProperties(schedaAllenamentoDto, schedaAllenamentoOld.get());

                // Ottengo utente collegato alla scheda di allenamento
                User user = userRepository.findById(schedaAllenamentoDto.getIdUtente()).orElseThrow(() -> new Exception("user non trovato"));
                schedaAllenamentoOld.get().setUtente(user);
                schedaAllenamentoOld.get().setId(id);

                // Salvo la scheda di allenamento
                return schedaAllenamentoRepository.save(schedaAllenamentoOld.get());
            } else {
                throw new ResourceNotFoundException(); // Se la scheda di allenamento non esiste, lancio una eccezione
            }
        } catch (Exception e) {
            log.error("errore aggiornamento scheda allenamento", e); // Se ci sono errori, lancio una eccezione
            throw new RuntimeException(e);

        }
    }

    /**
     * @param probe         scheda di allenamento da ottenere
     * @param page          page della lista di schede di allenamento
     * @param size          size della lista di schede di allenamento
     * @param sortField     campo per ordinare la lista
     * @param sortDirection direzione di ordinamento
     * @return lista di schede di allenamento
     */
    public ResponseEntity<Page<SchedaAllenamento>> filter(SchedaAllenamento probe, Integer page, Integer size, String sortField, String sortDirection) {
        Pageable pageable;

        // Controllo se la scheda di allenamento per filtrare è nulla
        if (probe == null) {
            probe = new SchedaAllenamento();
        }

        // Controllo se il campo per ordinare la lista è nullo
        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size); // Se è nullo, ordino per id
        } else {
            // Se il campo per ordinare la lista non è nullo, ordino per il campo specificato
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }

        // Filtro le schede di allenamento
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<SchedaAllenamento> example = Example.of(probe, matcher);

        return ResponseEntity.ok(schedaAllenamentoRepository.findAll(example, pageable));
    }
}