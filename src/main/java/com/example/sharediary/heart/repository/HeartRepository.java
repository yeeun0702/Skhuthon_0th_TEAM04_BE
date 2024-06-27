package com.example.sharediary.heart.repository;

import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.heart.domain.Heart;
import com.example.sharediary.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    boolean existsByMemberAndDiary(Member member, Diary diary);
    void deleteByMemberAndDiary(Member member, Diary diary);
}
