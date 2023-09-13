package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.entity.Voucher;
import com.mavrictan.halloweengameapplication.repository.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoucherService {

    VoucherRepository voucherRepository;

    public Optional<Voucher> createVoucher(long playerId,
                                           double amount,
                                           Voucher.VoucherStatus status,
                                           Voucher.VoucherType type,
                                           String comments) {
        return Optional.of(voucherRepository.save(Voucher.builder()
                .voucherUuid(UUID.randomUUID().toString())
                .playerId(playerId)
                .amount(amount)
                .status(status)
                .type(type)
                .comments(comments)
                .build()));
    }

    public Optional<Voucher> createFirstTime(long playerId, double amount) {
        return this.createVoucher(playerId,
                amount,
                Voucher.VoucherStatus.AWARDED,
                Voucher.VoucherType.FIRST_TIME,
                "Awarded on player getting to 5000");
    }

    public Optional<Voucher> redeemVoucher() {
        return null;
    }
}
