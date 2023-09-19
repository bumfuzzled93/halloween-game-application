package com.mavrictan.halloweengameapplication.repository;


import com.mavrictan.halloweengameapplication.entity.Voucher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "voucher", path = "voucher")
public interface VoucherRepository extends PagingAndSortingRepository<Voucher, Long>, CrudRepository<Voucher, Long> {
    List<Voucher> findVoucherByPlayerId(long playerId);

    boolean existsVoucherByPlayerIdAndType(long playerId, Voucher.VoucherType type);
}
