package com.tharushi.tomatogame.service.impl;

import com.tharushi.tomatogame.dto.game.*;
import com.tharushi.tomatogame.dto.player.PlayerScoreDto;
import com.tharushi.tomatogame.entity.Player;
import com.tharushi.tomatogame.entity.Score;
import com.tharushi.tomatogame.entity.ScoreDetail;
import com.tharushi.tomatogame.repository.PlayerRepository;
import com.tharushi.tomatogame.repository.ScoreDetailRepository;
import com.tharushi.tomatogame.repository.ScoreRepository;
import com.tharushi.tomatogame.service.GameService;
import com.tharushi.tomatogame.utilities.TomatoAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private final PlayerRepository playerRepository;
    private final ScoreRepository scoreRepository;
    private final ScoreDetailRepository scoreDetailRepository;
    private final TomatoAPI tomatoAPI;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GameDetailsResDto startGame(Long player_id) {
        Player player = playerRepository.findById(player_id).orElseThrow(() -> new ClassCastException("User Not Found!"));
        Score score = Score.builder()
                .player(player)
                .start_date_time(new Date())
                .point(0.0)
                .spend_time(0.0)
                .build();
        Score savedScore = scoreRepository.save(score);

        GameResDto resDto = tomatoAPI.getNewQuestion();
        resDto.setScore_id(savedScore.getId());

        ScoreDetail scoreDetail = ScoreDetail.builder()
                .solution(resDto.getSolution())
                .question_link(resDto.getQuestion())
                .score(score)
                .answer(0.0)
                .is_correct(false)
                .build();

        ScoreDetail saveScoreDetails = scoreDetailRepository.save(scoreDetail);
        return new GameDetailsResDto(savedScore.getId(), saveScoreDetails.getId(), resDto.getQuestion());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GameEndResDto endGame(Long player_id, GameEndReqDto dto) {
        Score score = scoreRepository.findByIdAndPlayerId(dto.getScore_id(), player_id).orElseThrow(() -> new ClassCastException("Cannot find a record!"));
        score.setEnd_date_time(new Date());
        score.setGame_end_type(dto.getEnd_type());
        scoreRepository.save(score);
        return new GameEndResDto(score.getId(), score.getPoint());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GameAnswerCheckResDto checkAnswer(Long player_id, GameAnswerCheckReqDto dto) {
        Score score = scoreRepository.findByIdAndPlayerId(dto.getScore_id(), player_id).orElseThrow(() -> new ClassCastException("Cannot find a record!"));
        ScoreDetail scoreDetail = scoreDetailRepository.findById(dto.getScore_details_id()).orElseThrow(() -> new ClassCastException("Cannot find a record!"));
        scoreDetail.setAnswer(dto.getAnswer());
        boolean isTrue = false;
        if (dto.getAnswer()==scoreDetail.getSolution()){
            score.setPoint(score.getPoint()+10);
            isTrue=true;
        }
        scoreDetail.set_correct(isTrue);
        scoreDetail.setUpdatedTimestamp(new Date());
        scoreDetailRepository.save(scoreDetail);
        scoreRepository.save(score);


        GameResDto resDto = tomatoAPI.getNewQuestion();

        ScoreDetail newScoreDetail = ScoreDetail.builder()
                .solution(resDto.getSolution())
                .score(score)
                .question_link(resDto.getQuestion())
                .answer(0.0)
                .is_correct(false)
                .build();

        ScoreDetail saveNewScoreDetails = scoreDetailRepository.save(newScoreDetail);


        return  GameAnswerCheckResDto.builder()
                .question(resDto.getQuestion())
                .score_details_id(saveNewScoreDetails.getId())
                .score_id(score.getId())
                .point(score.getPoint())
                .is_true(isTrue)
                .build();
    }




}
