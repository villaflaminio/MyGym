package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.dto.PalestraDto;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.repository.PalestraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PalestraService {
    private final PalestraRepository palestraRepository;
    private final PalestraMapper palestraMapper;

    public PalestraService(PalestraRepository palestraRepository, PalestraMapper palestraMapper) {
        this.palestraRepository = palestraRepository;
        this.palestraMapper = palestraMapper;
    }

    public PalestraDto save(PalestraDto palestraDto) {
        Palestra entity = palestraMapper.toEntity(palestraDto);
        return palestraMapper.toDto(palestraRepository.save(entity));
    }

    public void deleteById(Long id) {
        palestraRepository.deleteById(id);
    }

    public Palestra findById(Long id) {
        return palestraRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
    public List<Palestra> findAll() {
        return palestraRepository.findAll();
    }


    public PalestraDto update(PalestraDto palestraDto, Long id) {
        PalestraDto data = findById(id);
        Palestra entity = palestraMapper.toEntity(palestraDto);
        BeanUtil.copyProperties(data, entity);
        return save(palestraMapper.toDto(entity));
    }
}