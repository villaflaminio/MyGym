package org.elis.coreproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.elis.coreproject.model.User;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDTO {

    public Long id;
    public String email;
    public String password;
    public String role;
    public String nome;
    public String cognome;
    public User callUser;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nome = user.getNome();
        this.cognome = user.getCognome();
    }

    public UserDTO(String email, String password, String role, String nome, String cognome) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.nome = nome;
        this.cognome = cognome;
    }


}