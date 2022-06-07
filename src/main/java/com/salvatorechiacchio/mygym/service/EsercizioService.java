package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.dto.EsercizioDto;
import com.salvatorechiacchio.mygym.mapper.EsercizioMapper;
import com.salvatorechiacchio.mygym.model.Esercizio;
import com.salvatorechiacchio.mygym.repository.EsercizioRepository;
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
public class EsercizioService {
    private final EsercizioRepository repository;
    private final EsercizioMapper esercizioMapper;

    public EsercizioService(EsercizioRepository repository, EsercizioMapper esercizioMapper) {
        this.repository = repository;
        this.esercizioMapper = esercizioMapper;
    }

    public EsercizioDto save(EsercizioDto esercizioDto) {
        Esercizio entity = esercizioMapper.toEntity(esercizioDto);
        return esercizioMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public EsercizioDto findById(Long id) {
        return esercizioMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public Page<EsercizioDto> findByCondition(EsercizioDto esercizioDto, Pageable pageable) {
        Page<Esercizio> entityPage = repository.findAll(pageable);
        List<Esercizio> entities = entityPage.getContent();
        return new PageImpl<>(esercizioMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public EsercizioDto update(EsercizioDto esercizioDto, Long id) {
        EsercizioDto data = findById(id);
        Esercizio entity = esercizioMapper.toEntity(esercizioDto);
        BeanUtil.copyProperties(data, entity);
        return save(esercizioMapper.toDto(entity));
    }
}