package com.example.sharediary.diary.repository;

import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Override
    Page<Diary> findAll(Pageable pageable);

    Page<Diary> findBySenderMemberId(Long friendId, Pageable pageable);
    Diary findBySenderAndReceiver(Member sender, Member receiver);

    List<Diary> findByCreatedDate(LocalDateTime createdDate);
}
