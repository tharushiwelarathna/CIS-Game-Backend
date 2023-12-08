package com.tharushi.tomatogame.dto.game;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GameAnswerCheckResDto {
    private Long score_id;
    private Long score_details_id;
    private Boolean is_true;
    private double point;
    private String question;
}
