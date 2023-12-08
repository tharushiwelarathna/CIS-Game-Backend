package com.tharushi.tomatogame.dto.auth;

import com.tharushi.tomatogame.dto.player.PlayerResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class PlayerAuthDto extends PlayerResDto implements CommonUserAuth {
}
