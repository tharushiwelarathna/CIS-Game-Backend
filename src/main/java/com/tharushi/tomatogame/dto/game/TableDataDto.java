package com.tharushi.tomatogame.dto.game;

/**
 * @author Tharushi Welarathna <nirmanitharushi1@gmail.com>
 * @since 12/6/2023
 */
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TableDataDto {
    private Date date;
    private String player;
    private double level;
    private double points;
}
