package com.stockmarket.financialliteracy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "LISTED_SECURITY", schema = "public")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListedSecurity {

    @Id
    @JsonProperty("SYMBOL")
    @Column(name = "SYMBOL")
    private String symbol;

    @JsonProperty("COMPANY_NAME")
    @Column(name = "COMPANY_NAME")
    private String companyName;

    @JsonProperty("SERIES")
    @Column(name = "SERIES")
    private String series;

    @JsonProperty("EXCHANGE")
    @Column(name = "EXCHANGE")
    private String exchange;
}
