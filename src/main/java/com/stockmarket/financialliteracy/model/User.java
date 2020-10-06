package com.stockmarket.financialliteracy.model;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
@Data
@Builder
public class User {
    @Id
    private Long id;
    private String userName;
    private String fullName;

    @Relationship(type = "WATCHLIST", direction = Relationship.INCOMING)
    private List<Company> watchlistCompanies;
}
