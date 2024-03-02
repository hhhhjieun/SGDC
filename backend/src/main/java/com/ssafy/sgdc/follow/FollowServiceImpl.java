package com.ssafy.sgdc.follow;



import com.ssafy.sgdc.exception.CustomApiException;
import com.ssafy.sgdc.follow.dto.FollowerListResponseDto;
import com.ssafy.sgdc.follow.dto.FollowingListResponseDto;
import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepo followRepository;
    private final UserRepo userRepository;

    /**
     * 팔로우
     */
    @Override
    @Transactional
    public void follow(int toUserId, int fromUserId) throws CustomApiException {
        try {
            followRepository.mFollow(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 팔로우 하셨습니다.");
        }
    }

    /**
     * 언팔로우
     */
    @Override
    @Transactional
    public void unFollow(int toUserId, int fromUserId) {
        try {
            followRepository.mUnFollow(fromUserId, toUserId);
        } catch (Exception ignored) {

        }
    }

    /**
     * 팔로워 리스트
     */
    @Override
    @Transactional
    public List<FollowerListResponseDto> followerList(int fromUserId) {

        List<Follow> followList = followRepository.getListByFromUserId(fromUserId);
        List<FollowerListResponseDto> followDtoList = new ArrayList<>();

        for (Follow follow : followList) {
            //// 유저정보
            //// 이후 세션 추가하면 수정될 부분
            User userInfo = userRepository.findByUserId(follow.getUserId().getUserId())
                    .orElseThrow(() -> new RuntimeException("followerList -> 해당 유저를 찾을 수 없습니다."));

            followDtoList.add(new FollowerListResponseDto(userInfo.getUserId(),userInfo.getUserNickname(),userInfo.getUserImg()));
        }
        return followDtoList;
    }

    /**
     * 팔로잉 리스트
     */
    @Override
    @Transactional
    public List<FollowingListResponseDto> followingList(int toUserId) {
        List<Follow> followList = followRepository.getListByToUserId(toUserId);
        List<FollowingListResponseDto> followDtoList = new ArrayList<>();
        for (Follow follow : followList) {
            // TODO : 세션 추가하면 수정
            User userInfo = userRepository.findByUserId(follow.getFollowingId().getUserId())
                    .orElseThrow(() -> new RuntimeException("followingList -> 해당 유저를 찾을 수 없습니다."));
            followDtoList.add(new FollowingListResponseDto(userInfo.getUserId(),userInfo.getUserNickname(),userInfo.getUserImg()));
        }
        return followDtoList;
    }

    @Override
    public int followingCount(User userId) {
        return followRepository.countByUserId(userId);
    }

    @Override
    public int followerCount(User userId) {
        return followRepository.countByFollowingId(userId);
    }

    @Override
    public boolean isFollow(User followingId, User userId) {
        Optional<Follow> isFollow = followRepository.findByFollowingIdAndUserId(followingId, userId);
        return !isFollow.equals(Optional.empty());
    }

}


