package com.example.sharediary.comment.service;

import com.example.sharediary.comment.domain.Comment;
import com.example.sharediary.comment.dto.CommentRequestDto;
import com.example.sharediary.comment.dto.CommentResponseDto;
import com.example.sharediary.comment.repository.CommentRepository;
import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.diary.dto.DiaryResponseDto;
import com.example.sharediary.diary.repository.DiaryRepository;
import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createComment(CommentRequestDto commentRequestDto, Long diaryId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 일기장입니다."));

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .member(member)
                .diary(diary)
                .build();

        commentRepository.save(comment);
        return comment.getCommentId();
    }


    @Transactional
    public List<CommentResponseDto> getCommentLists(Long diaryId) {
        return commentRepository.findAll().stream()
                .filter(comment -> {
                    Diary diary = comment.getDiary();
                    return diary != null && diary.getDiaryId() != null && diary.getDiaryId().equals(diaryId);
                })
                .map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getCommentId())
                        .content(comment.getContent())
                        .memberName(comment.getMember().getMemberName())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.update(commentRequestDto);
        return CommentResponseDto.of(comment);
    }

    @Transactional
    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 댓글입니다."));
        commentRepository.deleteById(commentId);
    }
}
