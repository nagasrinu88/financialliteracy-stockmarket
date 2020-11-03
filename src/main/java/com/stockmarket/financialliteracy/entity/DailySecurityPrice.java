package com.stockmarket.financialliteracy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@SequenceGenerator(name = "seq", sequenceName = "daily_security_price_id_seq", allocationSize = 1)
@Entity
@Table(name = "DAILY_SECURITY_PRICE", schema = "public")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailySecurityPrice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @JsonProperty("SYMBOL")
    @Column(name = "SYMBOL")
    private String symbol;

    @JsonProperty("SERIES")
    @Column(name = "SERIES")
    private String series;

    @JsonProperty("DATE1")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    @Column(name = "DATE1")
    private Date date1;

    @JsonProperty("PREV_CLOSE")
    @Column(name = "PREV_CLOSE")
    private Float prevClose;

    @JsonProperty("OPEN_PRICE")
    @Column(name = "OPEN_PRICE")
    private Float openPrice;

    @JsonProperty("HIGH_PRICE")
    @Column(name = "HIGH_PRICE")
    private Float highPrice;

    @JsonProperty("LOW_PRICE")
    @Column(name = "LOW_PRICE")
    private Float lowPrice;

    @JsonProperty("LAST_PRICE")
    @Column(name = "LAST_PRICE")
    private Float lastPrice;

    @JsonProperty("CLOSE_PRICE")
    @Column(name = "CLOSE_PRICE")
    private Float closePrice;

    @JsonProperty("AVG_PRICE")
    @Column(name = "AVG_PRICE")
    private Float avgPrice;

    @JsonProperty("TTL_TRD_QNTY")
    @Column(name = "TTL_TRD_QNTY")
    private Long totalTradedQuantity;

    @JsonProperty("DELIV_QTY")
    @Column(name = "DELIV_QTY")
    private Long deliveryQuantity;

    @JsonProperty("DELIV_PER")
    @Column(name = "DELIV_PER")
    private Float deliveryPercentage;

}