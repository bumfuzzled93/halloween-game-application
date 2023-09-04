package com.mavrictan.halloweengameapplication.repository;


import com.mavrictan.halloweengameapplication.entity.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "player", path = "player")
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long>, CrudRepository<Player, Long> {

    Optional<Player> findByUsername(String username);

    Optional<Player> findByMobileNumber(String mobileNumber);

    @Query("select p from Player p where p.id in :ids")
    List<Player> findByIds(@Param("ids") List<Long> postIdsList);

    boolean existsByMobileNumberOrEmail(String mobileNumber, String email);
}
