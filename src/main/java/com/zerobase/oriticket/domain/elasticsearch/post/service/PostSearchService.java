package com.zerobase.oriticket.domain.elasticsearch.post.service;

import com.zerobase.oriticket.domain.elasticsearch.post.entity.PostSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.post.repository.PostSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostSearchRepository postSearchRepository;

    public Page<PostSearchDocument> search(String value, int page, int size) {

        if(value == null){
            return searchAll(page, size);
        }

        return searchByCategoryName(value, page, size);
    }

    public Page<PostSearchDocument> searchAll(int page, int size){

        Pageable pageable = PageRequest.of(page-1, size);

        Page<PostSearchDocument> postSearchDocuments = postSearchRepository.findAll(pageable);

        return postSearchDocuments;
    }

    public Page<PostSearchDocument> searchByCategoryName(String value, int page, int size){

        Pageable pageable = PageRequest.of(page-1, size);

        Page<PostSearchDocument> postSearchDocuments =
                postSearchRepository.findAllBySportsNameContainingOrStadiumNameContainingOrHomeTeamNameContainingOrAwayTeamNameContaining
                        (value, value, value, value, pageable);

        return postSearchDocuments;
    }

}
