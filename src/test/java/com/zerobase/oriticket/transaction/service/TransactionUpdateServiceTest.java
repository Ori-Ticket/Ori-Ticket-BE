package com.zerobase.oriticket.transaction.service;

import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import com.zerobase.oriticket.domain.chat.repository.ChatRoomRepository;
import com.zerobase.oriticket.domain.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.domain.post.constants.SaleStatus;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.repository.PostRepository;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.domain.transaction.dto.UpdateStatusToReceivedTransactionRequest;
import com.zerobase.oriticket.domain.transaction.dto.UpdateStatusTransactionRequest;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import com.zerobase.oriticket.domain.transaction.service.TransactionUpdateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TransactionUpdateServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private TransactionSearchRepository transactionSearchRepository;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private TransactionUpdateService transactionUpdateService;

    private Post createPost(Long salePostId, SaleStatus status){
        return Post.builder()
                .salePostId(salePostId)
                .saleStatus(status)
                .build();
    }

    private Transaction createTransaction(
            Long transactionId,
            Post salePost,
            Long memberId,
            Integer payAmount,
            TransactionStatus status,
            LocalDateTime receivedAt,
            LocalDateTime endedAt
    ){
        return Transaction.builder()
                .transactionId(transactionId)
                .salePost(salePost)
                .memberId(memberId)
                .payAmount(payAmount)
                .status(status)
                .receivedAt(receivedAt)
                .startedAt(LocalDateTime.now())
                .endedAt(endedAt)
                .build();
    }

    @Test
    @Transactional
    void successUpdateToReceived(){
        //given
        UpdateStatusToReceivedTransactionRequest updateRequest =
                UpdateStatusToReceivedTransactionRequest.builder()
                        .transactionId(11L)
                        .payAmount(10000)
                        .build();
        Post salePost = createPost(1L, SaleStatus.TRADING);
        Transaction transaction = createTransaction(11L, salePost, 2L,
                null, TransactionStatus.PENDING, null, null);

        given(transactionRepository.findById(anyLong()))
                .willReturn(Optional.of(transaction));

        given(transactionRepository.save(any(Transaction.class)))
                .willReturn(transaction);

        ArgumentCaptor<TransactionSearchDocument> transactionDocumentCaptor =
                ArgumentCaptor.forClass(TransactionSearchDocument.class);

        //when
        Transaction fetchedTransaction = transactionUpdateService.updateToReceived(updateRequest);

        //then
        then(transactionSearchRepository).should(times(1)).save(transactionDocumentCaptor.capture());
        TransactionSearchDocument fetchedTransactionDocument = transactionDocumentCaptor.getValue();

        assertThat(fetchedTransaction.getTransactionId()).isEqualTo(11L);
        assertThat(fetchedTransaction.getSalePost()).isEqualTo(salePost);
        assertThat(fetchedTransaction.getMemberId()).isEqualTo(2L);
        assertThat(fetchedTransaction.getPayAmount()).isEqualTo(10000);
        assertThat(fetchedTransaction.getStatus()).isEqualTo(TransactionStatus.RECEIVED);
        assertNotNull(fetchedTransaction.getReceivedAt());
        assertNotNull(fetchedTransaction.getStartedAt());

        assertThat(fetchedTransactionDocument.getTransactionId()).isEqualTo(11L);
        assertThat(fetchedTransactionDocument.getSalePostId()).isEqualTo(1L);
        assertThat(fetchedTransactionDocument.getMemberName()).isEqualTo("buyer name");
        assertThat(fetchedTransactionDocument.getPayAmount()).isEqualTo(10000);
        assertThat(fetchedTransactionDocument.getStatus()).isEqualTo(TransactionStatus.RECEIVED);
        assertNotNull(fetchedTransactionDocument.getReceivedAt());
        assertNotNull(fetchedTransactionDocument.getStartedAt());
    }

    @Test
    @Transactional
    void successUpdateToCompleted(){
        //given
        UpdateStatusTransactionRequest updateRequest =
                UpdateStatusTransactionRequest.builder()
                        .transactionId(12L)
                        .build();
        Post salePost = createPost(2L, SaleStatus.TRADING);
        Transaction transaction = createTransaction(12L, salePost, 3L,
                10000, TransactionStatus.RECEIVED, LocalDateTime.now(), null);

        given(transactionRepository.findById(anyLong()))
                .willReturn(Optional.of(transaction));

        given(chatRoomRepository.findByTransaction_TransactionId(anyLong()))
                .willReturn(Optional.of(ChatRoom.builder().build()));

        given(transactionRepository.save(any(Transaction.class)))
                .willReturn(transaction);

        ArgumentCaptor<TransactionSearchDocument> transactionDocumentCaptor =
                ArgumentCaptor.forClass(TransactionSearchDocument.class);
        ArgumentCaptor<Post> salePostCaptor = ArgumentCaptor.forClass(Post.class);

        //when
        Transaction fetchedTransaction = transactionUpdateService.updateToCompleted(updateRequest);

        //then
        then(transactionSearchRepository).should(times(1)).save(transactionDocumentCaptor.capture());
        then(postRepository).should(times(1)).save(salePostCaptor.capture());
        TransactionSearchDocument fetchedTransactionDocument = transactionDocumentCaptor.getValue();
        Post fetchedSalePost = salePostCaptor.getValue();

        assertThat(fetchedTransaction.getTransactionId()).isEqualTo(12L);
        assertThat(fetchedTransaction.getSalePost()).isEqualTo(salePost);
        assertThat(fetchedTransaction.getMemberId()).isEqualTo(3L);
        assertThat(fetchedTransaction.getPayAmount()).isEqualTo(10000);
        assertThat(fetchedTransaction.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
        assertNotNull(fetchedTransaction.getReceivedAt());
        assertNotNull(fetchedTransaction.getStartedAt());
        assertNotNull(fetchedTransaction.getEndedAt());

        assertThat(fetchedTransactionDocument.getTransactionId()).isEqualTo(12L);
        assertThat(fetchedTransactionDocument.getSalePostId()).isEqualTo(2L);
        assertThat(fetchedTransactionDocument.getMemberName()).isEqualTo("buyer name");
        assertThat(fetchedTransactionDocument.getPayAmount()).isEqualTo(10000);
        assertThat(fetchedTransactionDocument.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
        assertNotNull(fetchedTransactionDocument.getReceivedAt());
        assertNotNull(fetchedTransactionDocument.getStartedAt());
        assertNotNull(fetchedTransactionDocument.getEndedAt());

        assertThat(fetchedSalePost.getSaleStatus()).isEqualTo(SaleStatus.SOLD);
    }

    @Test
    @Transactional
    void successUpdateToCanceled(){
        //given
        UpdateStatusTransactionRequest updateRequest =
                UpdateStatusTransactionRequest.builder()
                        .transactionId(12L)
                        .build();
        Post salePost = createPost(2L, SaleStatus.TRADING);
        Transaction transaction = createTransaction(12L, salePost, 3L,
                10000, TransactionStatus.RECEIVED, LocalDateTime.now(), null);

        given(transactionRepository.findById(anyLong()))
                .willReturn(Optional.of(transaction));

        given(chatRoomRepository.findByTransaction_TransactionId(anyLong()))
                .willReturn(Optional.of(ChatRoom.builder().build()));

        given(transactionRepository.save(any(Transaction.class)))
                .willReturn(transaction);

        ArgumentCaptor<TransactionSearchDocument> transactionDocumentCaptor =
                ArgumentCaptor.forClass(TransactionSearchDocument.class);
        ArgumentCaptor<Post> salePostCaptor = ArgumentCaptor.forClass(Post.class);

        //when
        Transaction fetchedTransaction = transactionUpdateService.updateToCanceled(updateRequest);

        //then
        then(transactionSearchRepository).should(times(1)).save(transactionDocumentCaptor.capture());
        then(postRepository).should(times(1)).save(salePostCaptor.capture());
        TransactionSearchDocument fetchedTransactionDocument = transactionDocumentCaptor.getValue();
        Post fetchedSalePost = salePostCaptor.getValue();

        assertThat(fetchedTransaction.getTransactionId()).isEqualTo(12L);
        assertThat(fetchedTransaction.getSalePost()).isEqualTo(salePost);
        assertThat(fetchedTransaction.getMemberId()).isEqualTo(3L);
        assertThat(fetchedTransaction.getPayAmount()).isEqualTo(10000);
        assertThat(fetchedTransaction.getStatus()).isEqualTo(TransactionStatus.CANCELED);
        assertNotNull(fetchedTransaction.getReceivedAt());
        assertNotNull(fetchedTransaction.getStartedAt());
        assertNotNull(fetchedTransaction.getEndedAt());

        assertThat(fetchedTransactionDocument.getTransactionId()).isEqualTo(12L);
        assertThat(fetchedTransactionDocument.getSalePostId()).isEqualTo(2L);
        assertThat(fetchedTransactionDocument.getMemberName()).isEqualTo("buyer name");
        assertThat(fetchedTransactionDocument.getPayAmount()).isEqualTo(10000);
        assertThat(fetchedTransactionDocument.getStatus()).isEqualTo(TransactionStatus.CANCELED);
        assertNotNull(fetchedTransactionDocument.getReceivedAt());
        assertNotNull(fetchedTransactionDocument.getStartedAt());
        assertNotNull(fetchedTransactionDocument.getEndedAt());

        assertThat(fetchedSalePost.getSaleStatus()).isEqualTo(SaleStatus.FOR_SALE);
    }

    @Test
    @Transactional
    void successUpdateToReported(){
        //given
        UpdateStatusTransactionRequest updateRequest =
                UpdateStatusTransactionRequest.builder()
                        .transactionId(12L)
                        .build();
        Post salePost = createPost(2L, SaleStatus.TRADING);
        Transaction transaction = createTransaction(12L, salePost, 3L,
                10000, TransactionStatus.RECEIVED, LocalDateTime.now(), null);

        given(transactionRepository.findById(anyLong()))
                .willReturn(Optional.of(transaction));

        given(chatRoomRepository.findByTransaction_TransactionId(anyLong()))
                .willReturn(Optional.of(ChatRoom.builder().build()));

        given(transactionRepository.save(any(Transaction.class)))
                .willReturn(transaction);

        ArgumentCaptor<TransactionSearchDocument> transactionDocumentCaptor =
                ArgumentCaptor.forClass(TransactionSearchDocument.class);
        ArgumentCaptor<Post> salePostCaptor = ArgumentCaptor.forClass(Post.class);

        //when
        Transaction fetchedTransaction = transactionUpdateService.updateToReported(updateRequest);

        //then
        then(transactionSearchRepository).should(times(1)).save(transactionDocumentCaptor.capture());
        then(postRepository).should(times(1)).save(salePostCaptor.capture());
        TransactionSearchDocument fetchedTransactionDocument = transactionDocumentCaptor.getValue();
        Post fetchedSalePost = salePostCaptor.getValue();

        assertThat(fetchedTransaction.getTransactionId()).isEqualTo(12L);
        assertThat(fetchedTransaction.getSalePost()).isEqualTo(salePost);
        assertThat(fetchedTransaction.getMemberId()).isEqualTo(3L);
        assertThat(fetchedTransaction.getPayAmount()).isEqualTo(10000);
        assertThat(fetchedTransaction.getStatus()).isEqualTo(TransactionStatus.REPORTED);
        assertNotNull(fetchedTransaction.getReceivedAt());
        assertNotNull(fetchedTransaction.getStartedAt());
        assertNotNull(fetchedTransaction.getEndedAt());

        assertThat(fetchedTransactionDocument.getTransactionId()).isEqualTo(12L);
        assertThat(fetchedTransactionDocument.getSalePostId()).isEqualTo(2L);
        assertThat(fetchedTransactionDocument.getMemberName()).isEqualTo("buyer name");
        assertThat(fetchedTransactionDocument.getPayAmount()).isEqualTo(10000);
        assertThat(fetchedTransactionDocument.getStatus()).isEqualTo(TransactionStatus.REPORTED);
        assertNotNull(fetchedTransactionDocument.getReceivedAt());
        assertNotNull(fetchedTransactionDocument.getStartedAt());
        assertNotNull(fetchedTransactionDocument.getEndedAt());

        assertThat(fetchedSalePost.getSaleStatus()).isEqualTo(SaleStatus.REPORTED);
    }

}
