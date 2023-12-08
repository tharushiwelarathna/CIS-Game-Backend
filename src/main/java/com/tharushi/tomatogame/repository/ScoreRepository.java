package com.tharushi.tomatogame.repository;

import com.tharushi.tomatogame.entity.Score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<Score> findByIdAndPlayerId(Long score_id, Long player_id);
}
