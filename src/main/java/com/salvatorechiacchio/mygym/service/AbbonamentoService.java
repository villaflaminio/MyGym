package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.Abbonamento;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.model.User;
import com.salvatorechiacchio.mygym.model.dto.AbbonamentoDto;
import com.salvatorechiacchio.mygym.repository.AbbonamentoRepository;
import com.salvatorechiacchio.mygym.repository.PalestraRepository;
import com.salvatorechiacchio.mygym.security.repository.UserRepository;
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
public class AbbonamentoService {
    @Autowired
    AbbonamentoRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PalestraRepository palestraRepository;

    public Abbonamento save(AbbonamentoDto abbonamentoDto)  {
        try {
            User user = userRepository.findById(abbonamentoDto.getIdUtente()).orElseThrow(() -> new Exception("user non trovato"));
            Palestra palestra = palestraRepository.findById(abbonamentoDto.getIdPalestra()).orElseThrow(() -> new Exception("palestra non trovata"));
            Abbonamento abbonamento = new Abbonamento();
            BeanUtils.copyProperties(abbonamentoDto, abbonamento);
            abbonamento.setPalestra(palestra);
            abbonamento.setUtente(user);
            return repository.save(abbonamento);
        }catch (Exception e){
            log.error("errore salvataggio abbonamento", e);
            throw new RuntimeException(e);
        }
    }
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    public Abbonamento findById(Long id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
    public List<Abbonamento> findAll() {
        return repository.findAll();
    }

    public Abbonamento update(Abbonamento abbonamento, Long id) {
        Optional<Abbonamento> abbonamentoOld = repository.findById(id);
        abbonamento.setId(id);
        if (abbonamentoOld.isPresent()) {
            copyNonNullProperties(abbonamento, abbonamentoOld.get());
            return  repository.save(abbonamentoOld.get());
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