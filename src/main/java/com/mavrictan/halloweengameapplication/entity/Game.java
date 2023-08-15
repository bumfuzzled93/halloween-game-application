package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

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

}
