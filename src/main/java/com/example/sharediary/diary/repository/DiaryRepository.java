package com.example.sharediary.diary.repository;

import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.friend.domain.Friend;
import com.example.sharediary.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Override
    Page<Diary> findAll(Pageable pageable);

    Page<Diary> findByFriendFriendId(Long friendId, Pageable pageable);
    //Diary findBySenderAndReceiver(Member sender, Member receiver);

    @Query("SELECT d FROM Diary d " +
            "LEFT JOIN d.friend f " +
            "WHERE f.friendId = :friendId " +
            "AND FUNCTION('YEAR', d.createdDate) = :year " +
            "AND FUNCTION('MONTH', d.createdDate) = :month")
    List<Diary> findByFriendAndCreatedMonth(@Param("friendId") Long friendId,
                                           @Param("year") Integer year,
                                           @Param("month") Integer month);

    @Query("SELECT d FROM Diary d " +
            "LEFT JOIN d.friend f " +
            "WHERE f.friendId = :friendId " +
            "AND FUNCTION('YEAR', d.createdDate) = :year " +
            "AND FUNCTION('MONTH', d.createdDate) = :month " + // 공백 추가
            "AND FUNCTION('DAY', d.createdDate) = :date") // 'DATE' 대신 'DAY' 사용
    List<Diary> findByFriendAndCreateDate(@Param("friendId") Long friendId,
                                          @Param("year") Integer year,
                                          @Param("month") Integer month,
                                          @Param("date") Integer date);
}

