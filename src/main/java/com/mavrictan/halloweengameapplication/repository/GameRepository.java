package com.mavrictan.halloweengameapplication.repository;

import com.mavrictan.halloweengameapplication.entity.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "game", path = "game")
public interface GameRepository extends PagingAndSortingRepository<Game, Long>, CrudRepository<Game, Long> {

    Optional<Game> findByPhotonId(String photonId);
}
