package com.zerobase.oriticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.oriticket.domain.post.controller.AwayTeamController;
import com.zerobase.oriticket.domain.post.dto.RegisterAwayTeamRequest;
import com.zerobase.oriticket.domain.post.entity.AwayTeam;
import com.zerobase.oriticket.domain.post.entity.Sports;
import com.zerobase.oriticket.domain.post.service.AwayTeamService;
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

@WebMvcTest(AwayTeamController.class)
public class    AwayTeamControllerTest {

    @MockBean
    private AwayTeamService awayTeamService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String BASE_URL = "/awayteam";

    private final static Long AWAY_TEAM_ID = 1L;
    private final static Long SPORTS_ID = 1L;
    private final static String SPORTS_NAME = "야구";
    private final static String AWAY_TEAM_NAME = "두산";

    @Test
    @DisplayName("AwayTeam 등록 성공")
    void successRegister() throws Exception{
        //given
        RegisterAwayTeamRequest awayTeamRequest =
                RegisterAwayTeamRequest.builder()
                        .sportsId(SPORTS_ID)
                        .awayTeamName(AWAY_TEAM_NAME)
                        .build();

        Sports sports = Sports.builder()
                .sportsId(SPORTS_ID)
                .sportsName(SPORTS_NAME)
                .build();

        given(awayTeamService.register(any()))
                .willReturn(AwayTeam.builder()
                        .awayTeamId(AWAY_TEAM_ID)
                        .sports(sports)
                        .awayTeamName(AWAY_TEAM_NAME)
                        .build());

        //when
        //then
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(awayTeamRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.awayTeamId").value(1L))
                .andExpect(jsonPath("$.sportsId").value(1L))
                .andExpect(jsonPath("$.awayTeamName").value("두산"));
    }

    @Test
    @DisplayName("AwayTeam 조회 성공")
    void successGet() throws Exception{
        //given
        Sports sports = Sports.builder()
                .sportsId(SPORTS_ID)
                .sportsName(SPORTS_NAME)
                .build();

        given(awayTeamService.get(anyLong()))
                .willReturn(AwayTeam.builder()
                        .awayTeamId(AWAY_TEAM_ID)
                        .sports(sports)
                        .awayTeamName(AWAY_TEAM_NAME)
                        .build());

        //when
        //then
        mockMvc.perform(get(BASE_URL+"?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.awayTeamId").value(1L))
                .andExpect(jsonPath("$.sportsId").value(1L))
                .andExpect(jsonPath("$.awayTeamName").value("두산"));
    }

    @Test
    @DisplayName("AwayTeam 모두 조회 성공")
    void successGetAll() throws Exception{
        //given
        Sports sports = Sports.builder()
                .sportsId(SPORTS_ID)
                .sportsName(SPORTS_NAME)
                .build();

        AwayTeam awayTeam1 = AwayTeam.builder()
                .awayTeamId(1L)
                .sports(sports)
                .awayTeamName("기아")
                .build();

        AwayTeam awayTeam2 = AwayTeam.builder()
                .awayTeamId(2L)
                .sports(sports)
                .awayTeamName("한화")
                .build();

        AwayTeam awayTeam3 = AwayTeam.builder()
                .awayTeamId(3L)
                .sports(sports)
                .awayTeamName("두산")
                .build();



        List<AwayTeam> awayTeamList
                = Arrays.asList(awayTeam1, awayTeam2, awayTeam3);

        given(awayTeamService.getAll())
                .willReturn(awayTeamList);

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].awayTeamId").value(1L))
                .andExpect(jsonPath("$.[0].sportsId").value(1L))
                .andExpect(jsonPath("$.[0].awayTeamName").value("기아"))
                .andExpect(jsonPath("$.[1].awayTeamId").value(2L))
                .andExpect(jsonPath("$.[1].sportsId").value(1L))
                .andExpect(jsonPath("$.[1].awayTeamName").value("한화"))
                .andExpect(jsonPath("$.[2].awayTeamId").value(3L))
                .andExpect(jsonPath("$.[2].sportsId").value(1L))
                .andExpect(jsonPath("$.[2].awayTeamName").value("두산"));

    }

    @Test
    @DisplayName("SportId로 AwayTeam 조회 성공")
    void successGetBySportId() throws Exception{
        //given
        Sports sports = Sports.builder()
                .sportsId(SPORTS_ID)
                .sportsName(SPORTS_NAME)
                .build();

        AwayTeam awayTeam = AwayTeam.builder()
                .awayTeamId(AWAY_TEAM_ID)
                .sports(sports)
                .awayTeamName(AWAY_TEAM_NAME)
                .build();

        List<AwayTeam> awayTeamList
                = Arrays.asList(awayTeam);

        given(awayTeamService.getBySportsId(SPORTS_ID))
                .willReturn(awayTeamList);

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/sports?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].awayTeamId").value(1L))
                .andExpect(jsonPath("$.[0].sportsId").value(1L))
                .andExpect(jsonPath("$.[0].awayTeamName").value("두산"));

    }

    @Test
    @DisplayName("AwayTeam 삭제 성공")
    void successDelete() throws Exception{
        //given
        given(awayTeamService.delete(anyLong()))
                .willReturn(AWAY_TEAM_ID);

        //when
        //then
        mockMvc.perform(delete(BASE_URL+"?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }
}
