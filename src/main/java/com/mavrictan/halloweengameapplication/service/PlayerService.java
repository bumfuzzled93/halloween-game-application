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
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PlayerService {
    PlayerRepository playerRepository;

    PlayerWeaponRepository playerWeaponRepository;

    WeaponRepository weaponRepository;

    VoucherService voucherService;

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

    @SneakyThrows
    public Optional<Player> login(String playerUsername, String md5Password) {
        if (!md5Password.matches("[A-F0-9]{32}")) {
            throw new BadRequestException("Invalid password");
        }

        return Optional.ofNullable(playerRepository.findByUsernameAndPassword(playerUsername, md5Password)
                .orElseThrow(NoSuchPlayerException::new));
    }

    @SneakyThrows
    public Optional<Player> purchasePowerUp(long playerId, Player.PowerUp powerUp, int qty) {
        if (qty <= 0) {
            throw new BadRequestException("Invalid quantity - must be more than zero");
        }

        Player p = playerRepository.findById(playerId)
                .orElseThrow(NoSuchPlayerException::new);

        if (powerUp == Player.PowerUp.DRONE) {
            p.setPowerupDrone(p.getPowerupDrone() + qty);
        }
        if (powerUp == Player.PowerUp.AMMO_BOX) {
            p.setPowerupAmmoBox(p.getPowerupAmmoBox() + qty);
        }
        if (powerUp == Player.PowerUp.BONUS) {
            p.setPowerupBonus(p.getPowerupBonus() + qty);
        }

        return Optional.of(playerRepository.save(p));
    }

    public Optional<Player> updatePlayerCredits(String playerUsername, int creditsIssued) {
        Player player = playerRepository.findByUsername(playerUsername).orElseThrow(() -> new BadRequestException("No such player"));

        player.setCredits(player.getCredits() + creditsIssued);

        // if credits more than 5000 award voucher
        if (player.getCredits() >= 5000) {
            voucherService.createFirstTime(player.getId(), 10);
        }

        return Optional.of(playerRepository.save(player));
    }

    public List<Weapon> getPurchaseableWeapons(Long playerId) {
        Set<Long> purchasedPlayerWeaponIds = playerWeaponRepository.findByPlayerId(playerId).stream()
                .map(pw -> pw.getWeapon().getId())
                .collect(Collectors.toSet());

        return weaponRepository.findWeaponByUpgradeEquals(1).stream()
                .filter(w -> !purchasedPlayerWeaponIds.contains(w.getId()))
                .collect(Collectors.toList());
    }

    public Optional<Player> equipWeapon(long playerId, long weaponId) {
        Player p = playerRepository.findById(playerId).orElseThrow(NoSuchPlayerException::new);

        System.out.println("checking weapons");
        List<PlayerWeapon> playerWeapons = p.getPurchasedWeapons().stream()
                .filter(pw -> pw.getWeapon().getId() == weaponId)
                .toList();

        if (playerWeapons.isEmpty()) {
            throw new BadRequestException("Player does not own this weapon");
        }

        p.getPurchasedWeapons().forEach(pw -> pw.setEquipped(false));
        playerWeapons.get(0).setEquipped(true);

        playerRepository.save(p);

        return playerRepository.findById(playerId);
    }
}
