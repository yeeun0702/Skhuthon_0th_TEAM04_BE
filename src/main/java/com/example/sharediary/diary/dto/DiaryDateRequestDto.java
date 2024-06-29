package com.example.sharediary.diary.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiaryDateRequestDto {
    private int year;
    private int month;
    private int date;
}
