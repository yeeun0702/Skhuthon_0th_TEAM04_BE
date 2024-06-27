package com.example.sharediary.friend.dto.reqeust;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendRequestDto {
    private Long id;
    private String title;
}
