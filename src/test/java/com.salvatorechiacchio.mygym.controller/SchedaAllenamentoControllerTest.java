//package com.salvatorechiacchio.mygym.controller;
//
//import com.salvatorechiacchio.mygym.model.dto.SchedaAllenamentoDto;
//import com.salvatorechiacchio.mygym.mapper.EntityMapper;
//import com.salvatorechiacchio.mygym.mapper.SchedaAllenamentoMapper;
//import com.salvatorechiacchio.mygym.service.SchedaAllenamentoService;
//import org.hamcrest.Matchers;
//import org.hamcrest.core.Is;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.*;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//
//@Transactional
//public class SchedaAllenamentoControllerTest {
//    private static final String ENDPOINT_URL = "/api/scheda-allenamento";
//    @InjectMocks
//    private SchedaAllenamentoController schedaallenamentoController;
//    @Mock
//    private SchedaAllenamentoService schedaallenamentoService;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(schedaallenamentoController)
//                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
//                //.addFilter(CustomFilter::doFilter)
//                .build();
//    }
//
//    @Test
//    public void findAllByPage() throws Exception {
//        Page<SchedaAllenamentoDto> page = new PageImpl<>(Collections.singletonList(SchedaAllenamentoBuilder.getDto()));
//
//        Mockito.when(schedaallenamentoService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));
//
//        Mockito.verify(schedaallenamentoService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
//        Mockito.verifyNoMoreInteractions(schedaallenamentoService);
//
//    }
//
//    @Test
//    public void getById() throws Exception {
//        Mockito.when(schedaallenamentoService.findById(ArgumentMatchers.anyLong())).thenReturn(SchedaAllenamentoBuilder.getDto());
//
//        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content()
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
//        Mockito.verify(schedaallenamentoService, Mockito.times(1)).findById("1");
//        Mockito.verifyNoMoreInteractions(schedaallenamentoService);
//    }
//
//    @Test
//    public void save() throws Exception {
//        Mockito.when(schedaallenamentoService.save(ArgumentMatchers.any(SchedaAllenamentoDto.class))).thenReturn(SchedaAllenamentoBuilder.getDto());
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post(ENDPOINT_URL)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(CustomUtils.asJsonString(SchedaAllenamentoBuilder.getDto())))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//        Mockito.verify(schedaallenamentoService, Mockito.times(1)).save(ArgumentMatchers.any(SchedaAllenamentoDto.class));
//        Mockito.verifyNoMoreInteractions(schedaallenamentoService);
//    }
//
//    @Test
//    public void update() throws Exception {
//        Mockito.when(schedaallenamentoService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(SchedaAllenamentoBuilder.getDto());
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(CustomUtils.asJsonString(SchedaAllenamentoBuilder.getDto())))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//        Mockito.verify(schedaallenamentoService, Mockito.times(1)).update(ArgumentMatchers.any(SchedaAllenamentoDto.class), ArgumentMatchers.anyLong());
//        Mockito.verifyNoMoreInteractions(schedaallenamentoService);
//    }
//
//    @Test
//    public void delete() throws Exception {
//        Mockito.doNothing().when(schedaallenamentoService).deleteById(ArgumentMatchers.anyLong());
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(CustomUtils.asJsonString(SchedaAllenamentoBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
//        Mockito.verify(schedaallenamentoService, Mockito.times(1)).deleteById(Mockito.anyLong());
//        Mockito.verifyNoMoreInteractions(schedaallenamentoService);
//    }
//}