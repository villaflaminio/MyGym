package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.dto.SchedaAllenamentoDto;
import com.salvatorechiacchio.mygym.mapper.SchedaAllenamentoMapper;
import com.salvatorechiacchio.mygym.model.SchedaAllenamento;
import com.salvatorechiacchio.mygym.repository.SchedaAllenamentoRepository;
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
public class SchedaAllenamentoService {
    private final SchedaAllenamentoRepository repository;
    private final SchedaAllenamentoMapper schedaAllenamentoMapper;

    public SchedaAllenamentoService(SchedaAllenamentoRepository repository, SchedaAllenamentoMapper schedaAllenamentoMapper) {
        this.repository = repository;
        this.schedaAllenamentoMapper = schedaAllenamentoMapper;
    }

    public SchedaAllenamentoDto save(SchedaAllenamentoDto schedaAllenamentoDto) {
        SchedaAllenamento entity = schedaAllenamentoMapper.toEntity(schedaAllenamentoDto);
        return schedaAllenamentoMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public SchedaAllenamentoDto findById(Long id) {
        return schedaAllenamentoMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public Page<SchedaAllenamentoDto> findByCondition(SchedaAllenamentoDto schedaAllenamentoDto, Pageable pageable) {
        Page<SchedaAllenamento> entityPage = repository.findAll(pageable);
        List<SchedaAllenamento> entities = entityPage.getContent();
        return new PageImpl<>(schedaAllenamentoMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public SchedaAllenamentoDto update(SchedaAllenamentoDto schedaAllenamentoDto, Long id) {
        SchedaAllenamentoDto data = findById(id);
        SchedaAllenamento entity = schedaAllenamentoMapper.toEntity(schedaAllenamentoDto);
        BeanUtil.copyProperties(data, entity);
        return save(schedaAllenamentoMapper.toDto(entity));
    }
}