package com.salvatorechiacchio.mygym.service;

import com.salvatorechiacchio.mygym.security.SecurityUtils;
import com.salvatorechiacchio.mygym.model.User;
import com.salvatorechiacchio.mygym.repository.UserRepository;
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
