package com.stockmarket.financialliteracy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@SequenceGenerator(name = "seq", sequenceName = "price_id_seq", allocationSize = 1)
@Entity
@Table(name = "price", schema = "public")
public class Price {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "series")
    private String series;

    @Column(name = "open")
    private Float open;

    @Column(name = "high")
    private Float high;

    @Column(name = "lower")
    private Float lower;

    @Column(name = "close")
    private Float close;

    @Column(name = "prevclose")
    private Float prevClose;

    @Column(name = "TOTTRDQTY")
    private Float totalTradeQuantity;

    @Column(name = "TOTTRDVAL")
    private Float totalTradeValue;

    @Column(name = "timestamp")
    private LocalDate timeStamp;

    @Column(name = "totaltrades")
    private Integer totalTrades;

    @Column(name = "isin")
    private String isin;

}