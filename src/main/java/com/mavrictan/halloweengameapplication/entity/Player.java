package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String mobileNumber;
    private String email;
    private int credits;

    private int powerupAmmoBox;
    private int powerupDrone;
    private int powerupBonus;

    private boolean starterPackClaimed;

    @OneToMany(mappedBy = "player")
    @Singular
    private List<PlayerWeapon> purchasedWeapons;
}
