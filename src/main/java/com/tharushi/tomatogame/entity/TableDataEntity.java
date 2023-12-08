

package com.tharushi.tomatogame.entity;

/**
 * @author Tharushi Welarathna <nirmanitharushi1@gmail.com>
 * @since 12/6/2023
 */
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TableDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private String player;

    private double level;

    private double points;
}
