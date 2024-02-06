package com.ssafy.sgdc.solo.controller;

import com.ssafy.sgdc.solo.dto.SoloDto;
import com.ssafy.sgdc.solo.dto.request.ChallengeRequest;
import com.ssafy.sgdc.solo.dto.response.SoloListResponse;
import com.ssafy.sgdc.solo.service.SoloService;
import com.ssafy.sgdc.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/solo")
@RequiredArgsConstructor
public class SoloController {

    private final SoloService soloService;

    @PostMapping("/challenge")
    public ResponseEntity<?> makeChallenge(@RequestBody ChallengeRequest request) {
        soloService.createChallenge(request.getUserId(), request.getCategoryId());
        return new ResponseEntity<>(Message.builder().status(200).message("솔로 모드 생성 완료")
                .build(), HttpStatus.OK);
    }

    @PatchMapping("/challenge-auth")
    public ResponseEntity<?> challengeAuth(@RequestBody ChallengeRequest request) {
        soloService.soloImageAuth(request.getUserId(), request.getCategoryId(), request.getSoloAuthImg());
        return new ResponseEntity<>(Message.builder().status(200).message("이미지 인증 완료")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/challenge-list/{userId}")
    public ResponseEntity<?> getSoloList(@PathVariable int userId) {
        List<SoloDto> result = soloService.getSoloList(userId);
        return new ResponseEntity<>(SoloListResponse.builder().status(200).message("솔로 모드 내역 리스트 조회 완료.")
                .solos(result).build(), HttpStatus.OK
        );
    }

    @GetMapping("/today-list/{userId}")
    public ResponseEntity<?> getTodaySoloList(@PathVariable int userId) {
        List<SoloDto> result = soloService.getSoloTodayList(userId);
        return new ResponseEntity<>(SoloListResponse.builder().status(200).message("솔로 모드 내역 당일 리스트 조회 완료.")
                .solos(result).build(), HttpStatus.OK
        );
    }

}
