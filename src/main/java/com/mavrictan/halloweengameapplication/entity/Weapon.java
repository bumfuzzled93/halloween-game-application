package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int upgrade;
    private String weaponName;
    private double killChance;
    private int ammo;
    private int reloadSec;
    private int cooldownSec;
}
