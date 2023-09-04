package com.mavrictan.halloweengameapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int upgrade;
    private String weaponName;
    private int cost;

    @OneToMany(mappedBy = "weapon")
    @JsonIgnore
    private List<PlayerWeapon> playerWeapons;
}
