package com.stockmarket.financialliteracy.node;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.List;

@NodeEntity
@Data
@Builder
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Index(unique = true)
    private String userName;
    private String fullName;
    private String email;
    private String avatarUrl;

    @Relationship(type = "WATCHLIST", direction = Relationship.INCOMING)
    private List<Company> watchlistCompanies;
}
