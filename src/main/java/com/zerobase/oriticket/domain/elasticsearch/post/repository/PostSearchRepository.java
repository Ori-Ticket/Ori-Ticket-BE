package com.zerobase.oriticket.domain.elasticsearch.post.repository;

import com.zerobase.oriticket.domain.elasticsearch.post.entity.PostSearchDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostSearchRepository extends ElasticsearchRepository<PostSearchDocument, Long> {

    Page<PostSearchDocument> findAll(Pageable pageable);

    Page<PostSearchDocument> findAllByHomeTeamNameContainingOrAwayTeamNameContaining(String homeTeamName, String awayTeamName, Pageable pageable);

    Page<PostSearchDocument> findAllBySportsNameContainingOrStadiumNameContainingOrHomeTeamNameContainingOrAwayTeamNameContaining(String sportsName, String stadiumName, String homeTeamName, String awayTeamName, Pageable pageable);


}
