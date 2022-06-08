package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.exception.UserException;
import com.salvatorechiacchio.mygym.model.User;
import com.salvatorechiacchio.mygym.model.dto.LoginDTO;
import com.salvatorechiacchio.mygym.model.dto.UserDTO;
import com.salvatorechiacchio.mygym.repository.UserRepository;
import com.salvatorechiacchio.mygym.security.helper.UserHelper;
import com.salvatorechiacchio.mygym.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.salvatorechiacchio.mygym.exception.UserException.userExceptionCode.USER_NOT_LOGGED_IN;

@RestController
@RequestMapping("/api")
@Tag(name = "Utente")
public class UserController {
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Dati per il login nel seguente formato
     * {
     * "email" : "mariorossi@gmail.com",
     * "password" : "mario"
     * }
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/authenticate")
    public ResponseEntity<UserHelper.JWTToken> authorize(@Valid @RequestBody LoginDTO loginDto) {
        return userHelper.authorize(loginDto);
    }

    /**
     * Dati per register nel seguente formato
     * {
     * "email" : "mariorossi@gmail.com",
     * "password" : "ciao1234",
     * "firstName" : "mario",
     * "lastName" : "rossi",
     * "region" : 3
     * }
     */
    @PostMapping("/register/user")
    public User registerUser(@Valid @RequestBody UserDTO userDTO) {
        return userHelper.registerUser(userDTO);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUtente(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {

            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Dati per register nel seguente formato
     * {
     * "email" : "mariorossi@gmail.com",
     * "password" : "mario",
     * "firstName" : "mario",
     * "lastName" : "rossi",
     * <p>
     * }
     */
    @PostMapping("/register/admin")
    public User registerAdmin(@Valid @RequestBody UserDTO userDTO) {
        setUser(userDTO);

        return userHelper.registerAdmin(userDTO);
    }

    private void setUser(UserDTO userDTO) {
        Optional<User> userLogged = userService.getUserWithAuthorities();
        if (userLogged.isPresent())
            //userDTO.callUser = userLogged.get();
            return;
        else
            throw new UserException(USER_NOT_LOGGED_IN);
    }

    // =================================================================================================================

    @GetMapping("/user")
    public ResponseEntity<User> getActualUser() {
        return ResponseEntity.ok(userService.getUserWithAuthorities().get());
    }
}
