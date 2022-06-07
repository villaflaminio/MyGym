package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.model.User;
import com.salvatorechiacchio.mygym.model.dto.SchedaAllenamentoDto;
import com.salvatorechiacchio.mygym.model.SchedaAllenamento;
import com.salvatorechiacchio.mygym.model.dto.filter.PalestraDtoFilter;
import com.salvatorechiacchio.mygym.model.dto.filter.SchedaAllenamentoDtoFilter;
import com.salvatorechiacchio.mygym.repository.SchedaAllenamentoRepository;
import com.salvatorechiacchio.mygym.security.repository.UserRepository;
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
public class SchedaAllenamentoService {
    @Autowired
    private SchedaAllenamentoRepository schedaAllenamentoRepository;

    @Autowired
    private UserRepository userRepository;

    public SchedaAllenamento save(SchedaAllenamentoDto schedaAllenamentoDto) {
        try {
            User user = userRepository.findById(schedaAllenamentoDto.getIdUtente()).orElseThrow(() -> new Exception("user non trovato"));
            SchedaAllenamento schedaAllenamento = new SchedaAllenamento();
            BeanUtils.copyProperties(schedaAllenamentoDto, schedaAllenamento);
            schedaAllenamento.setUtente(user);
            return schedaAllenamentoRepository.save(schedaAllenamento);
        }catch (Exception e){
            log.error("errore salvataggio scheda allenamento", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        schedaAllenamentoRepository.deleteById(id);
    }

    public List<SchedaAllenamento> findAll() {
        return schedaAllenamentoRepository.findAll();
    }

    public SchedaAllenamento findById(Long id) {
        return schedaAllenamentoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public SchedaAllenamento update(SchedaAllenamento schedaAllenamento, Long id) {
        Optional<SchedaAllenamento> schedaAllenamentoOld = schedaAllenamentoRepository.findById(id);
        schedaAllenamento.setId(id);
        if (schedaAllenamentoOld.isPresent()) {
            copyNonNullProperties(schedaAllenamento, schedaAllenamentoOld.get());
            return schedaAllenamentoRepository.save(schedaAllenamentoOld.get());
        }
        else {
            throw new ResourceNotFoundException();
        }
    }

    public ResponseEntity<Page<SchedaAllenamento>> filter(SchedaAllenamentoDtoFilter probe, Integer page, Integer size, String sortField, String sortDirection){
        Pageable pageable;
        Page<SchedaAllenamento> result;
        SchedaAllenamento schedaAllenamento = new SchedaAllenamento();
        if (probe != null) {
            copyNonNullProperties(probe, schedaAllenamento);
        }

        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<SchedaAllenamento> example = Example.of(schedaAllenamento, matcher);

        result = schedaAllenamentoRepository.findAll(example, pageable);

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