package org.elis.coreproject.repository;

import org.elis.coreproject.model.Abbonamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AbbonamentoRepository extends JpaRepository<Abbonamento, Long>, JpaSpecificationExecutor<Abbonamento> {
}