package com.salvatorechiacchio.mygym.service;


import com.salvatorechiacchio.mygym.model.dto.PalestraDto;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.repository.PalestraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class PalestraService {
    @Autowired
    private PalestraRepository palestraRepository;

    public Palestra save(PalestraDto palestraDto) {
        try {
            Palestra palestra = new Palestra();
            BeanUtils.copyProperties(palestraDto, palestra);
            palestra.setAbilitato(true);
            return palestraRepository.save(palestra);
        }catch (Exception e){
            log.error("errore salvataggio palestra", e);
            throw new RuntimeException(e);
        }
    }
    public void deleteById(Long id) {
        Palestra palestra = palestraRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        palestra.setAbilitato(false);
        palestraRepository.save(palestra);
    }
    public Palestra findById(Long id) {
        return palestraRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
    public List<Palestra> findAll() {
        return palestraRepository.findAll();
    }
    public Palestra update(PalestraDto palestraDto, Long id) {
        Optional<Palestra> palestraOld = palestraRepository.findById(id);
        if (palestraOld.isPresent()) {
            copyNonNullProperties(palestraDto, palestraOld.get());
            palestraOld.get().setId(id);
            return palestraRepository.save(palestraOld.get());
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