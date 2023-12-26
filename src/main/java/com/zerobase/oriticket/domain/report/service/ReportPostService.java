package com.zerobase.oriticket.domain.report.service;

import com.zerobase.oriticket.domain.elasticsearch.report.entity.ReportPostSearchDocument;
import com.zerobase.oriticket.domain.elasticsearch.report.repository.ReportPostSearchRepository;
import com.zerobase.oriticket.domain.post.entity.Post;
import com.zerobase.oriticket.domain.post.repository.PostRepository;
import com.zerobase.oriticket.domain.report.constants.ReportPostType;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportPostService {

    private final ReportPostRepository reportPostRepository;
    private final ReportPostSearchRepository reportPostSearchRepository;
    private final PostRepository postRepository;

    @Transactional
    public ReportPost register(Long salePostId, RegisterReportPostRequest request) {
        Post salePost = postRepository.findById(salePostId)
                .orElseThrow(() -> new CustomException(PostExceptionStatus.SALE_POST_NOT_FOUND.getCode(),
                        PostExceptionStatus.SALE_POST_NOT_FOUND.getMessage()));

        validateCanRegister(salePostId, request);

        ReportPost reportPost = reportPostRepository.save(request.toEntity(salePost));
        reportPostSearchRepository.save(ReportPostSearchDocument.fromEntity(reportPost));

        return reportPost;
    }

    @Transactional
    public ReportPost updateToReacted(Long reportPostId, UpdateReportRequest request) {
        ReportPost reportPost = reportPostRepository.findById(reportPostId)
                .orElseThrow(() -> new CustomException(ReportExceptionStatus.REPORT_POST_NOT_FOUND.getCode(),
                        ReportExceptionStatus.REPORT_POST_NOT_FOUND.getMessage()));

        reportPost.setStatus(ReportReactStatus.REACTED);
        reportPost.setReactedAt(LocalDateTime.now());
        reportPost.setNote(request.getNote());
        reportPostSearchRepository.save(ReportPostSearchDocument.fromEntity(reportPost));

        return reportPostRepository.save(reportPost);
    }

    private void validateCanRegister(Long salePostId, RegisterReportPostRequest request){
        if(reportPostRepository.existsBySalePostIdAndMemberIdAndReason(
                salePostId, request.getMemberId(), ReportPostType.valueOf(request.getReason().toUpperCase()))
        ){
            throw new CustomException(ReportExceptionStatus.CANNOT_REGISTER_REPORT_POST.getCode(),
                    ReportExceptionStatus.CANNOT_REGISTER_REPORT_POST.getMessage());
        }
    }
}
