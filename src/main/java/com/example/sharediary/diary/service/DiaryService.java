package com.example.sharediary.diary.service;

import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.diary.dto.DiaryRequestDto;
import com.example.sharediary.diary.dto.DiaryResponseDto;
import com.example.sharediary.diary.dto.PagedResponse;
import com.example.sharediary.diary.repository.DiaryRepository;
import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import com.example.sharediary.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                .sing(diaryRequestDto.getSing())
                .member(member)
                .build();
        diaryRepository.save(diary);
        return diary.getDiaryId();
    }

    // 일기장 전체 조회하기
    @Transactional
    public PagedResponse<DiaryResponseDto> readDiary(Pageable pageable) {
        Page<Diary> diaryPage = diaryRepository.findAll(pageable);
        List<DiaryResponseDto> diaryDtoRead = diaryPage.getContent().stream()
                .map(diary -> DiaryResponseDto.builder()
                        .diaryId(diary.getDiaryId())
                        .title(diary.getTitle())
                        .content(diary.getContent())
                        .sing(diary.getSing())
                        .memberName(diary.getMember().getMemberName())
                        .build())
                .collect(Collectors.toList());

        PagedResponse<DiaryResponseDto> response = new PagedResponse<>();
        response.setContent(diaryDtoRead);
        response.setPageNumber(diaryPage.getNumber());
        response.setPageSize(diaryPage.getSize());
        response.setTotalElements(diaryPage.getTotalElements());
        response.setTotalPages(diaryPage.getTotalPages());
        response.setLast(diaryPage.isLast());
        response.setFirst(diaryPage.isFirst());

        return response;
    }

    // 일기장 diaryId로 조회(상세 페이지)
    @Transactional
    public DiaryResponseDto readDiaryById(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id= " + diaryId));

        return DiaryResponseDto.of(diary);
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