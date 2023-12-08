package com.tharushi.tomatogame.dto.player;

import com.tharushi.tomatogame.enums.common.AccountVerifyStatus;
import com.tharushi.tomatogame.enums.common.ActiveStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class PlayerResDto {
    private String id;
    private String userName;
    private String password;
    private String email;
    private AccountVerifyStatus email_verified;
    private double level;
    private Date created;
    private Date updated;
    private ActiveStatus status;
}
