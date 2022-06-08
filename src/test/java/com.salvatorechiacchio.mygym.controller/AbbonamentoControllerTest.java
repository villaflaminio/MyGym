//package com.salvatorechiacchio.mygym.controller;
//
//import com.salvatorechiacchio.mygym.model.Abbonamento;
//import com.salvatorechiacchio.mygym.service.AbbonamentoService;
//import org.hamcrest.core.Is;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.*;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//@Transactional
//public class AbbonamentoControllerTest {
//    private static final String ENDPOINT_URL = "/api/abbonamento";
//    @InjectMocks
//    private AbbonamentoController abbonamentoController;
//    @Mock
//    private AbbonamentoService abbonamentoService;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(abbonamentoController)
//                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
//                //.addFilter(CustomFilter::doFilter)
//                .build();
//    }
//
//
//    @Test
//    public void getById() throws Exception {
//
//        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content()
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
//        Mockito.verify(abbonamentoService, Mockito.times(1)).findById(1l);
//        Mockito.verifyNoMoreInteractions(abbonamentoService);
//    }
//
//    @Test
//    public void save() throws Exception {
////        Mockito.when(abbonamentoService.save(ArgumentMatchers.any(Abbonamento.class)));
////
////        mockMvc.perform(
////                        MockMvcRequestBuilders.post(ENDPOINT_URL)
////                                .contentType(MediaType.APPLICATION_JSON)
////                                .content(CustomUtils.asJsonString(Abbonamento.class)))
////                .andExpect(MockMvcResultMatchers.status().isCreated());
////        Mockito.verify(abbonamentoService, Mockito.times(1)).save(ArgumentMatchers.any(Abbonamento.class));
////        Mockito.verifyNoMoreInteractions(abbonamentoService);
////    }
////
////    @Test
////    public void update() throws Exception {
////
////        mockMvc.perform(
////                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
////                                .contentType(MediaType.APPLICATION_JSON)
////                                .content(CustomUtils.asJsonString(Abbonamento.class)))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////        Mockito.verify(abbonamentoService, Mockito.times(1)).update(ArgumentMatchers.any(Abbonamento.class), ArgumentMatchers.anyLong());
////        Mockito.verifyNoMoreInteractions(abbonamentoService);
////    }
////
////    @Test
////    public void delete() throws Exception {
////        Mockito.doNothing().when(abbonamentoService).deleteById(ArgumentMatchers.anyLong());
////        mockMvc.perform(
////                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(CustomUtils.asJsonString(Abbonamento.class))).andExpect(MockMvcResultMatchers.status().isOk());
////        Mockito.verify(abbonamentoService, Mockito.times(1)).deleteById(Mockito.anyLong());
////        Mockito.verifyNoMoreInteractions(abbonamentoService);
////    }
//}