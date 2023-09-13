package com.mavrictan.halloweengameapplication.repository;

import com.mavrictan.halloweengameapplication.entity.Redemption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "redemption", path = "redemption")
public interface RedemptionRepository extends PagingAndSortingRepository<Redemption, Long>, CrudRepository<Redemption, Long> {
}