//package com.salvatorechiacchio.mygym.controller;
//
//import com.salvatorechiacchio.mygym.model.dto.SensoreDto;
//import com.salvatorechiacchio.mygym.mapper.EntityMapper;
//import com.salvatorechiacchio.mygym.mapper.SensoreMapper;
//import com.salvatorechiacchio.mygym.service.SensoreService;
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
//public class SensoreControllerTest {
//    private static final String ENDPOINT_URL = "/api/sensore";
//    @InjectMocks
//    private SensoreController sensoreController;
//    @Mock
//    private SensoreService sensoreService;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(sensoreController)
//                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
//                //.addFilter(CustomFilter::doFilter)
//                .build();
//    }
//
//    @Test
//    public void findAllByPage() throws Exception {
//        Page<SensoreDto> page = new PageImpl<>(Collections.singletonList(SensoreBuilder.getDto()));
//
//        Mockito.when(sensoreService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));
//
//        Mockito.verify(sensoreService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
//        Mockito.verifyNoMoreInteractions(sensoreService);
//
//    }
//
//    @Test
//    public void getById() throws Exception {
//        Mockito.when(sensoreService.findById(ArgumentMatchers.anyLong())).thenReturn(SensoreBuilder.getDto());
//
//        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content()
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
//        Mockito.verify(sensoreService, Mockito.times(1)).findById("1");
//        Mockito.verifyNoMoreInteractions(sensoreService);
//    }
//
//    @Test
//    public void save() throws Exception {
//        Mockito.when(sensoreService.save(ArgumentMatchers.any(SensoreDto.class))).thenReturn(SensoreBuilder.getDto());
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post(ENDPOINT_URL)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(CustomUtils.asJsonString(SensoreBuilder.getDto())))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//        Mockito.verify(sensoreService, Mockito.times(1)).save(ArgumentMatchers.any(SensoreDto.class));
//        Mockito.verifyNoMoreInteractions(sensoreService);
//    }
//
//    @Test
//    public void update() throws Exception {
//        Mockito.when(sensoreService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(SensoreBuilder.getDto());
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(CustomUtils.asJsonString(SensoreBuilder.getDto())))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//        Mockito.verify(sensoreService, Mockito.times(1)).update(ArgumentMatchers.any(SensoreDto.class), ArgumentMatchers.anyLong());
//        Mockito.verifyNoMoreInteractions(sensoreService);
//    }
//
//    @Test
//    public void delete() throws Exception {
//        Mockito.doNothing().when(sensoreService).deleteById(ArgumentMatchers.anyLong());
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(CustomUtils.asJsonString(SensoreBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
//        Mockito.verify(sensoreService, Mockito.times(1)).deleteById(Mockito.anyLong());
//        Mockito.verifyNoMoreInteractions(sensoreService);
//    }
//}