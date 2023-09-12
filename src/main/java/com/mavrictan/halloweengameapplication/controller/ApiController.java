package com.mavrictan.halloweengameapplication.controller;

import com.mavrictan.halloweengameapplication.entity.Game;
import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.service.GameService;
import com.mavrictan.halloweengameapplication.service.PlayerService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "1. API")
@RequestMapping(path = "/api")
@AllArgsConstructor
public class ApiController {

    private PlayerService playerService;

    private GameService gameService;

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

    @Tag(name = "4. Redeem api")
    @RequestMapping(value = "/redeemCredits", method = RequestMethod.POST)
    public ResponseEntity<?> redeemCredits(@RequestParam Long playerId, @RequestParam Player.PowerUp powerUp, @RequestParam int quantity) {
        return playerService.purchasePowerUp(playerId, powerUp, quantity)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    @Tag(name = "4. Redeem api")
    @RequestMapping(value = "/redeemVoucher", method = RequestMethod.POST)
    public ResponseEntity<?> redeemVoucher(@RequestParam Long playerId, @RequestParam Player.PowerUp powerUp, @RequestParam int quantity) {
        return playerService.purchasePowerUp(playerId, powerUp, quantity)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
