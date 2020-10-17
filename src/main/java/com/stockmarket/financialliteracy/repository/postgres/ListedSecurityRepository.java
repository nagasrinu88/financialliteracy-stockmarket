package com.stockmarket.financialliteracy.repository.postgres;

import com.stockmarket.financialliteracy.model.ListedSecurity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ListedSecurityRepository extends CrudRepository<ListedSecurity, String> {
    @Query(nativeQuery = true, value = "SELECT * from public.LISTED_SECURITY where symbol like %:searchStr% or company_name like %:searchStr%")
    List<ListedSecurity> findAllBySearchString(String searchStr);
}
