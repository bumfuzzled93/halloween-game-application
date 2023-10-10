package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.config.ApplicationConfiguration;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RedemptionService {

    RedemptionRepository redemptionRepository;

    PlayerService playerService;

    VoucherService voucherService;

    FileService fileService;

    private ApplicationConfiguration applicationConfiguration;

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

            return redemptionRepository.redemptionByDate(before.toString(), after.toString()).stream()
                    .map(redemption -> redemption.withFileDownloadUrl(applicationConfiguration.getURL_PREFIX() + redemption.getImageFileUuid()))
                    .collect(Collectors.toList());
        } catch (ParseException e) {
            throw new BadRequestException("invalid date: " + date + ". Please use yyyy-MM-dd format.");
        }
    }

    public String getRedemptionsCSV(String date) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s, %s, %s, %s, %s, %s, %s\n",
                "redemption_id",
                "staff_id",
                "player_id",
                "credits_issued",
                "image_file_uuid",
                "create_timestamp",
                "downloadURL"
                ));

        this.getRedemptions(date).forEach(redemption ->
                sb.append(String.format("%d, %d, %d, %d, %s, %s, %s\n",
                        redemption.getId(),
                        redemption.getStaffId(),
                        redemption.getPlayerId(),
                        redemption.getCreditsIssued(),
                        redemption.getImageFileUuid(),
                        redemption.getCreateTimestamp().toString(),
                        redemption.getFileDownloadUrl())
                ));

        return sb.toString();
    }
}
