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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PlayerService {
    PlayerRepository playerRepository;

    PlayerWeaponRepository playerWeaponRepository;

    WeaponRepository weaponRepository;

    public Optional<Player> createPlayer(String username, String mobileNumber, String email) {
        if (playerRepository.existsByMobileNumberOrEmail(mobileNumber, email)) {
            throw new DuplicatedEntityException("Player", "mobileNumber or email");
        }

        return Optional.of(playerRepository.save(Player.builder()
                .username(username)
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
}
