package com.zerobase.oriticket.controller;

import com.zerobase.oriticket.domain.elasticsearch.post.contorller.PostSearchController;
import com.zerobase.oriticket.domain.elasticsearch.post.entity.PostSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.post.service.PostSearchService;
import com.zerobase.oriticket.domain.post.constants.SaleStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostSearchController.class)
public class PostSearchControllerTest {

    @MockBean
    private PostSearchService postSearchService;

    @Autowired
    private MockMvc mockMvc;

    private final static String BASE_URL = "/posts";

    @Test
    void successSearch() throws Exception {
        //given
        PostSearchDocument postSearchDocument1 =
                PostSearchDocument.builder()
                        .salePostId(1L)
                        .memberName("member1")
                        .sportsName("야구")
                        .stadiumName("잠실 경기장")
                        .homeTeamName("두산")
                        .awayTeamName("기아")
                        .quantity(1)
                        .salePrice(10000)
                        .originalPrice(15000)
                        .expirationAt(LocalDateTime.now().plusDays(1))
                        .isSuccessive(false)
                        .seatInfo("A열 21")
                        .imgUrl("image url1")
                        .note("note1")
                        .saleStatus(SaleStatus.FOR_SALE)
                        .createdAt(LocalDateTime.now())
                        .build();

        PostSearchDocument postSearchDocument2 =
                PostSearchDocument.builder()
                        .salePostId(2L)
                        .memberName("member2")
                        .sportsName("야구")
                        .stadiumName("잠실 경기장")
                        .homeTeamName("두산")
                        .awayTeamName("한화")
                        .quantity(1)
                        .salePrice(15000)
                        .originalPrice(20000)
                        .expirationAt(LocalDateTime.now().plusDays(2))
                        .isSuccessive(false)
                        .seatInfo("C열 21")
                        .imgUrl("image url2")
                        .note("note2")
                        .saleStatus(SaleStatus.FOR_SALE)
                        .createdAt(LocalDateTime.now())
                        .build();

        List<PostSearchDocument> postSearchDocumentList =
                Arrays.asList(postSearchDocument1, postSearchDocument2);

        Page<PostSearchDocument> postSearchDocuments =
                new PageImpl<>(postSearchDocumentList);

        given(postSearchService.search(anyString(), anyInt(), anyInt()))
                .willReturn(postSearchDocuments);

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/search?value="))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].salePostId").value(1L))
                .andExpect(jsonPath("$.content[0].memberName").value("member1"))
                .andExpect(jsonPath("$.content[0].sportsName").value("야구"))
                .andExpect(jsonPath("$.content[0].stadiumName").value("잠실 경기장"))
                .andExpect(jsonPath("$.content[0].homeTeamName").value("두산"))
                .andExpect(jsonPath("$.content[0].awayTeamName").value("기아"))
                .andExpect(jsonPath("$.content[0].quantity").value(1))
                .andExpect(jsonPath("$.content[0].salePrice").value(10000))
                .andExpect(jsonPath("$.content[0].originalPrice").value(15000))
                .andExpect(jsonPath("$.content[0].expirationAt").exists())
                .andExpect(jsonPath("$.content[0].isSuccessive").value(false))
                .andExpect(jsonPath("$.content[0].seatInfo").value("A열 21"))
                .andExpect(jsonPath("$.content[0].imgUrl").value("image url1"))
                .andExpect(jsonPath("$.content[0].note").value("note1"))
                .andExpect(jsonPath("$.content[0].saleStatus").value("FOR_SALE"))
                .andExpect(jsonPath("$.content[0].createdAt").exists())
                .andExpect(jsonPath("$.content[1].salePostId").value(2L))
                .andExpect(jsonPath("$.content[1].memberName").value("member2"))
                .andExpect(jsonPath("$.content[1].sportsName").value("야구"))
                .andExpect(jsonPath("$.content[1].stadiumName").value("잠실 경기장"))
                .andExpect(jsonPath("$.content[1].homeTeamName").value("두산"))
                .andExpect(jsonPath("$.content[1].awayTeamName").value("한화"))
                .andExpect(jsonPath("$.content[1].quantity").value(1))
                .andExpect(jsonPath("$.content[1].salePrice").value(15000))
                .andExpect(jsonPath("$.content[1].originalPrice").value(20000))
                .andExpect(jsonPath("$.content[1].expirationAt").exists())
                .andExpect(jsonPath("$.content[1].isSuccessive").value(false))
                .andExpect(jsonPath("$.content[1].seatInfo").value("C열 21"))
                .andExpect(jsonPath("$.content[1].imgUrl").value("image url2"))
                .andExpect(jsonPath("$.content[1].note").value("note2"))
                .andExpect(jsonPath("$.content[1].saleStatus").value("FOR_SALE"))
                .andExpect(jsonPath("$.content[1].createdAt").exists());

    }

}
