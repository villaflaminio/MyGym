package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.Abbonamento;
import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.model.dto.SensoreDto;
import com.salvatorechiacchio.mygym.model.Sensore;
import com.salvatorechiacchio.mygym.model.dto.filter.AbbonamentoDtoFilter;
import com.salvatorechiacchio.mygym.model.dto.filter.SensoreDtoFilter;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class SensoreService {
    @Autowired
    private SensoreRepository sensoreRepository;

    @Autowired
    private PalestraRepository palestraRepository;

    public Sensore save(SensoreDto sensoreDto) {
        try {
            Palestra palestra = palestraRepository.findById(sensoreDto.getIdPalestra()).orElseThrow(() -> new Exception("palestra non trovata"));
            Sensore sensore = new Sensore();
            BeanUtils.copyProperties(sensoreDto, sensore);
            sensore.setPalestra(palestra);
            palestra.setSensore(sensore);
            sensore.setId(null);
            return sensoreRepository.save(sensore);
        }catch (Exception e){
            log.error("errore salvataggio sensore", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        try {
            Sensore sensore = sensoreRepository.findById(id).orElseThrow(() -> new Exception("sensore non trovato"));
            sensore.getPalestra().setSensore(null);
            sensore.setPalestra(null);

            sensoreRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Sensore> findAll() {
        return sensoreRepository.findAll();
    }

    public Sensore findById(Long id) {
        return sensoreRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Sensore update(SensoreDto sensoreDto, Long id) {
        try {
            Palestra palestra = palestraRepository.findById(sensoreDto.getIdPalestra()).orElseThrow(() -> new Exception("palestra non trovata"));

            Optional<Sensore> sensoreOld = sensoreRepository.findById(id);
            sensoreDto.setId(id);
            if (sensoreOld.isPresent()) {
                copyNonNullProperties(sensoreDto, sensoreOld.get());
                sensoreOld.get().setPalestra(palestra);
                palestra.setSensore(sensoreOld.get());
                return sensoreRepository.save(sensoreOld.get());
            }
            else {
                throw new ResourceNotFoundException();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Page<Sensore>> filter(Sensore probe, Integer page, Integer size, String sortField, String sortDirection){
        Pageable pageable;
        Page<Sensore> result;
        if (probe == null) {
            probe = new Sensore();
        }

        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<Sensore> example = Example.of(probe, matcher);

        result = sensoreRepository.findAll(example, pageable);

        return ResponseEntity.ok(result);
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