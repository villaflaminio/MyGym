package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.dto.PalestraDto;
import com.salvatorechiacchio.mygym.mapper.PalestraMapper;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.repository.PalestraRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PalestraService {
    private final PalestraRepository repository;
    private final PalestraMapper palestraMapper;

    public PalestraService(PalestraRepository repository, PalestraMapper palestraMapper) {
        this.repository = repository;
        this.palestraMapper = palestraMapper;
    }

    public PalestraDto save(PalestraDto palestraDto) {
        Palestra entity = palestraMapper.toEntity(palestraDto);
        return palestraMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PalestraDto findById(Long id) {
        return palestraMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public Page<PalestraDto> findByCondition(PalestraDto palestraDto, Pageable pageable) {
        Page<Palestra> entityPage = repository.findAll(pageable);
        List<Palestra> entities = entityPage.getContent();
        return new PageImpl<>(palestraMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PalestraDto update(PalestraDto palestraDto, Long id) {
        PalestraDto data = findById(id);
        Palestra entity = palestraMapper.toEntity(palestraDto);
        BeanUtil.copyProperties(data, entity);
        return save(palestraMapper.toDto(entity));
    }
}