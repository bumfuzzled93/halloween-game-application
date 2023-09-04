package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.entity.Game;
import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.repository.GameRepository;
import com.mavrictan.halloweengameapplication.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class GameService {

    GameRepository gameRepository;

    PlayerRepository playerRepository;

    public Game createGame(String photonId) {
        return gameRepository.save(Game.builder().photonId(photonId).build());
    }

    public Optional<Game> startGame(String photonId, List<Long> playerIds) throws Exception {
        List<Player> playerList = playerRepository.findByIds(playerIds);

        return gameRepository.findByPhotonId(photonId)
                .map(game -> {
                    game.setPlayersList(playerList);
                    game.setStartTimeSeconds(LocalDateTime.now().getSecond());
                    return gameRepository.save(game);
                });
    }

    public Optional<Game> endGame(String photonId, List<Long> playerIds, int score) throws Exception {
        List<Player> playerList = playerRepository.findByIds(playerIds);

        for (Player p : playerList) {
            p.setCredits(p.getCredits() + score);
            playerRepository.save(p);
        }

        return gameRepository.findByPhotonId(photonId);
    }
}
