package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.model.dto.SensoreDto;
import com.salvatorechiacchio.mygym.model.Sensore;
import com.salvatorechiacchio.mygym.repository.PalestraRepository;
import com.salvatorechiacchio.mygym.repository.SensoreRepository;
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
public class SensoreService {
    @Autowired
    private SensoreRepository sensoreRepository;

    @Autowired
    private PalestraRepository palestraRepository;

    public Sensore save(SensoreDto sensoreDto) {
        try {
            Palestra palestra = palestraRepository.findById(sensoreDto.getIdPalestra()).orElseThrow(() -> new Exception("sensore non trovato"));
            Sensore sensore = new Sensore();
            BeanUtils.copyProperties(sensoreDto, sensore);
            sensore.setPalestra(palestra);
            return sensoreRepository.save(sensore);
        }catch (Exception e){
            log.error("errore salvataggio sensore", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        sensoreRepository.deleteById(id);
    }

    public Sensore findById(Long id) {
        return sensoreRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }


    public Sensore update(Sensore sensore, Long id) {
        Optional<Sensore> sensoreOld = sensoreRepository.findById(id);
        sensore.setId(id);
        if (sensoreOld.isPresent()) {
            copyNonNullProperties(sensore, sensoreOld.get());
            return sensoreRepository.save(sensoreOld.get());
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