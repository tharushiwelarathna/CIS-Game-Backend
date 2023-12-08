package com.tharushi.tomatogame.service;

import com.tharushi.tomatogame.dto.player.PlayerReqDto;


public interface PlayerService {
    void saveNewPlayer(PlayerReqDto playerReqDto);

    void verifyAccountAndEmail(String token);

}
