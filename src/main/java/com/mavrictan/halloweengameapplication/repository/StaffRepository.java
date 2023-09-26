package com.mavrictan.halloweengameapplication.repository;


import com.mavrictan.halloweengameapplication.entity.Player;
import com.mavrictan.halloweengameapplication.entity.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "file", path = "file")
public interface StaffRepository extends PagingAndSortingRepository<Staff, Long>, CrudRepository<Staff, Long> {

    Optional<Staff> findByUsernameAndPassword(String username, String password);
}
