package org.elis.coreproject.service;

import org.elis.coreproject.model.Abbonamento;
import org.elis.coreproject.model.Palestra;
import org.elis.coreproject.model.User;
import org.elis.coreproject.model.dto.AbbonamentoDto;
import org.elis.coreproject.repository.AbbonamentoRepository;
import org.elis.coreproject.repository.PalestraRepository;
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
 * Service per gestione abbonamento
 */
@Slf4j
@Service
@Transactional
public class AbbonamentoService {
    @Autowired
    AbbonamentoRepository abbonamentoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PalestraRepository palestraRepository;

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
     * @param abbonamentoDto abbonamento da salvare
     * @return abbonamento salvato
     */
    public Abbonamento save(AbbonamentoDto abbonamentoDto) {
        try {
            // Controllo se l'utente esiste
            User user = userRepository.findById(abbonamentoDto.getIdUtente()).orElseThrow(() -> new Exception("user non trovato"));

            // Controllo se la palestra esiste
            Palestra palestra = palestraRepository.findById(abbonamentoDto.getIdPalestra()).orElseThrow(() -> new Exception("palestra non trovata"));

            // Creo l'abbonamento
            Abbonamento abbonamento = new Abbonamento();

            // Copio i dati
            BeanUtils.copyProperties(abbonamentoDto, abbonamento);

            // Setto l'utente
            abbonamento.setUtente(user);

            // Setto la palestra
            abbonamento.setPalestra(palestra);
            abbonamento.setId(null);

            // Salvo l'abbonamento
            return abbonamentoRepository.save(abbonamento);
        } catch (Exception e) {
            log.error("errore salvataggio abbonamento", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id identificativo dell'abbonamento da eliminare
     */
    public void deleteById(Long id) {
        abbonamentoRepository.deleteById(id);
    }

    /**
     * @param id identificativo dell'abbonamento da cercare
     * @return abbonamento cercato
     */
    public Abbonamento findById(Long id) {
        // Controllo se l'abbonamento esiste e lo restituisco, altrimenti lancio eccezione di not found
        return abbonamentoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * @return lista di tutti gli abbonamenti
     */
    public List<Abbonamento> findAll() {
        return abbonamentoRepository.findAll();
    }

    /**
     * @param abbonamentoDto abbonamento modificato
     * @param id             identificativo dell'abbonamento da modificare
     * @return abbonamento modificato
     */
    public Abbonamento update(AbbonamentoDto abbonamentoDto, Long id) {
        // Ottengo abbonamento da modificare
        Optional<Abbonamento> abbonamentoOld = abbonamentoRepository.findById(id);
        abbonamentoDto.setId(id);

        // Controllo se l'abbonamento esiste
        if (abbonamentoOld.isPresent()) {
            // Copio i dati
            copyNonNullProperties(abbonamentoDto, abbonamentoOld.get());
            abbonamentoDto.setId(id);

            // Salvo l'abbonamento
            return abbonamentoRepository.save(abbonamentoOld.get());
        } else {
            // Se non esiste lancio eccezione di not found
            throw new ResourceNotFoundException();
        }
    }

    /**
     * @param probe         abbonamento utilizzato per filtrare
     * @param page          pagina da visualizzare
     * @param size          numero di elementi per pagina
     * @param sortField     campo per ordinamento
     * @param sortDirection direzione di ordinamento
     * @return lista di abbonamenti filtrati
     */
    public ResponseEntity<Page<Abbonamento>> filter(Abbonamento probe, Integer page, Integer size, String sortField, String sortDirection) {
        Pageable pageable;

        // Controllo se l'abbonamento da filtrare è nullo.
        if (probe == null) {
            probe = new Abbonamento(); // Se è nullo creo un nuovo abbonamento
        }

        // Controllo se il campo di ordinamento è nullo.
        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size); // Se è nullo ordino per id
        } else {
            // Se non è nullo ordino per il campo di ordinamento
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }

        // Filtro gli abbonamenti
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<Abbonamento> example = Example.of(probe, matcher);

        return ResponseEntity.ok(abbonamentoRepository.findAll(example, pageable));
    }
}