package com.mavrictan.halloweengameapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String voucherUuid;

    private long playerId;

    private double amount;

    private String comments;

    @Enumerated(EnumType.STRING)
    private VoucherStatus status;

    @Enumerated(EnumType.STRING)
    private VoucherType type;

    public enum VoucherStatus {
        AWARDED,
        USED,
        DISABLED
    }

    public enum VoucherType {
        FIRST_TIME,
        NORMAL
    }
}
