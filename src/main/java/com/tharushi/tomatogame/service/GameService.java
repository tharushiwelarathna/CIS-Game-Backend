package com.tharushi.tomatogame.service;

import com.tharushi.tomatogame.dto.game.*;


public interface GameService {
    GameDetailsResDto startGame(Long player_id);
    GameEndResDto endGame(Long player_id, GameEndReqDto gameEndType);
    GameAnswerCheckResDto checkAnswer(Long player_id, GameAnswerCheckReqDto gameAnswerCheckReqDto);
}
