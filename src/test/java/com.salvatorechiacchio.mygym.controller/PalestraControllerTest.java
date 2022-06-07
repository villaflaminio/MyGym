//package com.salvatorechiacchio.mygym.controller;
//
//import com.salvatorechiacchio.mygym.model.Palestra;
//import com.salvatorechiacchio.mygym.service.PalestraService;
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
//
//@Transactional
//public class PalestraControllerTest {
//    private static final String ENDPOINT_URL = "/api/palestra";
//    @InjectMocks
//    private PalestraController palestraController;
//    @Mock
//    private PalestraService palestraService;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(palestraController)
//                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
//                //.addFilter(CustomFilter::doFilter)
//                .build();
//    }
//
//    @Test
//    public void findAllByPage() throws Exception {
////        Page<PalestraDto> page = new PageImpl<>(Collections.singletonList(PalestraBuilder.getDto()));
////
////        Mockito.when(palestraService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(page);
////
////        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andDo(MockMvcResultHandlers.print())
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));
////
////        Mockito.verify(palestraService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
////        Mockito.verifyNoMoreInteractions(palestraService);
//
//    }
//
//    @Test
//    public void getById() throws Exception {
//
//        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content()
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
//        Mockito.verify(palestraService, Mockito.times(1)).findById(1l);
//        Mockito.verifyNoMoreInteractions(palestraService);
//    }
//
//    @Test
//    public void save() throws Exception {
////        Mockito.when(palestraService.save(ArgumentMatchers.any(PalestraDto.class))).thenReturn(PalestraBuilder.getDto());
////
////        mockMvc.perform(
////                        MockMvcRequestBuilders.post(ENDPOINT_URL)
////                                .contentType(MediaType.APPLICATION_JSON)
////                                .content(CustomUtils.asJsonString(PalestraBuilder.getDto())))
////                .andExpect(MockMvcResultMatchers.status().isCreated());
////        Mockito.verify(palestraService, Mockito.times(1)).save(ArgumentMatchers.any(PalestraDto.class));
////        Mockito.verifyNoMoreInteractions(palestraService);
//    }
//
//    @Test
//    public void update() throws Exception {
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(CustomUtils.asJsonString(Palestra.class)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//        Mockito.verify(palestraService, Mockito.times(1)).update(ArgumentMatchers.any(Palestra.class), ArgumentMatchers.anyLong());
//        Mockito.verifyNoMoreInteractions(palestraService);
//    }
//
//    @Test
//    public void delete() throws Exception {
//        Mockito.doNothing().when(palestraService).deleteById(ArgumentMatchers.anyLong());
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(CustomUtils.asJsonString(PalestraBuilder.getIds()))).andExpect(MockMvcResultMatchers.status().isOk());
//        Mockito.verify(palestraService, Mockito.times(1)).deleteById(Mockito.anyLong());
//        Mockito.verifyNoMoreInteractions(palestraService);
//    }
//}