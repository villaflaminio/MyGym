package com.salvatorechiacchio.mygym.security.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salvatorechiacchio.mygym.security.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    public String email;
    public String password;
    public String role;
    public String nome;
    public String cognome;
    public User callUser;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.nome = user.getNome();
        this.cognome = user.getCognome();
    }
}