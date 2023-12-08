

package com.tharushi.tomatogame.dto.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tharushi Welarathna <nirmanitharushi1@gmail.com>
 * @since 12/6/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerScoreDto {
    private String playerName;
    private double points;
}