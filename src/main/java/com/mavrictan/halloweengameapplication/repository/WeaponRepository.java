package com.mavrictan.halloweengameapplication.repository;

import com.mavrictan.halloweengameapplication.entity.Weapon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "weapon", path = "weapon")
public interface WeaponRepository extends PagingAndSortingRepository<Weapon, Long>, CrudRepository<Weapon, Long> {
}

