package com.tharushi.tomatogame.dto.game;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameDetailsResDto {
    private Long score_id;
    private Long score_details_id;
    private String question;

}
