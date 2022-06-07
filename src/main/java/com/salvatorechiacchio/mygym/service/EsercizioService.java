package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.dto.EsercizioDto;
import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.repository.EsercizioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
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
            return esercizioRepository.save(esercizio);
        }catch (Exception e){
            log.error("errore salvataggio esercizio", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        esercizioRepository.deleteById(id);
    }

    public Esercizio findById(Long id) {
        return esercizioRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Esercizio update(Esercizio esercizio, Long id) {
        Optional<Esercizio> esercizioOld = esercizioRepository.findById(id);
        esercizio.setId(id);
        if (esercizioOld.isPresent()) {
            copyNonNullProperties(esercizio, esercizioOld.get());
            return esercizioRepository.save(esercizioOld.get());
        }
        else {
            throw new ResourceNotFoundException();
        }
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