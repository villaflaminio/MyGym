package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.model.User;
import com.salvatorechiacchio.mygym.model.dto.SchedaAllenamentoDto;
import com.salvatorechiacchio.mygym.model.SchedaAllenamento;
import com.salvatorechiacchio.mygym.repository.SchedaAllenamentoRepository;
import com.salvatorechiacchio.mygym.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EsercizioService esercizioService;
    @Autowired
    private UserRepository userRepository;

    public SchedaAllenamento save(SchedaAllenamentoDto schedaAllenamentoDto) {

        try {
            User user = userRepository.findById(schedaAllenamentoDto.getIdUtente()).orElseThrow(() -> new Exception("user non trovato"));

            SchedaAllenamento schedaAllenamento = new SchedaAllenamento();
            BeanUtils.copyProperties(schedaAllenamentoDto, schedaAllenamento);
            List<Esercizio> esercizi = esercizioService.getAllByIdList(schedaAllenamentoDto.getIdEsercizi());
            schedaAllenamento.setEsercizi(esercizi);
            schedaAllenamento.setUtente(user);

            return schedaAllenamentoRepository.save(schedaAllenamento);
        } catch (Exception e) {
            log.error("errore salvataggio scheda allenamento", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        SchedaAllenamento schedaAllenamento = schedaAllenamentoRepository.findById(id).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        schedaAllenamento.setEsercizi(null);
        schedaAllenamento.setUtente(null);
        schedaAllenamentoRepository.delete(schedaAllenamento);
    }

    public List<SchedaAllenamento> findAll() {
        return schedaAllenamentoRepository.findAll();
    }

    public SchedaAllenamento findById(Long id) {
        return schedaAllenamentoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public SchedaAllenamento update(SchedaAllenamentoDto schedaAllenamentoDto, Long id) {
        Optional<SchedaAllenamento> schedaAllenamentoOld = schedaAllenamentoRepository.findById(id);
        schedaAllenamentoDto.setId(id);
        try {
        if (schedaAllenamentoOld.isPresent()) {
            copyNonNullProperties(schedaAllenamentoDto, schedaAllenamentoOld.get());

            User user = userRepository.findById(schedaAllenamentoDto.getIdUtente()).orElseThrow(() -> new Exception("user non trovato"));
            List<Esercizio> esercizi = esercizioService.getAllByIdList(schedaAllenamentoDto.getIdEsercizi());
            schedaAllenamentoOld.get().setEsercizi(esercizi);
            schedaAllenamentoOld.get().setUtente(user);
            schedaAllenamentoOld.get().setId(id);
            return schedaAllenamentoRepository.save(schedaAllenamentoOld.get());
        }else {
            throw new ResourceNotFoundException();
        }
        } catch (Exception e) {
            log.error("errore aggiornamento scheda allenamento", e);
            throw new RuntimeException(e);

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