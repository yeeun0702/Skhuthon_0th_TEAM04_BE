package com.example.sharediary.friend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FriendResponseDto {
    private Long senderId;
    private Long receiverId;
    private String senderName;
    private String receiverName;
    private String status;
    private String title;
    private Long friendId;

}
