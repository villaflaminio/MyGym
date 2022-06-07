package com.salvatorechiacchio.mygym.repository;

import com.salvatorechiacchio.mygym.model.Sensore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SensoreRepository extends JpaRepository<Sensore, Long>, JpaSpecificationExecutor<Sensore> {
}