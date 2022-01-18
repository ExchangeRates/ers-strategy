package com.wcreators.ersstrategy.service.indicators.ma;

public class SMMA extends EMA {
    public SMMA(int period) {
        super(period - 1);
    }
}
