package com.example.sharediary.friend.service;

import com.example.sharediary.friend.domain.Friend;
import com.example.sharediary.friend.domain.FriendStatus;
import com.example.sharediary.friend.dto.reqeust.FriendRequestDto;
import com.example.sharediary.friend.dto.response.FriendResponseDto;
import com.example.sharediary.friend.repository.FriendRepository;
import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public FriendService(FriendRepository friendRepository, MemberRepository memberRepository) {
        this.friendRepository = friendRepository;
        this.memberRepository = memberRepository;
    }

    // 친구 추가 요청
    public void senderFriendRequest(Long senderId, Long receiverId, String title) {
        Member sender = memberRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("sender 찾을 수 없음"));
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("receiver 찾을 수 없음"));

        Friend friend = new Friend(); // 엔티티에 protected 넣으면 오류 뚬
        friend.setSender(sender);
        friend.setReceiver(receiver);
        friend.setTitle(title);
        friend.setStatus(FriendStatus.PENDING);

        friendRepository.save(friend);
    }

    // 친구 요청 수락
    public void acceptFriendRequest(Long requestId) {
        Friend friend = friendRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("친구 요청 찾을 수 없음"));
        if(friend.getStatus() == FriendStatus.PENDING) {
            friend.setStatus(FriendStatus.ACCEPTED);
            friendRepository.save(friend);
        } else {
            throw new RuntimeException("친구 요청 없음");
        }
    }

    // 친구 목록 조회
    public List<FriendResponseDto> getAcceptFriends(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Sender 찾을 수 없음"));
        List<Friend> friends = friendRepository.findBySenderAndStatusOrReceiverAndStatus(member, FriendStatus.ACCEPTED, member, FriendStatus.ACCEPTED);
        return friends.stream()
                .map(friend -> new FriendResponseDto(
                        friend.getSender().getMemberId(),
                        friend.getReceiver().getMemberId(),
                        friend.getStatus().name(),
                        friend.getTitle()))
                .collect(Collectors.toList());

    }

    // 친구 요청 대기 목록 조회
    public List<FriendResponseDto> getPendingRequests(Long receiverId) {
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver 찾을 수 없음"));
        List<Friend> friends = friendRepository.findByReceiverAndStatus(receiver, FriendStatus.PENDING);
        return friends.stream()
                .map(friend -> new FriendResponseDto(
                        friend.getSender().getMemberId(),
                        friend.getReceiver().getMemberId(),
                        friend.getStatus().name(),
                        friend.getTitle()))
                .collect(Collectors.toList());
    }

}
