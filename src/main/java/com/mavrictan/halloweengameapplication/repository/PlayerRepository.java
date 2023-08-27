package com.mavrictan.halloweengameapplication.repository;


import com.mavrictan.halloweengameapplication.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "player", path = "player")
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long>, CrudRepository<Player, Long> {

    Optional<Player> findByUsername(String username);
}
