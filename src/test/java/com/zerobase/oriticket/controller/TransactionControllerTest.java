package com.zerobase.oriticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.domain.transaction.controller.TransactionController;
import com.zerobase.oriticket.domain.transaction.dto.RegisterTransactionRequest;
import com.zerobase.oriticket.domain.transaction.dto.UpdateStatusToReceivedTransactionRequest;
import com.zerobase.oriticket.domain.transaction.dto.UpdateStatusTransactionRequest;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.service.TransactionService;
import com.zerobase.oriticket.domain.transaction.service.TransactionUpdateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class    TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TransactionUpdateService transactionUpdateService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String BASE_URL = "/transactions";
    private final static Long TRANSACTION_ID = 1L;
    private final static Long POST_ID = 1L;
    private final static Long BUYER_ID = 2L;
    private final static Integer PAY_AMOUNT = 10000;

    @Test
    void successRegister() throws Exception {
        //given
        RegisterTransactionRequest transactionRequest =
                RegisterTransactionRequest.builder()
                        .salePostId(POST_ID)
                        .memberId(BUYER_ID)
                        .build();

        Post salePost = Post.builder()
                .salePostId(POST_ID)
                .build();

        given(transactionService.register(any()))
                .willReturn(Transaction.builder()
                        .transactionId(POST_ID)
                        .salePost(salePost)
                        .memberId(BUYER_ID)
                        .status(TransactionStatus.PENDING)
                        .startedAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.salePostId").value(1L))
                .andExpect(jsonPath("$.memberId").value(2L))
                .andExpect(jsonPath("$.status").value("Pending"))
                .andExpect(jsonPath("$.startedAt").exists());

    }

    @Test
    void successGet() throws Exception {
        //given
        Post salePost = Post.builder()
                .salePostId(POST_ID)
                .build();

        given(transactionService.get(anyLong()))
                .willReturn(Transaction.builder()
                        .transactionId(TRANSACTION_ID)
                        .salePost(salePost)
                        .memberId(BUYER_ID)
                        .status(TransactionStatus.PENDING)
                        .startedAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(get(BASE_URL+"?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.salePostId").value(1L))
                .andExpect(jsonPath("$.memberId").value(2L))
                .andExpect(jsonPath("$.status").value("Pending"))
                .andExpect(jsonPath("$.startedAt").exists());
    }

    @Test
    void successGetAll() throws Exception {
        //given
        Post salePost1 = Post.builder()
                .salePostId(1L)
                .build();
        Post salePost2 = Post.builder()
                .salePostId(2L)
                .build();
        Post salePost3 = Post.builder()
                .salePostId(3L)
                .build();

        Transaction transaction1 = Transaction.builder()
                .transactionId(1L)
                .salePost(salePost1)
                .memberId(BUYER_ID)
                .status(TransactionStatus.PENDING)
                .startedAt(LocalDateTime.now())
                .build();

        Transaction transaction2 = Transaction.builder()
                .transactionId(2L)
                .salePost(salePost2)
                .memberId(BUYER_ID)
                .status(TransactionStatus.PENDING)
                .startedAt(LocalDateTime.now())
                .build();

        Transaction transaction3 = Transaction.builder()
                .transactionId(3L)
                .salePost(salePost3)
                .memberId(BUYER_ID)
                .status(TransactionStatus.PENDING)
                .startedAt(LocalDateTime.now())
                .build();

        List<Transaction> transactionList =
                Arrays.asList(transaction1, transaction2, transaction3);

        Page<Transaction> transactionPage = new PageImpl<>(transactionList);

        given(transactionService.getAll(anyInt(), anyInt()))
                .willReturn(transactionPage);

        //when
        //then
        mockMvc.perform(get(BASE_URL+"/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].transactionId").value(1L))
                .andExpect(jsonPath("$.content[0].salePostId").value(1L))
                .andExpect(jsonPath("$.content[0].memberId").value(2L))
                .andExpect(jsonPath("$.content[0].status").value("Pending"))
                .andExpect(jsonPath("$.content[0].startedAt").exists())
                .andExpect(jsonPath("$.content[1].transactionId").value(2L))
                .andExpect(jsonPath("$.content[1].salePostId").value(2L))
                .andExpect(jsonPath("$.content[1].memberId").value(2L))
                .andExpect(jsonPath("$.content[1].status").value("Pending"))
                .andExpect(jsonPath("$.content[1].startedAt").exists())
                .andExpect(jsonPath("$.content[2].transactionId").value(3L))
                .andExpect(jsonPath("$.content[2].salePostId").value(3L))
                .andExpect(jsonPath("$.content[2].memberId").value(2L))
                .andExpect(jsonPath("$.content[2].status").value("Pending"))
                .andExpect(jsonPath("$.content[2].startedAt").exists());
    }

    @Test
    void successUpdateToReceived() throws Exception {
        //given
        UpdateStatusToReceivedTransactionRequest transactionRequest =
                UpdateStatusToReceivedTransactionRequest.builder()
                        .transactionId(TRANSACTION_ID)
                        .payAmount(PAY_AMOUNT)
                        .build();

        Post salePost = Post.builder()
                .salePostId(POST_ID)
                .build();

        given(transactionUpdateService.updateToReceived(transactionRequest))
                .willReturn(Transaction.builder()
                        .transactionId(TRANSACTION_ID)
                        .salePost(salePost)
                        .memberId(BUYER_ID)
                        .payAmount(PAY_AMOUNT)
                        .status(TransactionStatus.RECEIVED)
                        .receivedAt(LocalDateTime.now())
                        .startedAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(patch(BASE_URL+"/receive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.salePostId").value(1L))
                .andExpect(jsonPath("$.memberId").value(2L))
                .andExpect(jsonPath("$.payAmount").value(10000))
                .andExpect(jsonPath("$.status").value("Received"))
                .andExpect(jsonPath("$.receivedAt").exists())
                .andExpect(jsonPath("$.startedAt").exists());
    }

    @Test
    void successUpdateToCompleted() throws Exception {
        //given
        UpdateStatusTransactionRequest transactionRequest =
                UpdateStatusTransactionRequest.builder()
                        .transactionId(TRANSACTION_ID)
                        .build();

        Post salePost = Post.builder()
                .salePostId(POST_ID)
                .build();

        given(transactionUpdateService.updateToCompleted(transactionRequest))
                .willReturn(Transaction.builder()
                        .transactionId(TRANSACTION_ID)
                        .salePost(salePost)
                        .memberId(BUYER_ID)
                        .status(TransactionStatus.COMPLETED)
                        .startedAt(LocalDateTime.now())
                        .endedAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(patch(BASE_URL+"/completion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.salePostId").value(1L))
                .andExpect(jsonPath("$.memberId").value(2L))
                .andExpect(jsonPath("$.status").value("Completed"))
                .andExpect(jsonPath("$.startedAt").exists())
                .andExpect(jsonPath("$.endedAt").exists());
    }

    @Test
    void successUpdateToCanceled() throws Exception {
        //given
        UpdateStatusTransactionRequest transactionRequest =
                UpdateStatusTransactionRequest.builder()
                        .transactionId(TRANSACTION_ID)
                        .build();

        Post salePost = Post.builder()
                .salePostId(POST_ID)
                .build();

        given(transactionUpdateService.updateToCanceled(transactionRequest))
                .willReturn(Transaction.builder()
                        .transactionId(TRANSACTION_ID)
                        .salePost(salePost)
                        .memberId(BUYER_ID)
                        .status(TransactionStatus.CANCELED)
                        .startedAt(LocalDateTime.now())
                        .endedAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(patch(BASE_URL+"/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.salePostId").value(1L))
                .andExpect(jsonPath("$.memberId").value(2L))
                .andExpect(jsonPath("$.status").value("Canceled"))
                .andExpect(jsonPath("$.startedAt").exists())
                .andExpect(jsonPath("$.endedAt").exists());
    }

    @Test
    void successUpdateToReported() throws Exception {
        //given
        UpdateStatusTransactionRequest transactionRequest =
                UpdateStatusTransactionRequest.builder()
                        .transactionId(TRANSACTION_ID)
                        .build();

        Post salePost = Post.builder()
                .salePostId(POST_ID)
                .build();

        given(transactionUpdateService.updateToReported(transactionRequest))
                .willReturn(Transaction.builder()
                        .transactionId(TRANSACTION_ID)
                        .salePost(salePost)
                        .memberId(BUYER_ID)
                        .status(TransactionStatus.REPORTED)
                        .startedAt(LocalDateTime.now())
                        .endedAt(LocalDateTime.now())
                        .build());

        //when
        //then
        mockMvc.perform(patch(BASE_URL+"/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1L))
                .andExpect(jsonPath("$.salePostId").value(1L))
                .andExpect(jsonPath("$.memberId").value(2L))
                .andExpect(jsonPath("$.status").value("Reported"))
                .andExpect(jsonPath("$.startedAt").exists())
                .andExpect(jsonPath("$.endedAt").exists());
    }
}
