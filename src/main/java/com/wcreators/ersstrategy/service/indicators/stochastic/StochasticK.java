package com.wcreators.ersstrategy.service.indicators.stochastic;

import com.wcreators.ersstrategy.model.Decimal;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StochasticK extends StorageIndicator<Decimal, Decimal> {

    @Getter
    private final int period;
    private final List<Decimal> lastNPoints = new ArrayList<>();

    @Override
    public Decimal calculate(Decimal value) {
        if (lastNPoints.size() < period) {
            lastNPoints.add(value);
        } else {
            lastNPoints.add(value);
            lastNPoints.remove(0);
        }
        if (isEmpty()) {
            return calculate(value, value, value);
        }
        Optional<Decimal> optionalHighest = lastNPoints.stream().max(Decimal::compareTo);
        Optional<Decimal> optionalLowest = lastNPoints.stream().min(Decimal::compareTo);
        if (optionalHighest.isEmpty() || optionalLowest.isEmpty()) {
            return calculate(value, value, value);
        }
        Decimal highest = optionalHighest.get();
        Decimal lowest = optionalLowest.get();
        return calculate(value, highest, lowest);
    }

    private Decimal calculate(Decimal value, Decimal highest, Decimal lowest) {
        Decimal divider = highest.minus(lowest);
        if (divider.compareTo(Decimal.ZERO) == 0) {
            return Decimal.ZERO;
        }

        return value
                .minus(lowest)
                .divide(divider)
                .multiply(Decimal.HUNDRED);
    }
}
