package com.tharushi.tomatogame.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tharushi.tomatogame.enums.common.GameEndType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Score {
    // Unique identifier for each score record
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Date and time when the game started
    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date_time;
    // Date and time when the game ended
    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_date_time;
    // Points earned by the player in the game
    private double point;
    // Time spent by the player in the game
    private double spend_time;
    // Type of game end (e.g., completed, timeout)
    @Enumerated(value = EnumType.STRING)
    private GameEndType game_end_type;
    // Player associated with this score record
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Player player;
    // List of score details associated with this score record
    @OneToMany(mappedBy = "score", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScoreDetail> scores = new ArrayList<>();
}
