package com.mavrictan.halloweengameapplication.repository;

import com.mavrictan.halloweengameapplication.entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(collectionResourceRel = "file", path = "file")
public interface FileRepository extends PagingAndSortingRepository<File, Long>, CrudRepository<File, Long> {
    Optional<File> findFileByUuid(String uuid);
}
