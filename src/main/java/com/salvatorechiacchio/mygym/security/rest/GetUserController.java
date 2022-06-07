package com.salvatorechiacchio.mygym.security.rest;

import com.salvatorechiacchio.mygym.security.model.User;
import com.salvatorechiacchio.mygym.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GetUserController {

   private final UserService userService;

   public GetUserController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("/user")
   public ResponseEntity<User> getActualUser() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().get());
   }
}