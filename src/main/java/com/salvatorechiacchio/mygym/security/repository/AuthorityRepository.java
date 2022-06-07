package com.salvatorechiacchio.mygym.security.repository;

import com.salvatorechiacchio.mygym.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
@Component
@RepositoryRestResource(exported = false)
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Authority getByName(String name);
}
