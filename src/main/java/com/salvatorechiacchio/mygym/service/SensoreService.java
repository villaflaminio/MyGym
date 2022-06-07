package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.dto.SensoreDto;
import com.salvatorechiacchio.mygym.mapper.SensoreMapper;
import com.salvatorechiacchio.mygym.model.Sensore;
import com.salvatorechiacchio.mygym.repository.SensoreRepository;
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
public class SensoreService {
    private final SensoreRepository repository;
    private final SensoreMapper sensoreMapper;

    public SensoreService(SensoreRepository repository, SensoreMapper sensoreMapper) {
        this.repository = repository;
        this.sensoreMapper = sensoreMapper;
    }

    public SensoreDto save(SensoreDto sensoreDto) {
        Sensore entity = sensoreMapper.toEntity(sensoreDto);
        return sensoreMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public SensoreDto findById(Long id) {
        return sensoreMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public Page<SensoreDto> findByCondition(SensoreDto sensoreDto, Pageable pageable) {
        Page<Sensore> entityPage = repository.findAll(pageable);
        List<Sensore> entities = entityPage.getContent();
        return new PageImpl<>(sensoreMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public SensoreDto update(SensoreDto sensoreDto, Long id) {
        SensoreDto data = findById(id);
        Sensore entity = sensoreMapper.toEntity(sensoreDto);
        BeanUtil.copyProperties(data, entity);
        return save(sensoreMapper.toDto(entity));
    }
}