package com.tharushi.tomatogame.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ScoreDetail {
    // Unique identifier for each score detail record
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Link to the question associated with this score detail
    @Lob
    private String question_link;
    // Numeric solution to the question
    private double solution;

    // Player's answer to the question
    private double answer;

    // Flag indicating whether the answer is correct
    private boolean is_correct;

    // Date and time when the score detail was recorded
    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // Date and time when the score detail was last updated
    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTimestamp;

    // Score record associated with this score detail
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Score score;

}
