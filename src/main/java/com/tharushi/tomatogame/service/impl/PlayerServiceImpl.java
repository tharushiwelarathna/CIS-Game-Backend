package com.tharushi.tomatogame.service.impl;

import com.tharushi.tomatogame.constants.AppConstants;
import com.tharushi.tomatogame.constants.EmailHtmlConstant;
import com.tharushi.tomatogame.dto.player.PlayerReqDto;
import com.tharushi.tomatogame.entity.Player;
import com.tharushi.tomatogame.enums.common.AccountVerifyStatus;
import com.tharushi.tomatogame.enums.common.ActiveStatus;
import com.tharushi.tomatogame.exception.dto.CustomServiceException;
import com.tharushi.tomatogame.repository.PlayerRepository;
import com.tharushi.tomatogame.service.PlayerService;
import com.tharushi.tomatogame.utilities.EmailSender;
import com.tharushi.tomatogame.utilities.EmailVerificationTokenGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.mail.MessagingException;
import java.util.Date;
import java.util.Objects;


@Log4j2
@RequiredArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {


    private final PlayerRepository playerRepository;

    private final ModelMapper modelMapper;

    private final EmailSender emailSender;

    private final PasswordEncoder passwordEncoder;

    private final EmailVerificationTokenGenerator emailVerificationTokenGenerator;

    @Value("${game.frontend.base.url}")
    private String charityBaseUrl;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveNewPlayer(PlayerReqDto playerReqDto) {
        log.info("Start function saveNewPlayer");

        if (!EmailValidator.getInstance().isValid(playerReqDto.getEmail())) throw new CustomServiceException(AppConstants.ErrorConstants.INVALID_EMAIL);
        if (playerRepository.findFirstByEmail(playerReqDto.getEmail()).isPresent())
            throw new CustomServiceException(AppConstants.DuplicatedConstants.EMAIL_ALREADY_EXISTS);

        if (playerRepository.findFirstByUserName(playerReqDto.getUserName()).isPresent())
            throw new CustomServiceException(AppConstants.DuplicatedConstants.USERNAME_ALREADY_EXISTS);

        Player player = modelMapper.map(playerReqDto, Player.class);
        player.setCreated(new Date());
        player.setUpdated(new Date());
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        player.setStatus(ActiveStatus.ACTIVE);
        player.setEmail_verified(AccountVerifyStatus.VERIFY);
        Player savedPlayer = playerRepository.save(player);

        this.sendVerificationEmail(savedPlayer);

    }

    @Override
    public void verifyAccountAndEmail(String token) {
        log.info("Start function verifyAccount");
        try {
            Jws<Claims> claimsJws = emailVerificationTokenGenerator.verify(token);
            if (claimsJws == null) throw new CustomServiceException("Your verification link has expired");

            long userId = Long.parseLong(claimsJws.getBody().getSubject());
            String email = (String) claimsJws.getBody().get(EmailVerificationTokenGenerator.EMAIL_CLAIM);
            Player player = playerRepository.findFirstByIdAndEmail(userId, email)
                    .orElseThrow(() -> new CustomServiceException(AppConstants.NotFoundConstants.NO_USER_FOUND));

            if (!Objects.equals(player.getCurrent_verify_token(), token)) {
                throw new CustomServiceException(AppConstants.ErrorConstants.TOKEN_HAS_EXPIRED);
            }

            if (player.getStatus() == ActiveStatus.ACTIVE)
                throw new CustomServiceException("Account Already verified!");

            player.setStatus(ActiveStatus.ACTIVE);
            player.setEmail_verified(AccountVerifyStatus.VERIFY);
            playerRepository.save(player);

        } catch (Exception ex) {
            log.error("verifyAccount : {}", ex.getMessage());
            throw ex;
        }
    }


    public void sendVerificationEmail(Player player) {
        try {
            String token = emailVerificationTokenGenerator.generate(player.getId(), player.getEmail());
            emailSender.sendSimpleEmail(player.getEmail(), AppConstants.Email.VERIFY_EMAIL,
                    EmailHtmlConstant.getPlayerVerificationEmailBody(
                            AppConstants.Email.PLAYER_EMAIL_CONFORM_URL.replace("{player_frontend_base_url}", charityBaseUrl).replace("{token}", token), player));
            player.setCurrent_verify_token(token);
            player.setEmail_verify_link_timestamp(new Date());
            playerRepository.save(player);
        } catch (MessagingException e) {
            throw new CustomServiceException(e.getMessage());
        }
    }
}
