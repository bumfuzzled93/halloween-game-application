package com.mavrictan.halloweengameapplication.controller;

import com.mavrictan.halloweengameapplication.entity.Game;
import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.entity.Weapon;
import com.mavrictan.halloweengameapplication.service.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "1. API")
@RequestMapping(path = "/api")
@AllArgsConstructor
public class ApiController {

    private PlayerService playerService;

    private GameService gameService;

    private RedemptionService redemptionService;

    private FileService fileService;

    private StaffService staffService;

    private VoucherService voucherService;

    @Tag(name = "2. Player api")
    @RequestMapping(value = "/getPlayerByUsername", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerByUsername(@RequestParam String username) {
        return playerService.getPlayerByUsername(username)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Tag(name = "2. Player api")
    @RequestMapping(value = "/getPlayerByMobileNumber", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerByMobileNumber(@RequestParam String mobileNumber) {
        return playerService.getPlayerByMobileNumber(mobileNumber)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Tag(name = "2. Player api")
    @RequestMapping(value = "/getPlayerByEmail", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerByEmail(@RequestParam String email) {
        return playerService.getPlayerByEmail(email)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Tag(name = "2. Player api")
    @RequestMapping(value = "/createPlayer", method = RequestMethod.POST)
    public ResponseEntity<?> createPlayer(@RequestParam String playerUsername,
                                          @RequestParam String password,
                                          @RequestParam String mobileNumber,
                                          @RequestParam String email) {
        return playerService.createPlayer(playerUsername, password, mobileNumber, email)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Tag(name = "2. Player api")
    @RequestMapping(value = "/validatePlayer", method = RequestMethod.POST)
    public ResponseEntity<?> validatePlayer(@RequestParam String playerUsername,
                                            @RequestParam String md5password) {
        return playerService.login(playerUsername, md5password)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @Tag(name = "2. Player api")
    @RequestMapping(value = "/resetPlayerPassword", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> resetPlayerPassword(@RequestParam long playerId,
                                                 @RequestParam String newPassword,
                                                 @Parameter(name = "otp",
                                                         description = "One time Password - any string of length 6 for now",
                                                         example = "123456")
                                                 @RequestParam String otp) {
        playerService.resetPlayerPassword(playerId, newPassword, otp);
        return ResponseEntity.ok("Password has been reset");
    }

    @Tag(name = "2. Player api")
    @RequestMapping(value = "/getPlayerWeapons", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerWeapons(@RequestParam long playerId) {
        return new ResponseEntity<>(playerService.getPlayerWeapons(playerId), HttpStatus.OK);
    }

    @Tag(name = "4. Store api")
    @RequestMapping(value = "/getPlayerWeaponsSimplified", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getPlayerWeaponsSimplified(@RequestParam Long playerId) throws Exception {
        List<Weapon> weapons = playerService.getPlayerWeapons(playerId);

        return ResponseEntity.ok(weapons.stream()
                .map(weapon -> String.format("%d,%d,%s", weapon.getId(), weapon.getUpgrade(), weapon.getWeaponName()))
                .collect(Collectors.joining(";")));
    }


    @Tag(name = "2. Player api")
    @RequestMapping(value = "/equipWeapon", method = RequestMethod.POST)
    public ResponseEntity<?> equipWeapon(@RequestParam long playerId,
                                         @RequestParam long weaponId) {
        return playerService.equipWeapon(playerId, weaponId)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Tag(name = "2. Player api")
    @RequestMapping(value = "/getPlayerVouchers", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerVouchers(@RequestParam long playerId) {
        return new ResponseEntity<>(playerService.getPlayerVouchers(playerId), HttpStatus.OK);
    }

    @Tag(name = "3. Game api")
    @RequestMapping(value = "/createGame", method = RequestMethod.POST)
    public ResponseEntity<Game> createGame(String photonId) {
        return ResponseEntity.ok(gameService.createGame(photonId));
    }

    @Tag(name = "3. Game api")
    @RequestMapping(value = "/startGame", method = RequestMethod.POST)
    public ResponseEntity<?> startGame(@RequestParam String photonId, @RequestParam List<Long> playerIds) throws Exception {
        return gameService.startGame(photonId, playerIds)
                .map(game -> new ResponseEntity<>(game, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Tag(name = "3. Game api")
    @RequestMapping(value = "/endGame", method = RequestMethod.POST)
    public ResponseEntity<?> endGame(@RequestParam String photonId,
                                     @RequestParam List<Long> playerIds,
                                     @RequestParam int score) throws Exception {
        return gameService.endGame(photonId, playerIds, score)
                .map(game -> new ResponseEntity<>(game, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Tag(name = "4. Store api")
    @RequestMapping(value = "/purchaseWeapon", method = RequestMethod.POST)
    public ResponseEntity<?> purchaseWeapon(@RequestParam Long playerId, @RequestParam Long weaponId) throws Exception {
        return playerService.purchaseWeapon(playerId, weaponId)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Tag(name = "4. Store api")
    @RequestMapping(value = "/getPurchaseableWeapons", method = RequestMethod.GET)
    public ResponseEntity<?> getPurchaseableWeapons(@RequestParam Long playerId) throws Exception {
        List<Weapon> purchaseableWeapons = playerService.getPurchaseableWeapons(playerId);

        if (purchaseableWeapons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(purchaseableWeapons, HttpStatus.OK);
    }

    @Tag(name = "4. Store api")
    @RequestMapping(value = "/getPurchaseableWeaponsSimplified", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> getPurchaseableWeaponsSimplified(@RequestParam Long playerId) throws Exception {
        List<Weapon> purchaseableWeapons = playerService.getPurchaseableWeapons(playerId);

        if (purchaseableWeapons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(purchaseableWeapons.stream()
                    .map(weapon -> String.format("%d,%d,%s",weapon.getId(), weapon.getUpgrade(), weapon.getWeaponName()))
                    .collect(Collectors.joining(";")),
                HttpStatus.OK);
    }


    @Tag(name = "4. Store api")
    @RequestMapping(value = "/upgradeWeapon", method = RequestMethod.POST)
    public ResponseEntity<?> upgradeWeapon(@RequestParam Long playerId, @RequestParam Long weaponId) {
        return playerService.upgradeWeapon(playerId, weaponId)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Tag(name = "4. Store api")
    @RequestMapping(value = "/purchasePowerUp", method = RequestMethod.POST)
    public ResponseEntity<?> purchasePowerUp(@RequestParam Long playerId, @RequestParam Player.PowerUp powerUp, @RequestParam int quantity) {
        return playerService.purchasePowerUp(playerId, powerUp, quantity)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @SneakyThrows
    @Tag(name = "4. Redeem api")
    @RequestMapping(value = "/redeemCredits", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<?> redeemCredits(@RequestParam long playerId,
                                           @RequestParam long staffId,
                                           @RequestParam int creditsIssued,
                                           @RequestParam MultipartFile receiptImage) {
        return redemptionService.awardPointsToPlayer(playerId, staffId, creditsIssued, receiptImage)
                .map(redemption -> new ResponseEntity<>(redemption, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Tag(name = "4. Redeem api")
    @RequestMapping(value = "/redeemVoucher", method = RequestMethod.POST)
    public ResponseEntity<?> redeemVoucher(@RequestParam String voucherUuid) {
        return voucherService.redeemVoucher(voucherUuid)
                .map(voucher -> new ResponseEntity<>(voucher, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Tag(name = "4. Redeem api")
    @GetMapping("/files/{uuid}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String uuid) {
        return fileService.getFile(uuid)
                .map(file -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFileName() + "\"")
                        .body(file.getData()))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @SneakyThrows
    @Tag(name = "4. Redeem api")
    @GetMapping("/getRedemptionsByDate")
    public ResponseEntity<?> getRedemptionsByDate(@RequestParam String date) {
        return new ResponseEntity<>(redemptionService.getRedemptions(date), HttpStatus.OK);
    }

    @Tag(name = "2. Player api")
    @RequestMapping(value = "/validateStaff", method = RequestMethod.POST)
    public ResponseEntity<?> validateStaff(@RequestParam String merchantUserName,
                                            @RequestParam String md5password) {
        return staffService.login(merchantUserName, md5password)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }


}
