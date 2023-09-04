package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String photonId;
    private int gameLengthSeconds;
    private long startTimeSeconds;
    private int ghostKilled;
    private double score;

    @ManyToMany
    @JoinTable(
            name = "game_player",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> playersList;
}
