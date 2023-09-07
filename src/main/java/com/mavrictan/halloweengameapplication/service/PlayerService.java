package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.entity.PlayerWeapon;
import com.mavrictan.halloweengameapplication.entity.Weapon;
import com.mavrictan.halloweengameapplication.exception.BadRequestException;
import com.mavrictan.halloweengameapplication.exception.DuplicatedEntityException;
import com.mavrictan.halloweengameapplication.exception.NoSuchPlayerException;
import com.mavrictan.halloweengameapplication.exception.NoSuchWeaponException;
import com.mavrictan.halloweengameapplication.repository.PlayerRepository;
import com.mavrictan.halloweengameapplication.repository.PlayerWeaponRepository;
import com.mavrictan.halloweengameapplication.repository.WeaponRepository;
import com.mavrictan.halloweengameapplication.util.Security;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PlayerService {
    PlayerRepository playerRepository;

    PlayerWeaponRepository playerWeaponRepository;

    WeaponRepository weaponRepository;

    @SneakyThrows
    public Optional<Player> createPlayer(String username, String password, String mobileNumber, String email) {
        if (playerRepository.existsByMobileNumberOrEmail(mobileNumber, email)) {
            throw new DuplicatedEntityException("Player", "mobileNumber or email");
        }

        return Optional.of(playerRepository.save(Player.builder()
                .username(username)
                .password(Security.hashPassword(password))
                .mobileNumber(mobileNumber)
                .email(email)
                .build()));
    }

    public Optional<Player> getPlayerByUsername(String username) {

        return Optional.ofNullable(playerRepository.findByUsername(username)
                .orElseThrow(NoSuchPlayerException::new));
    }

    public Optional<Player> getPlayerByMobileNumber(String mobileNumber) {
        return Optional.ofNullable(playerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(NoSuchPlayerException::new));
    }

    public Optional<Player> getPlayerByEmail(String email) {
        return Optional.ofNullable(playerRepository.findByEmail(email)
                .orElseThrow(NoSuchPlayerException::new));
    }

    public List<Player> getPlayers(List<Long> playerIds) {
        return playerRepository.findByIds(playerIds);
    }

    public Optional<Player> updatePlayerScore(Long playerid, int score) {
        return Optional.ofNullable(playerRepository.findById(playerid)
                .map(player -> {
                    player.setCredits(player.getCredits() + score);
                    return playerRepository.save(player);
                }).orElseThrow(NoSuchPlayerException::new));
    }

    public Optional<Player> purchaseWeapon(Long playerId, Long weaponId) throws Exception {
        // check that weapon exists
        Weapon w = weaponRepository.findById(weaponId)
                .orElseThrow(NoSuchWeaponException::new);
        Player p = playerRepository.findById(playerId)
                .orElseThrow(NoSuchPlayerException::new);

        // check that player does not already have this weapon pair
        if (playerWeaponRepository.existsPlayerWeaponsByPlayerAndWeapon(p, w)) {
            throw new BadRequestException(String.format("Player %s already owned weapon %s",
                    p.getUsername(), w.getWeaponName()));
        }

        playerWeaponRepository.save(PlayerWeapon.builder()
                .weapon(w)
                .player(p)
                .build());

        return playerRepository.findById(playerId);
    }

    public Optional<Player> upgradeWeapon(Long playerId, Long weaponId) {
        PlayerWeapon pw = playerWeaponRepository.findByPlayerIdAndWeaponId(playerId, weaponId)
                .orElseThrow(() -> new BadRequestException(
                        String.format("No combination for playerid : %d and weaponId: %d", playerId, weaponId)));

        // check that weapon exists
        Weapon w = weaponRepository.findById(weaponId)
                .orElseThrow(NoSuchWeaponException::new);

        // validate upgrade level not more than 5
        pw.setWeapon(weaponRepository
                .findByWeaponNameAndUpgrade(w.getWeaponName(), w.getUpgrade() + 1)
                .orElseThrow(() -> new BadRequestException("Weapon cannot be upgraded further")));

        playerWeaponRepository.save(pw);

        return playerRepository.findById(playerId);
    }

    @SneakyThrows
    public void resetPlayerPassword(long playerId, String newPassword, String otp) {
        if (otp.length() != 6) {
            throw new BadRequestException("Invalid otp");
        }

        Player p = playerRepository.findById(playerId)
                .orElseThrow(NoSuchPlayerException::new);

        p.setPassword(Security.hashPassword(newPassword));

        playerRepository.save(p);
    }
}
