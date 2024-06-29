package com.example.sharediary.friend.repository;

import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.friend.domain.Friend;
import com.example.sharediary.friend.domain.FriendStatus;
import com.example.sharediary.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findBySenderAndStatusOrReceiverAndStatus(Member sender, FriendStatus senderStatus, Member receiver, FriendStatus receiverStatus);
    List<Friend> findByReceiverAndStatus(Member receiver, FriendStatus status);

    Optional<Friend> findByReceiver(Member receiverMember);

    boolean existsBySenderAndReceiver(Member sender, Member receiver);

    Friend findBySenderAndReceiver(Member member, Member receiverMember);

}
