package com.wcreators.ersstrategy.service.indicators.stochastic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wcreators.ersstrategy.dto.stochastic.StochasticRequestDTO;
import com.wcreators.ersstrategy.dto.stochastic.StochasticResponseDTO;
import com.wcreators.ersstrategy.feign.StochasticFeignClient;
import com.wcreators.ersstrategy.model.Decimal;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StochasticK extends StorageIndicator<Decimal, Decimal> {

    @Getter
    private final int period;
    private final List<Decimal> lastNPoints = new ArrayList<>();
    private List<Double> lastPoints = new ArrayList<>();
    private final StochasticFeignClient feignClient;
    private Double prevKPoint;

    @Override
    public Decimal calculate(Decimal value) {
        StochasticRequestDTO request = StochasticRequestDTO.builder()
          .period(2)
          .value(value.doubleValue())
          .prevKPoint(prevKPoint)
          .lastPoints(lastPoints)
          .build();
        System.out.println(request);
        StochasticResponseDTO response = feignClient.calculate(request);
        System.out.println(response);
        lastPoints = response.getLastPoints();
        prevKPoint = response.getPointK();
        if (lastNPoints.size() < period) {
            lastNPoints.add(value);
        } else {
            lastNPoints.add(value);
            lastNPoints.remove(0);
        }
        if (isEmpty()) {
            Decimal res = calculate(value, value, value);
            
          System.out.println("K resp " + response.getPointK() + " calc " + res.doubleValue());
          return res;
        }
        Optional<Decimal> optionalHighest = lastNPoints.stream().max(Decimal::compareTo);
        Optional<Decimal> optionalLowest = lastNPoints.stream().min(Decimal::compareTo);
        if (optionalHighest.isEmpty() || optionalLowest.isEmpty()) {
            Decimal res = calculate(value, value, value);
          System.out.println("K resp " + response.getPointK() + " calc " + res.doubleValue());
          return res;
        }
        Decimal highest = optionalHighest.get();
        Decimal lowest = optionalLowest.get();
        Decimal res = calculate(value, highest, lowest);
          
          System.out.println("K resp " + response.getPointK() + " calc " + res.doubleValue());
          return res;
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
