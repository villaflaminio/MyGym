package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.Misurazione;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.model.Sensore;
import com.salvatorechiacchio.mygym.model.dto.MisurazioneDTO;
import com.salvatorechiacchio.mygym.model.dto.SensoreDto;
import com.salvatorechiacchio.mygym.repository.MisurazioneRepository;
import com.salvatorechiacchio.mygym.repository.PalestraRepository;
import com.salvatorechiacchio.mygym.repository.SensoreRepository;
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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service per la gestione dei sensori
 */
@Slf4j
@Service
@Transactional
public class SensoreService {
    @Autowired
    private SensoreRepository sensoreRepository;

    @Autowired
    private MisurazioneRepository misurazioneRepository;
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
     * @param sensoreDto sensore da inserire
     * @return sensore inserito
     */
    public Sensore save(SensoreDto sensoreDto) {
        try {
            // Ottengo palestra da collegare al sensore
            Palestra palestra = palestraRepository.findById(sensoreDto.getIdPalestra()).orElseThrow(() -> new Exception("palestra non trovata"));

            // Creo il sensore
            Sensore sensore = new Sensore();
            BeanUtils.copyProperties(sensoreDto, sensore);

            // Collego palestra al sensore
            sensore.setPalestra(palestra);
            palestra.setSensore(sensore);
            sensore.setId(null);

            // Salvo il sensore
            return sensoreRepository.save(sensore);
        } catch (Exception e) {
            log.error("errore salvataggio sensore", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id id del sensore da eliminare
     */
    public void deleteById(Long id) {
        try {
            // Ottengo il sensore da eliminare
            Sensore sensore = sensoreRepository.findById(id).orElseThrow(() -> new Exception("sensore non trovato"));

            // Elimino collegamento palestra sensore
            sensore.getPalestra().setSensore(null);
            sensore.setPalestra(null);

            // Elimino il sensore
            sensoreRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return lista di tutti i sensori
     */
    public List<Sensore> findAll() {
        return sensoreRepository.findAll();
    }

    /**
     * @param id id del sensore da cercare
     * @return sensore cercato
     */
    public Sensore findById(Long id) {
        return sensoreRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * @param sensoreDto sensore aggiornato
     * @param id         id del sensore da aggiornare
     * @return sensore aggiornato
     */
    public Sensore update(SensoreDto sensoreDto, Long id) {
        try {
            // Ottengo il palestra collegata al sensore da aggiornare.
            Palestra palestra = palestraRepository.findById(sensoreDto.getIdPalestra()).orElseThrow(() -> new Exception("palestra non trovata"));

            // Ottengo il sensore da aggiornare
            Optional<Sensore> sensoreOld = sensoreRepository.findById(id);
            sensoreDto.setId(id);

            // Controllo se il sensore esiste
            if (sensoreOld.isPresent()) {
                copyNonNullProperties(sensoreDto, sensoreOld.get());
                sensoreOld.get().setPalestra(palestra);
                palestra.setSensore(sensoreOld.get());
                return sensoreRepository.save(sensoreOld.get());
            } else {
                throw new ResourceNotFoundException(); // Lancio eccezione se il sensore non esiste
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param probe         sensore con i campi per filtrare
     * @param page          page da visualizzare
     * @param size          size della pagina
     * @param sortField     campo per ordinare
     * @param sortDirection direzione di ordinamento
     * @return lista di sensori filtrati
     */
    public ResponseEntity<Page<Sensore>> filter(Sensore probe, Integer page, Integer size, String sortField, String sortDirection) {
        Pageable pageable;

        // Controllo se il sensore per filtrare è nullo
        if (probe == null) {
            probe = new Sensore(); // Se è nullo lo creo
        }

        // Controllo se il campo per ordinare è nullo
        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size); // Se è nullo ordino per id
        } else {
            // Se non è nullo ordino per il campo specificato
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }

        // Filtro i sensori
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<Sensore> example = Example.of(probe, matcher);

        return ResponseEntity.ok(sensoreRepository.findAll(example, pageable));
    }

    public ResponseEntity<Void> nuovaRilevazione(MisurazioneDTO misurazioneDTO) {
        try {
            // Ottengo il sensore in cui inserire la misurazione.
            Sensore sensore = sensoreRepository.findById(misurazioneDTO.getIdSensore()).orElseThrow(() -> new Exception("sensore non trovato"));

            Misurazione misurazione = new Misurazione();
            BeanUtils.copyProperties(misurazioneDTO, misurazione);
            misurazione.setTimestamp(LocalDateTime.now());
            misurazione.setSensore(sensore);

            // Aggiungo la misurazione al sensore
            sensore.getMisurazioni().add(misurazione);

            sensoreRepository.save(sensore);
            misurazioneRepository.save(misurazione);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}