package com.wcreators.ersstrategy.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@ToString
@Builder
public class CupRatePoint {
    private String major;
    private String minor;
    private double high;
    private double low;
    private double open;
    private double close;
    private Date start;
    private Date end;

    public void addPrice(double price, Date current) {
        close = price;
        end = current;
        high = max(high, close);
        low = min(low, close);
    }

    public Decimal getDecimalClose() {
        return Decimal.valueOf(close);
    }

    public Decimal getDecimalHigh() {
        return Decimal.valueOf(high);
    }

    public Decimal getDecimalLow() {
        return Decimal.valueOf(low);
    }
}
