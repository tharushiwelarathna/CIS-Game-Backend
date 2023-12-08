package com.tharushi.tomatogame.dto.game;

import com.tharushi.tomatogame.enums.common.GameEndType;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameEndReqDto {
    private Long score_id;
    private Long score_details_id;
    private GameEndType end_type;
}
