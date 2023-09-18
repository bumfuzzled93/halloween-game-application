package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.entity.File;
import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.entity.Redemption;
import com.mavrictan.halloweengameapplication.exception.BadRequestException;
import com.mavrictan.halloweengameapplication.repository.RedemptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RedemptionService {

    RedemptionRepository redemptionRepository;

    PlayerService playerService;

    VoucherService voucherService;


    FileService fileService;

    public Optional<Redemption> awardPointsToPlayer(String playerUsername, long staffId, int creditsIssued, MultipartFile imageData) throws IOException {
        if (creditsIssued <= 0) {
            throw new BadRequestException("Credits issued cannot be negative");
        }

        Player player = playerService.updatePlayerCredits(playerUsername, creditsIssued).orElseThrow(
                () -> new BadRequestException("Failed to update player credits"));

        File storeFile = fileService.store(imageData);

        return Optional.of(redemptionRepository.save(Redemption.builder()
                .playerId(player.getId())
                .staffId(staffId)
                .creditsIssued(creditsIssued)
                .imageFileUuid(storeFile.getUuid())
                .build()));
    }
}
