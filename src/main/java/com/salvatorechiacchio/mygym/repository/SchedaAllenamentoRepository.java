package com.salvatorechiacchio.mygym.repository;

import com.salvatorechiacchio.mygym.model.SchedaAllenamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedaAllenamentoRepository extends JpaRepository<SchedaAllenamento, Long>, JpaSpecificationExecutor<SchedaAllenamento> {
}