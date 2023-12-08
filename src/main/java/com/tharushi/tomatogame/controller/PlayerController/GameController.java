package com.tharushi.tomatogame.controller.PlayerController;

import com.tharushi.tomatogame.config.security.custom.CustomUserAuthenticator;
import com.tharushi.tomatogame.dto.common.CommonResponse;

import com.tharushi.tomatogame.dto.game.*;
import com.tharushi.tomatogame.dto.player.PlayerScoreDto;
import com.tharushi.tomatogame.service.GameService;
import com.tharushi.tomatogame.constants.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/game")
@RequiredArgsConstructor
@Log4j2
public class GameController {

    private final GameService gameService;

    // API endpoint to start a new game
    @PostMapping(value = "/start")
    public ResponseEntity<?> startGame(@RequestHeader(value = AppConstants.DetailConstants.HEADER_AUTH) String token) {
        // Extracting user ID from the authentication token
        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        // Starting a new game and returning a response
        GameDetailsResDto resDto = gameService.startGame(user_id);
        return ResponseEntity.ok(new CommonResponse<>(true, "Game started", resDto));
    }


    // API endpoint to end an ongoing game
    @PostMapping(value = "/end")
    public ResponseEntity<?> endGame(@RequestHeader(value = AppConstants.DetailConstants.HEADER_AUTH, required = true) String token, @RequestBody GameEndReqDto dto) {
        // Extracting user ID from the authentication token
        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        // Ending the ongoing game and returning a response
        GameEndResDto resDto = gameService.endGame(user_id, dto);
        return ResponseEntity.ok(new CommonResponse<>(true, resDto));
    }

    // API endpoint to check the answer during a game
    @PostMapping(value = "/answer/check")
    public ResponseEntity<?> checkGameAnswer(@RequestHeader(value = AppConstants.DetailConstants.HEADER_AUTH, required = true) String token, @RequestBody GameAnswerCheckReqDto dto) {
        // Extracting user ID from the authentication token
        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        // Checking the answer during the game and returning a response
        GameAnswerCheckResDto resDto = gameService.checkAnswer(user_id, dto);
        return ResponseEntity.ok(new CommonResponse<>(true, resDto));
    }



}
