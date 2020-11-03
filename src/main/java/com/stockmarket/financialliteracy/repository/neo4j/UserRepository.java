package com.stockmarket.financialliteracy.repository.neo4j;

import com.stockmarket.financialliteracy.node.Company;
import com.stockmarket.financialliteracy.node.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query(value = "MATCH (c: Company) <- [:WATCHLIST] - (:User {email: $email}) RETURN c")
    List<Company> getAllWatchListCompanies(String email);

    User findByEmail(String email);
}
