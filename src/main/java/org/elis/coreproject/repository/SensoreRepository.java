package org.elis.coreproject.repository;

import org.elis.coreproject.model.Sensore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SensoreRepository extends JpaRepository<Sensore, Long>, JpaSpecificationExecutor<Sensore> {
}