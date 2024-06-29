package com.example.sharediary.friend.dto.reqeust;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendRequestDto {

    private String friendName;
    private String title;

}
