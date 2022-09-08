package org.elis.coreproject.repository;

import org.elis.coreproject.model.SchedaAllenamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedaAllenamentoRepository extends JpaRepository<SchedaAllenamento, Long>, JpaSpecificationExecutor<SchedaAllenamento> {
}