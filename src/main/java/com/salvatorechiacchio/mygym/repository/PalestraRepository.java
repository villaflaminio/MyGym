package com.salvatorechiacchio.mygym.repository;

import com.salvatorechiacchio.mygym.model.Palestra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalestraRepository extends JpaRepository<Palestra, Long> {
}