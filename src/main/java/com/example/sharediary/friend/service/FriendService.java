package com.example.sharediary.friend.service;

import com.example.sharediary.friend.domain.Friend;
import com.example.sharediary.friend.domain.FriendStatus;
import com.example.sharediary.friend.dto.reqeust.FriendRequestDto;
import com.example.sharediary.friend.dto.response.FriendResponseDto;
import com.example.sharediary.friend.repository.FriendRepository;
import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import com.example.sharediary.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void senderFriendRequest(FriendRequestDto friendRequestDto, LoginMemberResponseDto loginMember) {
        Member sender = memberRepository.findById(loginMember.id())
                .orElseThrow(() -> new IllegalArgumentException("sender 찾을 수 없음"));
        Member receiver = memberRepository.findBySenderName(friendRequestDto.getFriendName())
                .orElseThrow(() -> new IllegalArgumentException("receiver 찾을 수 없음"));

        Friend friend = new Friend(); // 엔티티에 protected 넣으면 오류 뚬
        friend.setSender(sender);
        friend.setReceiver(receiver);
        friend.setTitle(friendRequestDto.getTitle());
        friend.setStatus(FriendStatus.ACCEPTED);

        friendRepository.save(friend);
    }

    // 친구 요청 수락
    @Transactional
    public void acceptFriendRequest(FriendRequestDto friendRequestDto) {
        Member receiverMember = memberRepository.findBySenderName(friendRequestDto.getFriendName()).orElseThrow();
        Friend friend = friendRepository.findByReceiver(receiverMember)
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
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Sender 찾을 수 없음"));
        List<Friend> friends = friendRepository.findBySenderAndStatusOrReceiverAndStatus(member, FriendStatus.ACCEPTED, member, FriendStatus.ACCEPTED);
        return friends.stream()
                .map(friend -> new FriendResponseDto(
                        friend.getSender().getMemberId(),
                        friend.getReceiver().getMemberId(),
                        friend.getStatus().name(),
                        friend.getTitle(),
                        friend.getFriendId()))
                .collect(Collectors.toList());

    }

    // 친구 요청 대기 목록 조회
    public List<FriendResponseDto> getPendingRequests(Long memberId) {
        Member receiver = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Receiver 찾을 수 없음"));
        List<Friend> friends = friendRepository.findByReceiverAndStatus(receiver, FriendStatus.PENDING);
        return friends.stream()
                .map(friend -> new FriendResponseDto(
                        friend.getSender().getMemberId(),
                        friend.getReceiver().getMemberId(),
                        friend.getStatus().name(),
                        friend.getTitle(),
                        friend.getFriendId()))
                .collect(Collectors.toList());
    }

}
