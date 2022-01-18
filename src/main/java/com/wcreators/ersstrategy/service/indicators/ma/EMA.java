package com.wcreators.ersstrategy.service.indicators.ma;


import com.wcreators.ersstrategy.model.Decimal;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;
import lombok.Getter;

public class EMA extends StorageIndicator<Decimal, Decimal> {

    @Getter
    private final int period;
    private final Decimal multiplier;

    public EMA(int period) {
        this.period = period;
        // 2 / (period + 1)
        this.multiplier = Decimal.TWO.divide(Decimal.valueOf(period).plus(Decimal.ONE));
    }

    @Override
    public Decimal calculate(Decimal value) {
        if (isEmpty()) {
            return value;
        }
        return Decimal.valueOf(multiplier.doubleValue() * value.doubleValue() + (1 - multiplier.doubleValue()) * lastAdded().doubleValue());
    }
}
