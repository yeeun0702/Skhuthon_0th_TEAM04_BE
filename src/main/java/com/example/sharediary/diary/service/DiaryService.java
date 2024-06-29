package com.example.sharediary.diary.service;

import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.diary.dto.DiaryRequestDto;
import com.example.sharediary.diary.dto.DiaryResponseDto;
import com.example.sharediary.diary.dto.PagedResponse;
import com.example.sharediary.diary.repository.DiaryRepository;
import com.example.sharediary.friend.domain.Friend;
import com.example.sharediary.friend.repository.FriendRepository;
import com.example.sharediary.heart.dto.request.HeartRequestDto;
import com.example.sharediary.heart.repository.HeartRepository;
import com.example.sharediary.heart.service.HeartService;
import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import com.example.sharediary.member.dto.request.MemberRequestDto;
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
    private  final HeartRepository heartRepository;
    private final FriendRepository friendRepository;

    // 일기장 글 생성하기
    @Transactional
    public Long createDiary(DiaryRequestDto diaryRequestDto, Long senderId) {
        Member member = memberRepository.findById(senderId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Member receiverMember = memberRepository.findById(diaryRequestDto.getReceiverId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 수신자입니다."));

        Friend friend = friendRepository.findBySenderAndReceiver(member, receiverMember);

        System.out.println(friend.getFriendId());

        if (!friendRepository.existsBySenderAndReceiver(member, receiverMember)) {
            throw new IllegalArgumentException("친구 관계가 존재하지 않습니다.");
        }

        Diary diary = Diary.builder()
                .title(diaryRequestDto.getTitle())
                .content(diaryRequestDto.getContent())
                .sing(diaryRequestDto.getSing())
                .heartCount(diaryRequestDto.getHeartCount())
                .sender(member)
                .receiver(receiverMember)
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
                        .heartCount(diary.getHeartCount())
                        .senderName(diary.getSender().getSenderName())
                        .receiverName(diary.getReceiver().getSenderName())
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

    // friendId 통해 조회
    @Transactional
    public PagedResponse<DiaryResponseDto> readDiaryByFriendId(Long friendId, Pageable pageable) {
        Page<Diary> diaryPage = diaryRepository.findBySenderMemberId(friendId, pageable);
        List<DiaryResponseDto> diaryDtoRead = diaryPage.getContent().stream()
                .map(diary -> DiaryResponseDto.builder()
                        .diaryId(diary.getDiaryId())
                        .title(diary.getTitle())
                        .content(diary.getContent())
                        .sing(diary.getSing())
                        .heartCount(diary.getHeartCount())
                        .senderName(diary.getSender().getSenderName())
                        .receiverName(diary.getReceiver().getSenderName())

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
    public void updateDiary(Long diaryId, DiaryRequestDto diaryRequestDto, Long senderId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + diaryId));

        if (!diary.getSender().getMemberId().equals(senderId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        diary.update(diaryRequestDto);
        diaryRepository.save(diary);
    }

    // 일기장 삭제하기
    @Transactional
    public void deleteDiary(Long diaryId, Long senderId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new IllegalArgumentException("해당 일기가 없습니다. id=" + diaryId));

        if (!diary.getSender().getMemberId().equals(senderId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        diaryRepository.delete(diary);
    }
}