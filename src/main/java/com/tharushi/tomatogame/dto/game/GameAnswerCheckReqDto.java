package com.tharushi.tomatogame.dto.game;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameAnswerCheckReqDto {
    private Long score_id;
    private Long score_details_id;
    private int answer;
}
