package com.tharushi.tomatogame.dto.game;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameEndResDto {
    private Long score_id;
    private double total_point;
}
