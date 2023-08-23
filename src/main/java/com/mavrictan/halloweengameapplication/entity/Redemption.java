package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Redemption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int userId;
    private int staffId;
    private String photoPath;
    private int amount;
    private int creditsIssued;
    private String redemptionStatus;
    private long createTimestamp;
    private long modifiedTimestamp;
}
