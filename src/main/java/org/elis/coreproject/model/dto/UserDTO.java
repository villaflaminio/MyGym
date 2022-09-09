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
    public String nome;
    public String cognome;

    public UserDTO(Long id, String email, String password, String nome, String cognome) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

}