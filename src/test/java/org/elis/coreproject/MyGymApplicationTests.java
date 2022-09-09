package org.elis.coreproject;

import org.elis.coreproject.controller.AbbonamentoController;
import org.elis.coreproject.controller.PalestraController;
import org.elis.coreproject.controller.SchedaAllenamentoController;
import org.elis.coreproject.controller.UserController;
import org.elis.coreproject.repository.AbbonamentoRepository;
import org.elis.coreproject.repository.PalestraRepository;
import org.elis.coreproject.repository.SchedaAllenamentoRepository;
import org.elis.coreproject.repository.UserRepository;
import org.elis.coreproject.service.AbbonamentoService;
import org.elis.coreproject.service.PalestraService;
import org.elis.coreproject.service.SchedaAllenamentoService;
import org.elis.coreproject.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
class MyGymApplicationTests {
    // -------------------[CONTROLLERS]--------------------
    @Autowired
    private UserController userController;
    @Autowired
    private SchedaAllenamentoController schedaAllenamentoController;
    @Autowired
    private PalestraController palestraController;
    @Autowired
    private AbbonamentoController abbonamentoController;

    // -------------------[REPOSITORIES]--------------------

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SchedaAllenamentoRepository schedaAllenamentoRepository;
    @Autowired
    private PalestraRepository palestraRepository;
    @Autowired
    private AbbonamentoRepository abbonamentoRepository;

    // -------------------[SERVICES]--------------------

    @Autowired
    private UserService userService;
    @Autowired
    private SchedaAllenamentoService schedaAllenamentoService;
    @Autowired
    private PalestraService palestraService;
    @Autowired
    private AbbonamentoService abbonamentoService;

    // -------------------------------------------------

    /**
     * Verifica che il context sia caricato correttamente.
     */
    @Test
    @Order(1)
    void contextLoads() {
        assertThat(userController).isNotNull();
        assertThat(schedaAllenamentoController).isNotNull();
        assertThat(palestraController).isNotNull();
        assertThat(abbonamentoController).isNotNull();

        assertThat(userRepository).isNotNull();
        assertThat(schedaAllenamentoRepository).isNotNull();
        assertThat(palestraRepository).isNotNull();
        assertThat(abbonamentoRepository).isNotNull();

        assertThat(userService).isNotNull();
        assertThat(schedaAllenamentoService).isNotNull();
        assertThat(palestraService).isNotNull();
        assertThat(abbonamentoService).isNotNull();
    }
}
