package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Redemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long playerId;
    private long staffId;
    private int creditsIssued;
    private String imageFileUuid;
}
