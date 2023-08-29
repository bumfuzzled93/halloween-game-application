package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.entity.PlayerWeapon;
import com.mavrictan.halloweengameapplication.entity.Weapon;
import com.mavrictan.halloweengameapplication.exception.DuplicatedEntityException;
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
        return playerRepository.findByUsername(username);
    }

    public Optional<Player> getPlayerByMobileNumber(String mobileNumber) {
        return playerRepository.findByMobileNumber(mobileNumber);
    }

    public List<Player> getPlayers(List<Long> playerIds) {
        return playerRepository.findByIds(playerIds);
    }

    public Optional<Player> updatePlayerScore(Long playerid, int score) {
        return playerRepository.findById(playerid)
                .map(player -> {
                    player.setCredits(player.getCredits() + score);
                    return playerRepository.save(player);
                });
    }

    public Optional<Player> purchaseWeapon(Long playerId, Long weaponId) throws Exception {
        // check that weapon exists
        Weapon w = weaponRepository.findById(weaponId).orElseThrow();
        Player p = playerRepository.findById(playerId).orElseThrow();

        // check that player does not already have this weapon pair
        if (playerWeaponRepository.existsPlayerWeaponsByPlayerAndWeapon(p, w)) {
            throw new Exception();
        }

        playerWeaponRepository.save(PlayerWeapon.builder()
                .weapon(w)
                .player(p)
                .build());

        return playerRepository.findById(playerId);
    }
}
