package com.example.sharediary.comment.controller;

import com.example.sharediary.comment.dto.CommentRequestDto;
import com.example.sharediary.comment.dto.CommentResponseDto;
import com.example.sharediary.comment.service.CommentService;
import com.example.sharediary.diary.dto.PagedResponse;
import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "댓글 API", description = "Swagger 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/v1/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create/{diaryId}")
    public ResponseEntity<String> createComment(@PathVariable("diaryId") Long diaryId, @RequestBody CommentRequestDto commentRequestDto, LoginMemberResponseDto member) {
        Long senderId = member.id();
        commentService.createComment(commentRequestDto, diaryId, senderId);
        return ResponseEntity.ok("댓글이 성공적으로 생성되었습니다. 일기 ID: " + diaryId);
    }

    @GetMapping("/read/{diaryId}")
    public ResponseEntity<PagedResponse<CommentResponseDto>> getCommentLists(@PathVariable("diaryId") Long diaryId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "sort", defaultValue = "commentId, asc") String sort
    ) {
        Pageable pageable;
        if (sort.isEmpty()) {
            pageable = PageRequest.of(page, size, Sort.by("commentId").ascending());
        } else  {
            String[] sortParams = sort.split(",");
            Sort sortOrder = Sort.by(Sort.Direction.fromString((sortParams[1]).trim()), sortParams[0].trim());
            pageable = PageRequest.of(page, size, sortOrder);
        }
        PagedResponse<CommentResponseDto> commentResponseDtoList = commentService.getCommentLists(diaryId, pageable);
        return ResponseEntity.ok(commentResponseDtoList);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.updateComment(commentId, commentRequestDto);
        return ResponseEntity.ok(commentResponseDto);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>("댓글이 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }
}
