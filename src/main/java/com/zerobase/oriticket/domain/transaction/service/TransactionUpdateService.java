package com.zerobase.oriticket.domain.transaction.service;

import com.zerobase.oriticket.domain.chat.entity.ChatRoom;
import com.zerobase.oriticket.domain.chat.repository.ChatRoomRepository;
import com.zerobase.oriticket.domain.elasticsearch.transaction.entity.TransactionSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.transaction.repository.TransactionSearchRepository;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.repository.PostRepository;
import com.zerobase.oriticket.domain.transaction.constants.TransactionStatus;
import com.zerobase.oriticket.domain.transaction.dto.UpdateStatusToReceivedTransactionRequest;
import com.zerobase.oriticket.domain.transaction.dto.UpdateStatusTransactionRequest;
import com.zerobase.oriticket.domain.transaction.entity.Transaction;
import com.zerobase.oriticket.domain.transaction.repository.TransactionRepository;
import com.zerobase.oriticket.global.exception.impl.chat.ChatRoomNotFoundException;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyTransactionStateOfCanceledException;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyTransactionStateOfCompletedException;
import com.zerobase.oriticket.global.exception.impl.transaction.CannotModifyTransactionStateOfReportedException;
import com.zerobase.oriticket.global.exception.impl.transaction.TransactionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionUpdateService {

    private final TransactionRepository transactionRepository;
    private final PostRepository postRepository;
    private final TransactionSearchRepository transactionSearchRepository;
    private final ChatRoomRepository chatRoomRepository;

    public Transaction updateToReceived(UpdateStatusToReceivedTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFoundException::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatusToReceived(request.getPayAmount());
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        chatRoomRepository.save(ChatRoom.createChatRoom(transaction, transaction.getSalePost()));

        return transactionRepository.save(transaction);
    }

    public Transaction updateToCompleted(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFoundException::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatusToCompleted();
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        Post salePost = transaction.getSalePost();
        salePost.updateToSold();
        postRepository.save(salePost);

        endChatRoom(transaction);

        return transactionRepository.save(transaction);
    }

    public Transaction updateToCanceled(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFoundException::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatusToCanceled();
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        Post salePost = transaction.getSalePost();
        salePost.updateToForeSale();
        postRepository.save(salePost);

        endChatRoom(transaction);

        return transactionRepository.save(transaction);
    }

    public Transaction updateToReported(UpdateStatusTransactionRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(TransactionNotFoundException::new);

        validateCanUpdateStatus(transaction.getStatus());

        transaction.updateStatusToReported();
        transactionSearchRepository.save(TransactionSearchDocument.fromEntity(transaction));

        Post salePost = transaction.getSalePost();
        salePost.updateToReported();
        postRepository.save(salePost);

        endChatRoom(transaction);

        return transactionRepository.save(transaction);
    }

    public void validateCanUpdateStatus(TransactionStatus status){
        if(status == TransactionStatus.CANCELED)
            throw new CannotModifyTransactionStateOfCanceledException();
        if(status == TransactionStatus.COMPLETED)
            throw new CannotModifyTransactionStateOfCompletedException();
        if(status == TransactionStatus.REPORTED)
            throw new CannotModifyTransactionStateOfReportedException();
    }

    public void endChatRoom(Transaction transaction){
        ChatRoom chatRoom = chatRoomRepository.findByTransaction(transaction)
                .orElseThrow(ChatRoomNotFoundException::new);

        chatRoom.endChatRoom();
        chatRoomRepository.save(chatRoom);
    }
}
