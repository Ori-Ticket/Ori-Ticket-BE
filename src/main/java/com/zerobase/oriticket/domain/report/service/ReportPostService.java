package com.zerobase.oriticket.domain.report.service;

import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.repository.PostRepository;
import com.zerobase.oriticket.domain.report.constants.ReportReactStatus;
import com.zerobase.oriticket.domain.report.dto.RegisterReportPostRequest;
import com.zerobase.oriticket.domain.report.dto.UpdateReportRequest;
import com.zerobase.oriticket.domain.report.entity.ReportPost;
import com.zerobase.oriticket.domain.report.repository.ReportPostRepository;
import com.zerobase.oriticket.global.constants.PostExceptionStatus;
import com.zerobase.oriticket.global.constants.ReportExceptionStatus;
import com.zerobase.oriticket.global.exception.impl.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportPostService {

    private final ReportPostRepository reportPostRepository;
    private final PostRepository postRepository;

    public ReportPost register(Long salePostId, RegisterReportPostRequest request) {
        Post salePost = postRepository.findById(salePostId)
                .orElseThrow(() -> new CustomException(PostExceptionStatus.SALE_POST_NOT_FOUND.getCode(),
                        PostExceptionStatus.SALE_POST_NOT_FOUND.getMessage()));

        return reportPostRepository.save(request.toEntity(salePost));
    }

    public ReportPost updateToReacted(Long reportPostId, UpdateReportRequest request) {
        ReportPost reportPost = reportPostRepository.findById(reportPostId)
                .orElseThrow(() -> new CustomException(ReportExceptionStatus.REPORT_POST_NOT_FOUND.getCode(),
                        ReportExceptionStatus.REPORT_POST_NOT_FOUND.getMessage()));

        reportPost.setStatus(ReportReactStatus.REACTED);
        reportPost.setReactedAt(LocalDateTime.now());
        reportPost.setNote(request.getNote());

        return reportPostRepository.save(reportPost);
    }
}
