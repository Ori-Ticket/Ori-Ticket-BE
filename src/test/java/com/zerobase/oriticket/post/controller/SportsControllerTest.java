package com.zerobase.oriticket.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.oriticket.domain.post.controller.SportsController;
import com.zerobase.oriticket.domain.post.dto.RegisterSportsRequest;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.service.SportsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SportsController.class)
public class SportsControllerTest {

    @MockBean
    private SportsService sportsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String BASE_URL = "/sports";

    private final static Long SPORTS_ID = 1L;
    private final static String SPORTS_NAME = "야구";

    @Test
    @DisplayName("Sports 등록 성공")
    void successRegister() throws Exception{
        //given
        RegisterSportsRequest sportsRequest =
                RegisterSportsRequest.builder()
                        .sportsName(SPORTS_NAME)
                        .build();

        given(sportsService.register(any(RegisterSportsRequest.class)))
                .willReturn(Sports.builder()
                        .sportsId(SPORTS_ID)
                        .sportsName(SPORTS_NAME)
                        .build());

        //when
        //then
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sportsRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sportsId").value(1L))
                .andExpect(jsonPath("$.sportsName").value("야구"));
    }

    @Test
    @DisplayName("Sports 조회 성공")
    void successGet() throws Exception{
        //given
        given(sportsService.get(anyLong()))
                .willReturn(Sports.builder()
                        .sportsId(SPORTS_ID)
                        .sportsName(SPORTS_NAME)
                        .build());

        //when
        //then
        mockMvc.perform(get(BASE_URL+"?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sportsId").value(1L))
                .andExpect(jsonPath("$.sportsName").value("야구"));
    }

    @Test
    @DisplayName("Sports 모두 조회 성공")
    void successGetAll() throws Exception{
        //given
        Sports sports1 = Sports.builder()
                .sportsId(1L)
                .sportsName("야구")
                .build();

        Sports sports2 = Sports.builder()
                .sportsId(2L)
                .sportsName("축구")
                .build();

        Sports sports3 = Sports.builder()
                .sportsId(3L)
                .sportsName("농구")
                .build();

        List<Sports> sportsList 
                = Arrays.asList(sports1, sports2, sports3);

        given(sportsService.getAll())
                .willReturn(sportsList);

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].sportsId").value(1L))
                .andExpect(jsonPath("$.[0].sportsName").value("야구"))
                .andExpect(jsonPath("$.[1].sportsId").value(2L))
                .andExpect(jsonPath("$.[1].sportsName").value("축구"))
                .andExpect(jsonPath("$.[2].sportsId").value(3L))
                .andExpect(jsonPath("$.[2].sportsName").value("농구"));
    }

    @Test
    @DisplayName("Sports 삭제 성공")
    void successDelete() throws Exception{
        //given
        given(sportsService.delete(anyLong()))
                .willReturn(SPORTS_ID);

        //when
        //then
        mockMvc.perform(delete(BASE_URL+"?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }
}
