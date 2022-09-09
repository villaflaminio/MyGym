package org.elis.coreproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.elis.coreproject.model.User;
import org.elis.coreproject.model.dto.UserDTO;
import org.elis.coreproject.repository.UserRepository;
import org.elis.coreproject.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Utente")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    // =================================================================================================================

    /**
     * @return Tutti gli utenti presenti nel database.
     */
    @Operation(summary = "findAll", description = "Ottieni tutti gli utenti")
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    /**
     * @param userDto Dto contenente i dati dell'utente da salvare.
     * @return L'utente salvato.
     */
    @Operation(summary = "save", description = "Salva un nuovo utente")
    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Validated UserDTO userDto) {
        return ResponseEntity.ok(userService.save(userDto));
    }

    /**
     * @param id Identificativo dell'utente da ottenere.
     * @return L'utente con l'identificativo specificato.
     */
    @Operation(summary = "findById", description = "Ottieni l'utente specificato dall'id")
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
        User utente = userService.findById(id);
        return ResponseEntity.ok(utente);
    }

    /**
     * @param id Identificativo dell'utente da eliminare.
     */
    @Operation(summary = "delete", description = "Elimina l'utente specificato dall'id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        //            log.error("Unable to delete non-existent data！");
        Optional.ofNullable(userService.findById(id)).orElseThrow(ResourceNotFoundException::new);
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param userDto Dto contenente i dati dell'utente da aggiornare.
     * @param id      Identificativo dell'utente da aggiornare.
     * @return L'utente aggiornato.
     */
    @Operation(summary = "update", description = "Aggiorna l'utente specificato dall'id")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody UserDTO userDto, @PathVariable("id") Long id) {
        userService.update(userDto, id);
        return ResponseEntity.ok().build();
    }

    /**
     * @param probe         Dto contenente i dati dell'utente da cercare.
     * @param page          La pagina da cercare.
     * @param size          La dimensione della pagina.
     * @param sortField     Il campo per cui ordinare.
     * @param sortDirection La direzione di ordinamento.
     * @return La pagina di risultati della ricerca.
     */
    @Operation(summary = "filter", description = "Filtra utenti con i parametri specificati")
    @PostMapping("/filter")
    ResponseEntity<Page<User>> filter(
            @RequestBody(required = false) User probe,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, name = "sortField") String sortField,
            @RequestParam(required = false, name = "sortDirection") String sortDirection) {
        return userService.filter(probe, page, size, sortField, sortDirection);
    }
}
