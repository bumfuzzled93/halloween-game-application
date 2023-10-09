package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.entity.File;
import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.entity.Redemption;
import com.mavrictan.halloweengameapplication.exception.BadRequestException;
import com.mavrictan.halloweengameapplication.repository.RedemptionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RedemptionService {

    RedemptionRepository redemptionRepository;

    PlayerService playerService;

    VoucherService voucherService;

    FileService fileService;

    @Transactional
    public Optional<Redemption> awardPointsToPlayer(long playerId, long staffId, int creditsIssued, MultipartFile imageData) throws IOException {
        if (creditsIssued <= 0) {
            throw new BadRequestException("Credits issued cannot be negative");
        }

        Player player = playerService.updatePlayerCredits(playerId, creditsIssued).orElseThrow(
                () -> new BadRequestException("Failed to update player credits"));

        File storeFile = fileService.store(imageData);

        return Optional.of(redemptionRepository.save(Redemption.builder()
                .playerId(player.getId())
                .staffId(staffId)
                .creditsIssued(creditsIssued)
                .imageFileUuid(storeFile.getUuid())
                .build()));
    }

    public List<Redemption> getRedemptions(String date) throws IOException {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Timestamp before = new Timestamp(dateFormat.parse(date).getTime() - (8 * 60 * 60 * 1000));
            Timestamp after = new Timestamp(dateFormat.parse(date).getTime() + (16 * 60 * 60 * 1000));

            return redemptionRepository.redemptionByDate(before.toString(), after.toString());
        } catch (ParseException e) {
            throw new BadRequestException("invalid date: " + date + ". Please use yyyy-MM-dd format.");
        }
    }
}
