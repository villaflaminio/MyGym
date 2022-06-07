package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.Abbonamento;
import com.salvatorechiacchio.mygym.model.dto.EsercizioDto;
import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.model.dto.filter.AbbonamentoDtoFilter;
import com.salvatorechiacchio.mygym.model.dto.filter.EsercizioDtoFilter;
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

@Slf4j
@Service
@Transactional
public class EsercizioService {
    @Autowired
    private EsercizioRepository esercizioRepository;

    public Esercizio save(EsercizioDto esercizioDto) {
        try {
            Esercizio esercizio = new Esercizio();
            BeanUtils.copyProperties(esercizioDto, esercizio);
            esercizio.setId(null);
            return esercizioRepository.save(esercizio);
        }catch (Exception e){
            log.error("errore salvataggio esercizio", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        esercizioRepository.deleteById(id);
    }

    public List<Esercizio> findAll() {
        return esercizioRepository.findAll();
    }

    public Esercizio findById(Long id) {
        return esercizioRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Esercizio update(EsercizioDto esercizioDto, Long id) {
        Optional<Esercizio> esercizioOld = esercizioRepository.findById(id);
        esercizioDto.setId(id);
        if (esercizioOld.isPresent()) {
            copyNonNullProperties(esercizioDto, esercizioOld.get());
            esercizioOld.get().setId(id);
            return esercizioRepository.save(esercizioOld.get());
        }
        else {
            throw new ResourceNotFoundException();
        }
    }

    public ResponseEntity<Page<Esercizio>> filter(EsercizioDtoFilter probe, Integer page, Integer size, String sortField, String sortDirection){
        Pageable pageable;
        Page<Esercizio> result;
        Esercizio esercizio = new Esercizio();
        if (probe != null) {
            copyNonNullProperties(probe, esercizio);
        }

        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<Esercizio> example = Example.of(esercizio, matcher);

        result = esercizioRepository.findAll(example, pageable);

        return ResponseEntity.ok(result);
    }

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