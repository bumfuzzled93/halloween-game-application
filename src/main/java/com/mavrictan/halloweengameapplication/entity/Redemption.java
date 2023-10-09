package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

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
    private LocalDateTime createTimestamp;
    private LocalDateTime modifiedTimestamp;

    @Transient
    @With
    private String fileDownloadUrl;
}
