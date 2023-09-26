package com.mavrictan.halloweengameapplication.service;

import com.mavrictan.halloweengameapplication.entity.Game;
import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.exception.BadRequestException;
import com.mavrictan.halloweengameapplication.repository.GameRepository;
import com.mavrictan.halloweengameapplication.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

        if (playerIds.size() != playerList.size()) {
            Set<Long> players = playerList.stream().map(Player::getId).collect(Collectors.toSet());
            List<Long> missingPlayerIds = playerIds.stream()
                    .filter(id -> !players.contains(id)).toList();

            throw new BadRequestException(String.format(
                    "The following playerIds do not exist %s", missingPlayerIds));
        }

        return Optional.ofNullable(gameRepository.findByPhotonId(photonId)
                .map(game -> {
                    game.setPlayersList(playerList);
                    game.setStartTimeSeconds(LocalDateTime.now().getSecond());
                    return gameRepository.save(game);
                }).orElseThrow(() ->
                        new Exception(String.format("No such game photon id : %s", photonId))));
    }

    public Optional<Game> endGame(String photonId, List<Long> playerIds, int score) throws Exception {
        List<Player> playerList = playerRepository.findByIds(playerIds);

        for (Player p : playerList) {
            p.setScore(p.getScore() + score);
            playerRepository.save(p);
        }

        return gameRepository.findByPhotonId(photonId);
    }
}
