package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int gameLengthSeconds;
    private int startTimeSeconds;
    private int currentTimeSeconds;
    private boolean ghostHourActive;
    private boolean ghostHourEndTime;
    private int ghostKilled;
    private double score;

    @ManyToMany
    @JoinTable(
            name = "game_player",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> playersList;
}
