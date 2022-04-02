package com.wcreators.ersstrategy.service.indicators.ma;


import java.util.ArrayList;
import java.util.List;

import com.wcreators.ersstrategy.service.storage.StorageIndicator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SMA extends StorageIndicator<Double, Double> {

    @Getter
    private final int period;
    private Double sum = 0D;
    private final List<Double> fromNValues = new ArrayList<>();

    @Override
    public Double calculate(Double value) {
        if (size() < period) {
            fromNValues.add(value);
            sum += value;
            return sum / (points.size() + 1);
        } else {
            sum = null;
        }

        Double fromNValue = fromNValues.get(0);
        fromNValues.remove(0);
        fromNValues.add(value);

        Double prevValue = lastAdded().get();
        Double delimiter = Double.valueOf(period);
        return prevValue - fromNValue / delimiter + value / delimiter;
    }
}
