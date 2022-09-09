package org.elis.coreproject.service;

import org.apache.commons.lang3.StringUtils;
import org.elis.coreproject.model.Abbonamento;
import org.elis.coreproject.model.User;
import org.elis.coreproject.model.dto.UserDTO;
import org.elis.coreproject.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =========================================================================================
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    // =========================================================================================

    /**
     * @param userDto utente da salvare
     * @return utente salvato
     */
    public User save(UserDTO userDto) {
        try {

            // Creo l'utente
            User user = new User();

            // Copio i dati
            BeanUtils.copyProperties(userDto, user);

            // Salvo l'utente
            return userRepository.save(user);
        } catch (Exception e) {
//            log.error("errore salvataggio utente", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id identificativo dell'utente da cercare
     * @return utente cercato, se presente
     */
    public User findById(Long id) {
        // Controllo se l'utente esiste e lo restituisco, altrimenti lancio eccezione di not found
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * @param id identificativo dell'utente da eliminare
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * @param userDto utente modificato
     * @param id      identificativo dell'utente da modificare
     * @return utente modificato
     */
    public User update(UserDTO userDto, Long id) {
        // Ottengo utente da modificare
        Optional<User> userOld = userRepository.findById(id);
        userDto.setId(id);

        // Controllo se l'utente esiste
        if (userOld.isPresent()) {
            // Copio i dati
            copyNonNullProperties(userDto, userOld.get());
            userDto.setId(id);

            // Salvo l'utente
            return userRepository.save(userOld.get());
        } else {
            // Se non esiste lancio eccezione di not found
            throw new ResourceNotFoundException();
        }
    }

    /**
     * @param probe         utente utilizzato per filtrare
     * @param page          pagina da visualizzare
     * @param size          numero di elementi per pagina
     * @param sortField     campo per ordinamento
     * @param sortDirection direzione di ordinamento
     * @return lista di utenti filtrati
     */
    public ResponseEntity<Page<User>> filter(User probe, Integer page, Integer size, String sortField, String sortDirection) {
        Pageable pageable;

        // Controllo se l'utente da filtrare è nullo.
        if (probe == null) {
            probe = new User(); // Se è nullo creo un nuovo utente
        }

        // Controllo se il campo di ordinamento è nullo.
        if (StringUtils.isEmpty(sortField)) {
            pageable = PageRequest.of(page, size); // Se è nullo ordino per id
        } else {
            // Se non è nullo ordino per il campo di ordinamento
            Sort.Direction dir = StringUtils.isEmpty(sortDirection) ? Sort.Direction.ASC : Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
            pageable = PageRequest.of(page, size, dir, sortField);
        }

        // Filtro gli abbonamenti
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<User> example = Example.of(probe, matcher);

        return ResponseEntity.ok(userRepository.findAll(example, pageable));
    }
}
