package com.zerobase.oriticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.oriticket.domain.post.constants.SaleStatus;
import com.zerobase.oriticket.domain.post.controller.PostController;
import com.zerobase.oriticket.domain.post.dto.RegisterPostRequest;
import com.zerobase.oriticket.domain.post.entity.*;
import com.zerobase.oriticket.domain.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostController.class)
public class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String BASE_URL = "/posts";

    private final static Long SALE_POST_ID = 1L;
    private final static Long MEMBER_ID = 1L;
    private final static Long SPORTS_ID = 1L;
    private final static Long STADIUM_ID = 1L;
    private final static Long AWAY_TEAM_ID = 1L;
    private final static Long TICKET_ID = 1L;
    private final static Integer QUANTITY = 1;
    private final static Integer SALE_PRICE = 10000;
    private final static Integer ORIGINAL_PRICE = 20000;
    private final static LocalDateTime EXPIRATION_AT = LocalDateTime.now().plusDays(1);
    private final static boolean IS_SUCCESSIVE = false;
    private final static String SEAT_INFO = "seat info";
    private final static String IMG_URL = "image url";
    private final static String NOTE = "note";

    private final static String SPORTS_NAME = "야구";
    private final static String STADIUM_NAME = "고척돔";
    private final static String HOME_TEAM_NAME = "두산";
    private final static String AWAY_TEAM_NAME = "한화";

    @Test
    void successRegister() throws Exception {
        //given
        RegisterPostRequest postRequest =
                RegisterPostRequest.builder()
                        .memberId(MEMBER_ID)
                        .sportsId(SPORTS_ID)
                        .stadiumId(STADIUM_ID)
                        .awayTeamId(AWAY_TEAM_ID)
                        .quantity(QUANTITY)
                        .salePrice(SALE_PRICE)
                        .originalPrice(ORIGINAL_PRICE)
                        .expirationAt(EXPIRATION_AT)
                        .isSuccessive(IS_SUCCESSIVE)
                        .seatInfo(SEAT_INFO)
                        .imgUrl(IMG_URL)
                        .note(NOTE)
                        .build();

        Sports sports = Sports.builder()
                .sportsId(SPORTS_ID)
                .sportsName(SPORTS_NAME)
                .build();

        Stadium stadium = Stadium.builder()
                .stadiumId(STADIUM_ID)
                .sports(sports)
                .stadiumName(STADIUM_NAME)
                .homeTeamName(HOME_TEAM_NAME)
                .build();

        AwayTeam awayTeam = AwayTeam.builder()
                .awayTeamId(AWAY_TEAM_ID)
                .sports(sports)
                .awayTeamName(AWAY_TEAM_NAME)
                .build();

        Ticket ticket = Ticket.builder()
                .ticketId(TICKET_ID)
                .sports(sports)
                .stadium(stadium)
                .awayTeam(awayTeam)
                .quantity(QUANTITY)
                .salePrice(SALE_PRICE)
                .originalPrice(ORIGINAL_PRICE)
                .expirationAt(EXPIRATION_AT)
                .isSuccessive(IS_SUCCESSIVE)
                .seatInfo(SEAT_INFO)
                .imgUrl(IMG_URL)
                .note(NOTE)
                .build();

        given(postService.registerPost(any()))
                .willReturn(Post.builder()
                        .salePostId(SALE_POST_ID)
                        .memberId(MEMBER_ID)
                        .ticket(ticket)
                        .saleStatus(SaleStatus.FOR_SALE)
                        .createdAt(LocalDateTime.now())
                        .build());
        //when
        //then
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.memberId").value(1L))
                .andExpect(jsonPath("$.ticket.sportsId").value(1L))
                .andExpect(jsonPath("$.ticket.stadiumId").value(1L))
                .andExpect(jsonPath("$.ticket.awayTeamId").value(1L))
                .andExpect(jsonPath("$.ticket.quantity").value(1))
                .andExpect(jsonPath("$.ticket.salePrice").value(10000))
                .andExpect(jsonPath("$.ticket.originalPrice").value(20000))
                .andExpect(jsonPath("$.ticket.isSuccessive").value(false))
                .andExpect(jsonPath("$.ticket.seatInfo").value("seat info"))
                .andExpect(jsonPath("$.ticket.imgUrl").value("image url"))
                .andExpect(jsonPath("$.ticket.note").value("note"))
                .andExpect(jsonPath("$.saleStatus").value("FOR_SALE"));
    }

    @Test
    void successGet() throws Exception {
        //given
        Sports sports = Sports.builder()
                .sportsId(SPORTS_ID)
                .sportsName(SPORTS_NAME)
                .build();

        Stadium stadium = Stadium.builder()
                .stadiumId(STADIUM_ID)
                .sports(sports)
                .stadiumName(STADIUM_NAME)
                .homeTeamName(HOME_TEAM_NAME)
                .build();

        AwayTeam awayTeam = AwayTeam.builder()
                .awayTeamId(AWAY_TEAM_ID)
                .sports(sports)
                .awayTeamName(AWAY_TEAM_NAME)
                .build();

        Ticket ticket = Ticket.builder()
                .ticketId(TICKET_ID)
                .sports(sports)
                .stadium(stadium)
                .awayTeam(awayTeam)
                .quantity(QUANTITY)
                .salePrice(SALE_PRICE)
                .originalPrice(ORIGINAL_PRICE)
                .expirationAt(EXPIRATION_AT)
                .isSuccessive(IS_SUCCESSIVE)
                .seatInfo(SEAT_INFO)
                .imgUrl(IMG_URL)
                .note(NOTE)
                .build();

        given(postService.get(anyLong()))
                .willReturn(Post.builder()
                        .salePostId(SALE_POST_ID)
                        .memberId(MEMBER_ID)
                        .ticket(ticket)
                        .saleStatus(SaleStatus.FOR_SALE)
                        .createdAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(get(BASE_URL+"?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(1L))
                .andExpect(jsonPath("$.memberId").value(1L))
                .andExpect(jsonPath("$.ticket.sportsId").value(1L))
                .andExpect(jsonPath("$.ticket.stadiumId").value(1L))
                .andExpect(jsonPath("$.ticket.awayTeamId").value(1L))
                .andExpect(jsonPath("$.ticket.quantity").value(1))
                .andExpect(jsonPath("$.ticket.salePrice").value(10000))
                .andExpect(jsonPath("$.ticket.originalPrice").value(20000))
                .andExpect(jsonPath("$.ticket.isSuccessive").value(false))
                .andExpect(jsonPath("$.ticket.seatInfo").value("seat info"))
                .andExpect(jsonPath("$.ticket.imgUrl").value("image url"))
                .andExpect(jsonPath("$.ticket.note").value("note"))
                .andExpect(jsonPath("$.saleStatus").value("FOR_SALE"));
    }

    @Test
    void successDelete() throws Exception {
        //given
        given(postService.delete(anyLong()))
                .willReturn(SALE_POST_ID);

        //when
        //then
        mockMvc.perform(delete(BASE_URL+"?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }

}
