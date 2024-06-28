package com.example.sharediary.friend.controller;

import com.example.sharediary.friend.domain.Friend;
import com.example.sharediary.friend.dto.response.FriendResponseDto;
import com.example.sharediary.friend.service.FriendService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis/v1/friends")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    // 친구 요청
    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam String title) {
        friendService.senderFriendRequest(senderId, receiverId, title);
        return ResponseEntity.ok("친구 요청 발송");
    }

    // 친구 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Long friendId) {
        friendService.acceptFriendRequest(friendId);
        return ResponseEntity.ok("친구 요청 수락 성공, 친구 맺음!");
    }

    // 친구 목록 조회
    @GetMapping("/accept")
    public ResponseEntity<List<FriendResponseDto>> getAcceptFriend(@RequestParam Long memberId) {
        List<FriendResponseDto>acceptedFriends = friendService.getAcceptFriends(memberId);
        return ResponseEntity.ok(acceptedFriends);
    }

    // 친구 요청 대기 목록 조회
    @GetMapping("/request")
    public ResponseEntity<List<FriendResponseDto>> getPendingRequests(@RequestParam Long receiverId) {
        List<FriendResponseDto> pendingRequests = friendService.getPendingRequests(receiverId);
        return ResponseEntity.ok(pendingRequests);
    }
}
