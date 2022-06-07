package com.salvatorechiacchio.mygym.repository;

import com.salvatorechiacchio.mygym.model.Esercizio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EsercizioRepository extends JpaRepository<Esercizio, Long>, JpaSpecificationExecutor<Esercizio> {
}