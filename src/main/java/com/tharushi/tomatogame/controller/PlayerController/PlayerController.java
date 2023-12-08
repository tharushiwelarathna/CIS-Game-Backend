package com.tharushi.tomatogame.controller.PlayerController;


import com.tharushi.tomatogame.config.security.custom.CustomUserAuthenticator;
import com.tharushi.tomatogame.config.throttling_config.Throttling;
import com.tharushi.tomatogame.dto.common.CommonResponse;
import com.tharushi.tomatogame.dto.game.GameDetailsResDto;
import com.tharushi.tomatogame.dto.player.PlayerReqDto;

import com.tharushi.tomatogame.service.GameService;
import com.tharushi.tomatogame.service.PlayerService;
import com.tharushi.tomatogame.constants.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    private final GameService gameService;

    // API endpoint for player sign-up
    @Throttling(timeFrameInSeconds = 60, calls = 5)
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> playerSignUp(@RequestBody PlayerReqDto reqDto){
        // Saving a new player and returning a success response
        playerService.saveNewPlayer(reqDto);
        return ResponseEntity.ok(new CommonResponse<>(true,"sign up successfully!"));
    }

    // API endpoint for verifying a player's account
    @Throttling(timeFrameInSeconds = 60, calls = 10)
    @PatchMapping(value = "/account/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyAccount(@RequestParam("token") String token) {
        // Logging the token and activating the player's account
        log.info("verifyAccount token => reqBody: {}",token);
        playerService.verifyAccountAndEmail(token);
        return ResponseEntity.ok(new CommonResponse<>(true, "Your account has been activated successfully!"));
    }

    // API endpoint to start a new game for a player
    @PostMapping(value = "/game/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startGame(@RequestHeader(value = AppConstants.DetailConstants.HEADER_AUTH) String token) {
        // Extracting user ID from the authentication token
        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        // Starting a new game for the player and returning a response
        GameDetailsResDto resDto = gameService.startGame(user_id);
        return ResponseEntity.ok(new CommonResponse<>(true, "Game started", resDto));
    }
}