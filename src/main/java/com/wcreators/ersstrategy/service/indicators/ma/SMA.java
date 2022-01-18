package com.wcreators.ersstrategy.service.indicators.ma;


import com.wcreators.ersstrategy.model.Decimal;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SMA extends StorageIndicator<Decimal, Decimal> {

    @Getter
    private final int period;
    private Decimal sum = Decimal.ZERO;
    private final List<Decimal> fromNValues = new ArrayList<>();

    @Override
    public Decimal calculate(Decimal value) {
        if (size() < period) {
            fromNValues.add(value);
            sum = sum.plus(value);
            return sum.divide(Decimal.valueOf(points.size() + 1));
        } else {
            sum = null;
        }

        Decimal fromNValue = fromNValues.get(0);
        fromNValues.remove(0);
        fromNValues.add(value);

        Decimal prevValue = lastAdded();
        Decimal delimiter = Decimal.valueOf(period);
        return prevValue
                .minus(fromNValue.divide(delimiter))
                .plus(value.divide(delimiter));
    }
}
