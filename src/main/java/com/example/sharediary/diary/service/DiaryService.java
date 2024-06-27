package com.example.sharediary.diary.service;

import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.diary.dto.DiaryRequestDto;
import com.example.sharediary.diary.dto.DiaryResponseDto;
import com.example.sharediary.diary.repository.DiaryRepository;
import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import com.example.sharediary.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    // 일기장 글 생성하기
    @Transactional
    public Long createDiary(DiaryRequestDto diaryRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 사용자."));

        Diary diary = Diary.builder()
                .title(diaryRequestDto.getTitle())
                .content(diaryRequestDto.getContent())
                .member(member)
                .build();
        diaryRepository.save(diary);
        return diary.getDiaryId();
    }

    // 일기장 조회하기
    @Transactional
    public List<DiaryResponseDto> readDiary() {
        List<Diary> readDiary = diaryRepository.findAll();
        List<DiaryResponseDto> diaryDtoRead = new ArrayList<>();

        for (Diary diary : readDiary) {
            DiaryResponseDto diaryDto = DiaryResponseDto.builder()
                    .diaryId(diary.getDiaryId())
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .memberName(diary.getMember().getMemberName())
                    .build();
            diaryDtoRead.add(diaryDto);
        }
        return diaryDtoRead;
    }

    // 일기장 수정하기
    @Transactional
    public void updateDiary(Long diaryId, DiaryRequestDto diaryRequestDto, Long memberId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + diaryId));

        if (!diary.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        diary.update(diaryRequestDto);
        diaryRepository.save(diary);
    }

    // 일기장 삭제하기
    @Transactional
    public void deleteDiary(Long diaryId, Long memberId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new IllegalArgumentException("해당 일기가 없습니다. id=" + diaryId));

        if (!diary.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        diaryRepository.delete(diary);
    }
}