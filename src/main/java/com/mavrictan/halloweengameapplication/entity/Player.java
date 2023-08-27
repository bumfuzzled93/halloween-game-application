package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String mobileNumber;
    private int score;
    private int credits;
    private boolean withSpecial;

    @ManyToMany
    @JoinTable(
            name = "player_weapon",
            joinColumns = @JoinColumn(name = "played_id"),
            inverseJoinColumns = @JoinColumn(name = "weapon_id"))
    private List<Weapon> purchasedWeapons;

    public Player(String username, String mobileNumber) {
        this.username = username;
        this.mobileNumber = mobileNumber;
    }
}
