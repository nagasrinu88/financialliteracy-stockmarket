package com.stockmarket.financialliteracy.repository.postgres;

import com.stockmarket.financialliteracy.entity.ListedSecurity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ListedSecurityRepository extends PagingAndSortingRepository<ListedSecurity, String> {
    @Query("SELECT v from ListedSecurity v where v.symbol like %:searchStr% or v.companyName like %:searchStr%")
    Page<ListedSecurity> findAllBySearchString(String searchStr, Pageable pageable);
}
