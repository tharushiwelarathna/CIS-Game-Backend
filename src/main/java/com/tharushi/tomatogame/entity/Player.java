package com.tharushi.tomatogame.entity;


import com.tharushi.tomatogame.enums.common.AccountVerifyStatus;
import com.tharushi.tomatogame.enums.common.ActiveStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {
    // Unique identifier for each player
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Unique username for the player (not nullable)
    @Column(unique=true, nullable = false)
    private String userName;
    // Password associated with the player's account
    private String password;
    // Unique email address for the player (not nullable)
    @Column(unique=true, nullable = false)
    private String email;
    // Status of email verification for the player's account
    @Enumerated(value = EnumType.STRING)
    private AccountVerifyStatus email_verified;
    // Player's current level in the game
    private double level;
    // Date and time when the player's account was created
    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    // Date and time when the player's account was last updated
    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    // Status of the player's account (e.g., active, inactive)
    @Enumerated(value = EnumType.STRING)
    private ActiveStatus status;

    // Token used for account verification
    @Lob
    private String current_verify_token;
    // Date and time when the email verification link was sent
    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date email_verify_link_timestamp;
    // List of scores associated with the player
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Score> scores = new ArrayList<>();
}
