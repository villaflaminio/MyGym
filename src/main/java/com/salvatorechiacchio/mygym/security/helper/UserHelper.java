package com.salvatorechiacchio.mygym.security.helper;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.common.base.Preconditions;
import com.salvatorechiacchio.mygym.exception.UserException;
import com.salvatorechiacchio.mygym.security.jwt.JWTFilter;
import com.salvatorechiacchio.mygym.security.jwt.TokenProvider;
import com.salvatorechiacchio.mygym.model.Authority;
import com.salvatorechiacchio.mygym.model.User;
import com.salvatorechiacchio.mygym.repository.AuthorityRepository;
import com.salvatorechiacchio.mygym.repository.UserRepository;
import com.salvatorechiacchio.mygym.model.dto.LoginDTO;
import com.salvatorechiacchio.mygym.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.salvatorechiacchio.mygym.exception.UserException.userExceptionCode.AUTHORITY_NOT_EXIST;
import static com.salvatorechiacchio.mygym.exception.UserException.userExceptionCode.USER_ALREADY_EXISTS;


@Component
public class UserHelper {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;


    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserHelper(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDTO loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //SecurityContextHolder è una classe di supporto, che forniscono l'accesso al contesto di protezione
        SecurityContextHolder.getContext().setAuthentication(authentication);

        boolean rememberMe = (loginDto.rememberMe == null) ? false : loginDto.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new JWTToken(jwt,userRepository.findByEmail(loginDto.email)), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    public static class JWTToken {

        private String idToken;
        private final UserDTO user;
        JWTToken(String idToken, User user) {
            this.idToken = idToken;
            this.user = new UserDTO(user);
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }
        @JsonProperty("user")
        UserDTO getUser() {
            return user;
        }
        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

    public void ceckUser(UserDTO userDTO) {
        Preconditions.checkArgument(Objects.nonNull(userDTO.email));
        Preconditions.checkArgument(Objects.nonNull(userDTO.password));
        Preconditions.checkArgument(Objects.nonNull(userDTO.role));
        Preconditions.checkArgument(Objects.nonNull(userDTO.nome));
        Preconditions.checkArgument(Objects.nonNull(userDTO.cognome));

    }

    public User registerUser(UserDTO userDTO) {
        userDTO.role = "USER";
        return register(userDTO);

    }

    public User registerSecretary(UserDTO userDTO) {
        userDTO.role = "SECRETARY";
        return register(userDTO);

    }

    public User registerAdminSecretary(UserDTO userDTO) {
        userDTO.role = "ADMIN_SECRETARY";
        return register(userDTO);

    }

    public User registerAdmin(UserDTO userDTO) {
        userDTO.role = "ADMIN";
        return register(userDTO);
    }


    private User register(UserDTO userDTO) {
        ceckUser(userDTO);
        if (!userRepository.existsByEmail(userDTO.email) && !role(userDTO).isEmpty()) {
            return userRepository.save(User.builder()
                    .email(userDTO.email)
                    .password(bcryptEncoder.encode(userDTO.password))
                    .nome(userDTO.nome)
                    .cognome(userDTO.cognome)
                    .activated(true)
                    .authorities(role(userDTO))
                    .build());
        }
        throw new UserException(USER_ALREADY_EXISTS);
    }

    private Set<Authority> role(UserDTO userDTO) {
        Set<Authority> author = new HashSet<>();
        switch (userDTO.role) {
            case "USER":
                author.add(authorityRepository.getByName("ROLE_USER"));
                break;

            case "ADMIN":
                author.add(authorityRepository.getByName("ROLE_USER"));
                author.add(authorityRepository.getByName("ROLE_ADMIN"));
                break;
            default:
                throw new UserException(AUTHORITY_NOT_EXIST);
        }
        return author;
    }



}
