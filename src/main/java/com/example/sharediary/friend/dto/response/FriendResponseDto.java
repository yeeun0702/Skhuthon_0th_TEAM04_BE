package com.example.sharediary.friend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FriendResponseDto {
    private Long memberId;
    private Long receiverId;
    private String status;
    private String title;
    private Long friendId;

}
