package com.zerobase.oriticket.transaction.service;

import com.zerobase.oriticket.domain.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.domain.post.constants.SaleStatus;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.repository.PostRepository;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.domain.transaction.dto.RegisterTransactionRequest;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import com.zerobase.oriticket.domain.transaction.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionSearchRepository transactionSearchRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Post createPost(Long salePostId){
        return Post.builder()
                .salePostId(salePostId)
                .saleStatus(SaleStatus.FOR_SALE)
                .build();
    }

    private Transaction createTransaction(
            Long transactionId,
            Post salePost,
            Long memberId
    ){
        return Transaction.builder()
                .transactionId(transactionId)
                .salePost(salePost)
                .memberId(memberId)
                .status(TransactionStatus.PENDING)
                .startedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @Transactional
    @DisplayName("Transaction 등록 성공")
    void successRegister(){
        //given
        RegisterTransactionRequest registerRequest =
                RegisterTransactionRequest.builder()
                        .salePostId(1L)
                        .memberId(2L)
                        .build();
        Post salePost = createPost(1L);
        Transaction transaction = createTransaction(10L, salePost, 2L);

        given(postRepository.findById(anyLong()))
                .willReturn(Optional.of(salePost));
        given(transactionRepository.validateCanRegisterByStatus(any(Post.class)))
                .willReturn(true);
        given(transactionRepository.save(any(Transaction.class)))
                .willReturn(transaction);

        ArgumentCaptor<TransactionSearchDocument> transactionDocumentCaptor =
                ArgumentCaptor.forClass(TransactionSearchDocument.class);

        ArgumentCaptor<Post> salePostCaptor = ArgumentCaptor.forClass(Post.class);

        //when
        Transaction fetchedTransaction = transactionService.register(registerRequest);

        //then
        then(transactionSearchRepository).should(times(1)).save(transactionDocumentCaptor.capture());
        then(postRepository).should(times(1)).save(salePostCaptor.capture());
        TransactionSearchDocument fetchedTransactionDocument = transactionDocumentCaptor.getValue();
        Post fetchedSalePost = salePostCaptor.getValue();

        assertThat(fetchedTransaction.getTransactionId()).isEqualTo(10L);
        assertThat(fetchedTransaction.getSalePost()).isEqualTo(salePost);
        assertThat(fetchedTransaction.getMemberId()).isEqualTo(2L);
        assertThat(fetchedTransaction.getStatus()).isEqualTo(TransactionStatus.PENDING);
        assertNotNull(fetchedTransaction.getStartedAt());

        assertThat(fetchedTransactionDocument.getTransactionId()).isEqualTo(10L);
        assertThat(fetchedTransactionDocument.getSalePostId()).isEqualTo(1L);
        assertThat(fetchedTransactionDocument.getMemberName()).isEqualTo("buyer name");
        assertThat(fetchedTransactionDocument.getStatus()).isEqualTo(TransactionStatus.PENDING);
        assertNotNull(fetchedTransactionDocument.getStartedAt());

        assertThat(fetchedSalePost.getSaleStatus()).isEqualTo(SaleStatus.TRADING);
    }

    @Test
    @DisplayName("Transaction 조회 성공")
    void successGet(){
        //given
        Post salePost = createPost(10L);
        Transaction transaction = createTransaction(15L, salePost, 3L);

        given(transactionRepository.findById(anyLong()))
                .willReturn(Optional.of(transaction));

        //when
        Transaction fetchedTransaction = transactionService.get(15L);

        //then
        assertThat(fetchedTransaction.getTransactionId()).isEqualTo(15L);
        assertThat(fetchedTransaction.getSalePost()).isEqualTo(salePost);
        assertThat(fetchedTransaction.getMemberId()).isEqualTo(3L);
        assertThat(fetchedTransaction.getStatus()).isEqualTo(TransactionStatus.PENDING);
        assertNotNull(fetchedTransaction.getStartedAt());

    }

    @Test
    @DisplayName("모든 Transaction 조회 성공")
    void successGetAll(){
        //given
        Post salePost1 = createPost(10L);
        Post salePost2 = createPost(11L);
        Transaction transaction1 = createTransaction(15L, salePost1, 3L);
        Transaction transaction2 = createTransaction(16L, salePost2, 4L);

        List<Transaction> transactionList = Arrays.asList(transaction1, transaction2);

        given(transactionRepository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(transactionList));

        //when
        Page<Transaction> fetchedTransactions = transactionService.getAll(1, 10);

        //then
        assertThat(fetchedTransactions.getContent().get(0).getTransactionId()).isEqualTo(15L);
        assertThat(fetchedTransactions.getContent().get(0).getSalePost()).isEqualTo(salePost1);
        assertThat(fetchedTransactions.getContent().get(0).getMemberId()).isEqualTo(3L);
        assertThat(fetchedTransactions.getContent().get(0).getStatus()).isEqualTo(TransactionStatus.PENDING);
        assertNotNull(fetchedTransactions.getContent().get(0).getStartedAt());
        assertThat(fetchedTransactions.getContent().get(1).getTransactionId()).isEqualTo(16L);
        assertThat(fetchedTransactions.getContent().get(1).getSalePost()).isEqualTo(salePost2);
        assertThat(fetchedTransactions.getContent().get(1).getMemberId()).isEqualTo(4L);
        assertThat(fetchedTransactions.getContent().get(1).getStatus()).isEqualTo(TransactionStatus.PENDING);
        assertNotNull(fetchedTransactions.getContent().get(1).getStartedAt());

    }


}
