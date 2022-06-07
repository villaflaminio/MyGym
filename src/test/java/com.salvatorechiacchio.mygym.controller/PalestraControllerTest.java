package com.salvatorechiacchio.mygym.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.google.gson.Gson;
import com.salvatorechiacchio.mygym.controller.CustomUtils;
import com.salvatorechiacchio.mygym.controller.PalestraController;
import com.salvatorechiacchio.mygym.dto.PalestraDto;
import com.salvatorechiacchio.mygym.mapper.EntityMapper;
import com.salvatorechiacchio.mygym.mapper.PalestraMapper;
import com.salvatorechiacchio.mygym.model.Palestra;
import com.salvatorechiacchio.mygym.service.PalestraService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@Transactional
public class PalestraControllerTest {
    private static final String ENDPOINT_URL = "/api/palestra";
    @InjectMocks
    private PalestraController palestraController;
    @Mock
    private PalestraService palestraService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(palestraController)
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                //.addFilter(CustomFilter::doFilter)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<PalestraDto> page = new PageImpl<>(Collections.singletonList(PalestraBuilder.getDto()));

        Mockito.when(palestraService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(palestraService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(palestraService);

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(palestraService.findById(ArgumentMatchers.anyLong())).thenReturn(PalestraBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(palestraService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(palestraService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(palestraService.save(ArgumentMatchers.any(PalestraDto.class))).thenReturn(PalestraBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(PalestraBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(palestraService, Mockito.times(1)).save(ArgumentMatchers.any(PalestraDto.class));
        Mockito.verifyNoMoreInteractions(palestraService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(palestraService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(PalestraBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(PalestraBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(palestraService, Mockito.times(1)).update(ArgumentMatchers.any(PalestraDto.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(palestraService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(palestraService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(PalestraBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(palestraService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(palestraService);
    }
}