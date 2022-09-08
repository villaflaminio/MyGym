package org.elis.coreproject.repository;

import org.elis.coreproject.model.Misurazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MisurazioneRepository extends JpaRepository<Misurazione, Long> {
}