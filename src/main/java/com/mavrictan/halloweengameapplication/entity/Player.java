package com.mavrictan.halloweengameapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String mobileNumber;
    @JsonIgnore
    private String email;

    private double credits;
    private int score;
    private int powerupAmmoBox;
    private int powerupDrone;
    private int powerupBonus;

    private boolean starterPackClaimed;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Singular
    private List<PlayerWeapon> purchasedWeapons;

    public enum PowerUp {
        AMMO_BOX,
        DRONE,
        BONUS,
    }
}

