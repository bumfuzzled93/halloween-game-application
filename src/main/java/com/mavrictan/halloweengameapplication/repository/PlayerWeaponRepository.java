package com.mavrictan.halloweengameapplication.repository;


import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.entity.PlayerWeapon;
import com.mavrictan.halloweengameapplication.entity.Weapon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "playerWeapon", path = "playerWeapon")
public interface PlayerWeaponRepository extends PagingAndSortingRepository<PlayerWeapon, Long>, CrudRepository<PlayerWeapon, Long> {

    boolean existsPlayerWeaponsByPlayerAndWeapon(Player player, Weapon weapon);

    Optional<PlayerWeapon> findByPlayerIdAndWeaponId(long playerId, long weaponId);
}
