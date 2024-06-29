package com.example.sharediary.heart.controller;

import com.example.sharediary.heart.dto.request.HeartRequestDto;
import com.example.sharediary.heart.service.HeartService;
import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apis/v1/hearts")
public class HeartController {
    private final HeartService heartService;

    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    @PostMapping
    public ResponseEntity<String> insertHeart(LoginMemberResponseDto loginMemberResponseDto, @RequestBody HeartRequestDto heartRequestDto) {
        Long senderId = loginMemberResponseDto.id();
        heartService.insertHeart(senderId, heartRequestDto.getDiaryId());
        return new ResponseEntity<>("좋아요 등록", HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteHeart(LoginMemberResponseDto loginMemberResponseDto, @RequestBody HeartRequestDto heartRequestDto) {
        Long senderId = loginMemberResponseDto.id();
        heartService.deleteHeart(senderId, heartRequestDto.getDiaryId());
        return new ResponseEntity<>("좋아요 삭제", HttpStatus.OK);
    }
}
