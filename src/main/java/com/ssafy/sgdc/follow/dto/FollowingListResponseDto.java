package com.ssafy.sgdc.follow.dto;

import com.ssafy.sgdc.follow.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowingListResponseDto {

    private String userNickname;
    private Long userId;
    private String userImg;


    public FollowingListResponseDto(Long userId, String userNickname, String userImg) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userImg = userImg;
    }

}