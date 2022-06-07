package com.salvatorechiacchio.mygym.mapper;

import com.salvatorechiacchio.mygym.dto.PalestraDto;
import com.salvatorechiacchio.mygym.model.Palestra;

@Mapper(componentModel = "spring")
public interface PalestraMapper extends EntityMapper<PalestraDto, Palestra> {
}