package com.salvatorechiacchio.mygym.controller;

import com.salvatorechiacchio.mygym.model.dto.EsercizioDto;
import com.salvatorechiacchio.mygym.mapper.EntityMapper;
import com.salvatorechiacchio.mygym.mapper.EsercizioMapper;
import com.salvatorechiacchio.mygym.service.EsercizioService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Transactional
public class EsercizioControllerTest {
    private static final String ENDPOINT_URL = "/api/esercizio";
    @InjectMocks
    private EsercizioController esercizioController;
    @Mock
    private EsercizioService esercizioService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(esercizioController)
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                //.addFilter(CustomFilter::doFilter)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<EsercizioDto> page = new PageImpl<>(Collections.singletonList(EsercizioBuilder.getDto()));

        Mockito.when(esercizioService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(esercizioService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(esercizioService);

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(esercizioService.findById(ArgumentMatchers.anyLong())).thenReturn(EsercizioBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(esercizioService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(esercizioService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(esercizioService.save(ArgumentMatchers.any(EsercizioDto.class))).thenReturn(EsercizioBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(EsercizioBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(esercizioService, Mockito.times(1)).save(ArgumentMatchers.any(EsercizioDto.class));
        Mockito.verifyNoMoreInteractions(esercizioService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(esercizioService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(EsercizioBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(EsercizioBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(esercizioService, Mockito.times(1)).update(ArgumentMatchers.any(EsercizioDto.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(esercizioService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(esercizioService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(EsercizioBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(esercizioService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(esercizioService);
    }
}