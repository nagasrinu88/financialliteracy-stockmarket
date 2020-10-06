package com.stockmarket.financialliteracy.model;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Objects;

@NodeEntity
@Data
@Builder
public class Company {
    @Id
    private Long id;

    private String name;
    private String symbol;
    private String exchange;
    private Double mCapInLakh;
    private String cap;

    public static class CompanyBuilder {
        public CompanyBuilder mCapInLakh(Double mCapInLakh) {
            this.mCapInLakh = mCapInLakh;
            String cap = "";
            if (Objects.nonNull(this.mCapInLakh)) {
                long mCapInCrore = this.mCapInLakh.longValue() / 100;
                if (mCapInCrore < 5000) {
                    cap = Cap.SMALL_CAP.name();
                } else if (mCapInCrore < 20000 && mCapInCrore > 5000) {
                    cap = Cap.MID_CAP.name();
                } else {
                    cap = Cap.LARGE_CAP.name();
                }
            }
            this.cap = cap;
            return this;
        }
    }
}

enum Cap {
    LARGE_CAP, MID_CAP, SMALL_CAP
}
