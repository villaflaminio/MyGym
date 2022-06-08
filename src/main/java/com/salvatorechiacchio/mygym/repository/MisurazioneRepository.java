package com.salvatorechiacchio.mygym.repository;

import com.salvatorechiacchio.mygym.model.Misurazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MisurazioneRepository extends JpaRepository<Misurazione, Long> {
}