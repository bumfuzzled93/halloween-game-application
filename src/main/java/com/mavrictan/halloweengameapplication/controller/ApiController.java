package com.mavrictan.halloweengameapplication.controller;

import com.mavrictan.halloweengameapplication.entity.Game;
import com.mavrictan.halloweengameapplication.service.GameService;
import com.mavrictan.halloweengameapplication.service.PlayerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/getPlayerByUsername", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerByUsername(@RequestParam String username) {
        return playerService.getPlayerByUsername(username)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/getPlayerByMobileNumber", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerByMobileNumber(@RequestParam String mobileNumber) {
        return playerService.getPlayerByMobileNumber(mobileNumber)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/createPlayer", method = RequestMethod.POST)
    public ResponseEntity<?> createPlayer(@RequestParam String playerUsername,
                                          @RequestParam String password,
                                          @RequestParam String mobileNumber,
                                          @RequestParam String email) {
        return playerService.createPlayer(playerUsername, mobileNumber, email)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @RequestMapping(value = "/createGame", method = RequestMethod.POST)
    public ResponseEntity<Game> createGame(String photonId) {
        return ResponseEntity.ok(gameService.createGame(photonId));
    }

    @RequestMapping(value = "/startGame", method = RequestMethod.POST)
    public ResponseEntity<?> startGame(@RequestParam String photonId, @RequestParam List<Long> playerIds) throws Exception {
        return gameService.startGame(photonId, playerIds)
                .map(game -> new ResponseEntity<>(game, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @RequestMapping(value = "/endGame", method = RequestMethod.POST)
    public ResponseEntity<?> endGame(@RequestParam String photonId,
                                     @RequestParam List<Long> playerIds,
                                     @RequestParam int score) throws Exception {
        return gameService.endGame(photonId, playerIds, score)
                .map(game -> new ResponseEntity<>(game, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/purchaseWeapon", method = RequestMethod.POST)
    public ResponseEntity<?> purchaseWeapon(@RequestParam Long playerId, @RequestParam Long weaponId) throws Exception {
        return playerService.purchaseWeapon(playerId, weaponId)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//
//    @RequestMapping(value = "/equipLoadout", method = RequestMethod.POST)
//    public ResponseEntity<Player> equipLoadout(@RequestParam Long playerId, @RequestParam Long weaponId) {
//        return ResponseEntity.ok(playerRepository.findById(playerId).get());
//    }
//
//    @RequestMapping(value = "/upgradeWeapon", method = RequestMethod.POST)
//    public ResponseEntity<Player> upgradeWeapon(@RequestParam Long playerId, @RequestParam Long weaponId) {
//        return ResponseEntity.ok(playerRepository.findById(playerId).get());
//    }
}
