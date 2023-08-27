package com.mavrictan.halloweengameapplication.controller;

import com.mavrictan.halloweengameapplication.entity.Game;
import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.repository.GameRepository;
import com.mavrictan.halloweengameapplication.repository.PlayerRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "1. API")
@RequestMapping(path = "/api")
@AllArgsConstructor
public class ApiController {

    private PlayerRepository playerRepository;

    private GameRepository gameRepository;

    @RequestMapping(value = "/getPlayer", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayerByUsername(@RequestParam String username) {
        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>(
                    "Please provide valid username or playerId",
                    HttpStatus.BAD_REQUEST);
        }

        return playerRepository.findByUsername(username).map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/createPlayer", method = RequestMethod.POST)
    public ResponseEntity<?> createPlayer(@RequestParam String playerUsername, @RequestParam String mobileNumber) {
        return new ResponseEntity<>(playerRepository.save(new Player(playerUsername, mobileNumber)), HttpStatus.OK);
    }

    @RequestMapping(value = "/createGame", method = RequestMethod.POST)
    public ResponseEntity<Game> createGame() {
        return ResponseEntity.ok(gameRepository.save(new Game()));
    }

    @RequestMapping(value = "/startGame", method = RequestMethod.POST)
    public ResponseEntity<?> startGame(@RequestParam Long gameId, @RequestParam List<Long> playerIds) {
        List<Player> gamePlayers = new ArrayList<>();

        playerIds.forEach(id -> {
            playerRepository.findById(id).map(gamePlayers::add);
        });

        return gameRepository.findById(gameId)
                .map(game -> {
                    game.setPlayersList(gamePlayers);
                    return new ResponseEntity<>(gameRepository.save(game), HttpStatus.OK);
                })
                .orElse(
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/endGame", method = RequestMethod.POST)
    public ResponseEntity<?> endGame(@RequestParam Long gameId, @RequestParam Long score, @RequestParam List<Long> playerBombUsed) {
        return gameRepository.findById(gameId)
                .map(game -> new ResponseEntity(game, HttpStatus.OK))
                .orElse(new ResponseEntity("Invalid gameId", HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/purchaseWeapon", method = RequestMethod.POST)
    public ResponseEntity<Player> purchaseItem(@RequestParam Long playerId, @RequestParam Long weaponId) {
        return ResponseEntity.ok(playerRepository.findById(playerId).get());
    }

    @RequestMapping(value = "/equipLoadout", method = RequestMethod.POST)
    public ResponseEntity<Player> equipLoadout(@RequestParam Long playerId, @RequestParam Long weaponId) {
        return ResponseEntity.ok(playerRepository.findById(playerId).get());
    }

    @RequestMapping(value = "/upgradeWeapon", method = RequestMethod.POST)
    public ResponseEntity<Player> upgradeWeapon(@RequestParam Long playerId, @RequestParam Long weaponId) {
        return ResponseEntity.ok(playerRepository.findById(playerId).get());
    }
}
