package com.mavrictan.halloweengameapplication.repository;

import com.mavrictan.halloweengameapplication.entity.Redemption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "redemption", path = "redemption")
public interface RedemptionRepository extends PagingAndSortingRepository<Redemption, Long>, CrudRepository<Redemption, Long> {

    @Query(value = "select * from redemption r " +
            "where create_timestamp > :before AND create_timestamp < :after ",
            nativeQuery = true)
    List<Redemption> redemptionByDate(String before, String after);
}