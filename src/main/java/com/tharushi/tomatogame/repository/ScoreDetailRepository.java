package com.tharushi.tomatogame.repository;

import com.tharushi.tomatogame.entity.ScoreDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreDetailRepository extends JpaRepository<ScoreDetail, Long> {
}
