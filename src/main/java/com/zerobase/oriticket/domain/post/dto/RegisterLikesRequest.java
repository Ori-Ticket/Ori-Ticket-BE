package com.zerobase.oriticket.domain.post.dto;

import com.zerobase.oriticket.domain.post.entity.Likes;
import com.zerobase.oriticket.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterLikesRequest {

    private Long memberId;

    public Likes toEntity(Post salePost){
        return Likes.builder()
                .memberId(this.memberId)
                .salePost(salePost)
                .build();
    }

}
