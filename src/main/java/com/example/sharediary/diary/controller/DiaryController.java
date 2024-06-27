package com.example.sharediary.diary.controller;

import com.example.sharediary.diary.dto.DiaryRequestDto;
import com.example.sharediary.diary.dto.DiaryResponseDto;
import com.example.sharediary.diary.service.DiaryService;
import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "일기장 API", description = "Swagger 일기장 API")
@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;

    // 일기장 글 생성하기
    @Operation(summary = "일기장 작성", description = "클라이언트에게 내용과 제목을 입력받아 데이터베이스에 저장")
    @Parameter(name = "diaryRequestDto", description = "클라이언트에게 입력받는 부분")
    @Parameter(name = "member", description = "현재 로그인되어 있는 사용자를 받기 위한 Member 객체")
    @PostMapping("/create")
    public ResponseEntity<String> createDiary(@RequestBody DiaryRequestDto diaryRequestDto, LoginMemberResponseDto member) {
        // ArgumentResolver
        Long memberId = member.id();
        Long createDiaryId = diaryService.createDiary(diaryRequestDto, memberId);
        return new ResponseEntity<>(createDiaryId + "번째 글 작성", HttpStatus.OK);
    }

    // 일기장 조회하기
    @Operation(summary = "전체 일기장 불러오기", description = "일기장 전체를 불러오기. 근데 페이징 처리 안됨.")
    @GetMapping("/read")
    public ResponseEntity<List<DiaryResponseDto>> readDiary() {
        List<DiaryResponseDto> diaries = diaryService.readDiary();
        return new ResponseEntity<>(diaryService.readDiary(), HttpStatus.OK);
    }

    @Operation(summary = "일기장 수정", description = "일기장 ID를 가지고 일기장을 수정하는 API")
    @Parameter(name = "diaryId", description = "일기장의 고유 ID(PK)")
    @Parameter(name = "diaryRequestDto", description = "수정할 내용을 담고 있는 변수 / 클라이언트에게 받는다.")
    @PutMapping("/update/{diaryId}")
    public ResponseEntity<String> updateDiary(@PathVariable Long diaryId, @RequestBody DiaryRequestDto diaryRequestDto, LoginMemberResponseDto member) {
        Long memberId = member.id();
        diaryService.updateDiary(diaryId, diaryRequestDto, memberId);
        return new ResponseEntity<>("일기장 수정 완료", HttpStatus.OK);
    }

    @Operation(summary = "일기장 삭제", description = "일기장 고유 ID를 가지고 게시글을 삭제하는 API")
    @Parameter(name = "diaryId", description = "일기장의 고유 ID(PK)")
    @DeleteMapping("/delete/{diaryId}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long diaryId, LoginMemberResponseDto member) {
        Long memberId = member.id();
        diaryService.deleteDiary(diaryId, memberId);
        return new ResponseEntity<>("일기장 삭제 완료", HttpStatus.OK);
    }
}
