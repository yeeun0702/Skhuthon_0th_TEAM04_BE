package com.example.sharediary.diary.controller;

import com.example.sharediary.diary.dto.DiaryDateRequestDto;
import com.example.sharediary.diary.dto.DiaryRequestDto;
import com.example.sharediary.diary.dto.DiaryResponseDto;
import com.example.sharediary.diary.dto.PagedResponse;
import com.example.sharediary.diary.service.DiaryService;
import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "일기장 API", description = "Swagger 일기장 API")
@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/v1/diary")
public class DiaryController {
    private final DiaryService diaryService;

    // 일기장 글 생성하기
    @Operation(summary = "일기장 작성", description = "클라이언트에게 내용과 제목을 입력받아 데이터베이스에 저장")
    @Parameter(name = "diaryRequestDto", description = "클라이언트에게 입력받는 부분")
    @Parameter(name = "member", description = "현재 로그인되어 있는 사용자를 받기 위한 Member 객체")
    @PostMapping("/create")
    public ResponseEntity<String> createDiary(@RequestBody DiaryRequestDto diaryRequestDto, LoginMemberResponseDto member) {
        // ArgumentResolver
        Long senderId = member.id();
        Long createDiaryId = diaryService.createDiary(diaryRequestDto, senderId);
        return new ResponseEntity<>(createDiaryId + "번째 글 작성", HttpStatus.OK);
    }

    // 일기장 전체 조회하기
    @Operation(summary = "전체 일기장 불러오기", description = "일기장 전체를 불러오기")
    @GetMapping("/read")
    public ResponseEntity<PagedResponse<DiaryResponseDto>> readDiary(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "diaryId, asc") String sort
    ) {
        Pageable pageable;
        if (sort.isEmpty()) {
            // PageRequest.of: 정렬 기준 - diaryId, 오름차순 정렬하는 Pageable 객체 생성
            pageable = PageRequest.of(page, size, Sort.by("diaryId").ascending());
        } else {
            // sort 파라미터가 제공된 경우 이를 ,로 분리하여 정렬기준, 정렬 방향 추출
            String[] sortParams = sort.split(",");
            // Sort.Direction.fromString(sortParams[1]): 정렬 방향
            // sortParams[0]: 정렬 기준
            Sort sortOrder = Sort.by(Sort.Direction.fromString((sortParams[1]).trim()), sortParams[0].trim()    );
            pageable = PageRequest.of(page, size, sortOrder);
        }

        PagedResponse<DiaryResponseDto> diaries = diaryService.readDiary(pageable);
        return new ResponseEntity<>(diaries, HttpStatus.OK);
    }

    // friendId 통해 조회
    @Operation(summary = "다이어리 목록", description = "일기장 friendId로 조회")
    @GetMapping("/read/friend/{friendId}")
    public ResponseEntity<PagedResponse<DiaryResponseDto>> readDiaryByFriendId(@PathVariable Long friendId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
//        @RequestParam(value = "sort", defaultValue = "senderId, asc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size);
//        if (sort.isEmpty()) {
//            // PageRequest.of: 정렬 기준 - diaryId, 오름차순 정렬하는 Pageable 객체 생성
//            pageable = PageRequest.of(page, size, Sort.by("senderId").ascending());
//        } else {
//            // sort 파라미터가 제공된 경우 이를 ,로 분리하여 정렬기준, 정렬 방향 추출
//            String[] sortParams = sort.split(",");
//            // Sort.Direction.fromString(sortParams[1]): 정렬 방향
//            // sortParams[0]: 정렬 기준
//            Sort sortOrder = Sort.by(Sort.Direction.fromString((sortParams[1]).trim()), sortParams[0].trim());
//            pageable = PageRequest.of(page, size, sortOrder);
//        }

        PagedResponse<DiaryResponseDto> diaries = diaryService.readDiaryByFriendId(friendId, pageable);
        return new ResponseEntity<>(diaries, HttpStatus.OK);
    }

    // friendId, 연월 통해 조회
    @GetMapping("/read/month/{friendId}")
    public ResponseEntity<List<DiaryResponseDto>> readDiaryByMonth(@PathVariable Long friendId, @RequestBody DiaryDateRequestDto diaryDate) {
        List<DiaryResponseDto> diaryResponseDto = diaryService.readDiaryByMonth(friendId, diaryDate);
        return new ResponseEntity<>(diaryResponseDto, HttpStatus.OK);
    }

    // 날짜, friendId 통해 조회(달력 클릭했을 때 나오는 상세페이지)
    @GetMapping("/read/date/{friendId}")
    public ResponseEntity<List<DiaryResponseDto>> readDiaryByDate(@PathVariable Long friendId, @RequestBody DiaryDateRequestDto diaryDate) {
        List<DiaryResponseDto> diaryResponseDtos = diaryService.readDiaryByDate(friendId, diaryDate);
        return new ResponseEntity<>(diaryResponseDtos, HttpStatus.OK);
    }

    // 일기장 diaryId로 조회(상세 페이지)
    @Operation(summary = "상세페이지", description = "일기장 diaryId로 조회")
    @GetMapping("/read/{diaryId}")
    public ResponseEntity<DiaryResponseDto> readDiaryById(@PathVariable Long diaryId) {
        DiaryResponseDto diaryResponseDto = diaryService.readDiaryById(diaryId);
        return new ResponseEntity<>(diaryResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "일기장 수정", description = "일기장 ID를 가지고 일기장을 수정하는 API")
    @Parameter(name = "diaryId", description = "일기장의 고유 ID(PK)")
    @Parameter(name = "diaryRequestDto", description = "수정할 내용을 담고 있는 변수 / 클라이언트에게 받는다.")
    @PutMapping("/update/{diaryId}")
    public ResponseEntity<String> updateDiary(@PathVariable Long diaryId, @RequestBody DiaryRequestDto diaryRequestDto, LoginMemberResponseDto member) {
        Long senderId = member.id();
        diaryService.updateDiary(diaryId, diaryRequestDto, senderId);
        return new ResponseEntity<>("일기장 수정 완료", HttpStatus.OK);
    }

    @Operation(summary = "일기장 삭제", description = "일기장 고유 ID를 가지고 게시글을 삭제하는 API")
    @Parameter(name = "diaryId", description = "일기장의 고유 ID(PK)")
    @DeleteMapping("/delete/{diaryId}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long diaryId, LoginMemberResponseDto member) {
        Long senderId = member.id();
        diaryService.deleteDiary(diaryId, senderId);
        return new ResponseEntity<>("일기장 삭제 완료", HttpStatus.OK);
    }
}
