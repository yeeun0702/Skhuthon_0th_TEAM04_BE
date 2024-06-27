package com.example.sharediary.diary.repository;

import com.example.sharediary.diary.domain.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Override
    Page<Diary> findAll(Pageable pageable);
}
