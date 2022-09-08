package org.elis.coreproject.service;

import org.elis.coreproject.model.User;
import org.elis.coreproject.repository.UserRepository;
import org.elis.coreproject.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
    }

}
