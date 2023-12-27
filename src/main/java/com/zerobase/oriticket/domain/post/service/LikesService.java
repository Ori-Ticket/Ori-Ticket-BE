package com.zerobase.oriticket.domain.post.service;

import com.zerobase.oriticket.domain.post.constants.SaleStatus;
import com.zerobase.oriticket.domain.post.dto.RegisterLikesRequest;
import com.zerobase.oriticket.domain.post.entity.Likes;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.repository.LikesRepository;
import com.zerobase.oriticket.domain.post.repository.PostRepository;
import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.exception.impl.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;

    public Likes register(Long salePostId, RegisterLikesRequest request) {
        Post salePost = postRepository.findById(salePostId)
                .orElseThrow(() -> new CustomException(PostExceptionStatus.SALE_POST_NOT_FOUND.getCode(),
                        PostExceptionStatus.SALE_POST_NOT_FOUND.getMessage()));

        validateCanRegister(request.getMemberId(), salePost);

        return likesRepository.save(request.toEntity(salePost));
    }

    public Long delete(Long salePostId, Long memberId) {
        Likes likes = likesRepository.findBySalePost_SalePostIdAndMemberId(salePostId, memberId)
                .orElseThrow(() -> new CustomException(PostExceptionStatus.LIKES_NOT_FOUND.getCode(),
                        PostExceptionStatus.LIKES_NOT_FOUND.getMessage()));

        likesRepository.delete(likes);

        return likes.getLikesId();
    }

    public List<Likes> get(Long memberId) {
        List<SaleStatus> saleStatusList = List.of(SaleStatus.REPORTED);
        return likesRepository.findAllByMemberIdAndSalePost_SaleStatusNotIn(memberId, saleStatusList);
    }

    private void validateCanRegister(Long memberId, Post salePost){
        if(likesRepository.existsByMemberIdAndSalePost(memberId, salePost)){
            throw new CustomException(PostExceptionStatus.CANNOT_REGISTER_LIKES_EXIST_LIKES.getCode(),
                    PostExceptionStatus.CANNOT_REGISTER_LIKES_EXIST_LIKES.getMessage());
        }
    }
}
