package com.mavrictan.halloweengameapplication.repository;


import com.mavrictan.halloweengameapplication.entity.Voucher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "voucher", path = "voucher")
public interface VoucherRepository extends PagingAndSortingRepository<Voucher, Long>, CrudRepository<Voucher, Long> {
    List<Voucher> findVoucherByPlayerId(long playerId);

    Optional<Voucher> findVoucherByVoucherUuid(String uuid);

    boolean existsVoucherByPlayerIdAndType(long playerId, Voucher.VoucherType type);
}
