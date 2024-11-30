//package com.gmail.HoangHuy.ecommerce.controller;
//
//import com.gmail.HoangHuy.ecommerce.model.Perfume;
//import com.gmail.HoangHuy.ecommerce.repository.PerfumeRepository;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@SpringBootTest
//public class MainRestControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PerfumeRepository perfumeRepository;
//
//    @Test
//    public void getAllProducts() throws Exception {
//        Perfume perfumeOne = new Perfume();
//        perfumeOne.setId(1L);
//        perfumeOne.setPerfumer("Gucci");
//
//        Perfume perfumeTwo = new Perfume();
//        perfumeTwo.setId(2L);
//        perfumeTwo.setPerfumer("Dior");
//
//        when(perfumeRepository.findAll()).thenReturn(Arrays.asList(perfumeOne, perfumeTwo));
//
//        mockMvc.perform(get("/api/v1/rest"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
//                .andExpect(jsonPath("$[*].perfumer", containsInAnyOrder("Gucci", "Dior")));
//    }
//
//    @Test
//    public void getProduct() throws Exception {
//        Perfume perfumeOne = new Perfume();
//        perfumeOne.setId(1L);
//        perfumeOne.setPerfumer("Gucci");
//
//        when(perfumeRepository.findById(anyLong())).thenReturn(Optional.of(perfumeOne));
//
//        mockMvc.perform(get("/api/v1/rest/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(1)))
//                .andExpect(jsonPath("$.perfumer", equalTo("Gucci")));
//    }
//}
