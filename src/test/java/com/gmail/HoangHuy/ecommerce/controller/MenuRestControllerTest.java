//package com.gmail.HoangHuy.ecommerce.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gmail.HoangHuy.ecommerce.model.Perfume;
//import com.gmail.HoangHuy.ecommerce.dto.PerfumeSearchFilterDto;
//import com.gmail.HoangHuy.ecommerce.repository.PerfumeRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@SpringBootTest
//class MenuRestControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @MockBean
//    private PerfumeRepository perfumeRepository;
//
//    @Test
//    void findProductsByFilterParams() throws Exception {
//
//    }
//
//    @Test
//    void findByPerfumeGender() throws Exception {
//        PerfumeSearchFilterDto perfumeSearchFilterDto = new PerfumeSearchFilterDto();
//        perfumeSearchFilterDto.setPerfumeGender("женский");
//
//        Perfume perfume = new Perfume();
//        perfume.setPerfumer("Gucci");
//        perfume.setPerfumeGender("женский");
//
//        when(perfumeRepository.findByPerfumeGenderOrderByPriceDesc("женский"))
//                .thenReturn(Arrays.asList(perfume));
//
//        mockMvc.perform(post("/api/v1/rest/menu/gender")
//                .contentType("application/json")
//                .content(mapper.writeValueAsString(perfumeSearchFilterDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].perfumer", contains("Gucci")))
//                .andExpect(jsonPath("$[*].perfumeGender", contains("женский")));
//    }
//
//    @Test
//    void findByPerfumer() throws Exception {
//        PerfumeSearchFilterDto perfumeSearchFilterDto = new PerfumeSearchFilterDto();
//        perfumeSearchFilterDto.setPerfumer("Gucci");
//
//        Perfume perfume = new Perfume();
//        perfume.setPerfumer("Gucci");
//        perfume.setPerfumeGender("женский");
//
//        when(perfumeRepository.findByPerfumerOrderByPriceDesc("Gucci"))
//                .thenReturn(Arrays.asList(perfume));
//
//        mockMvc.perform(post("/api/v1/rest/menu/perfumer")
//                .contentType("application/json")
//                .content(mapper.writeValueAsString(perfumeSearchFilterDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].perfumer", contains("Gucci")))
//                .andExpect(jsonPath("$[*].perfumeGender", contains("женский")));
//    }
//}