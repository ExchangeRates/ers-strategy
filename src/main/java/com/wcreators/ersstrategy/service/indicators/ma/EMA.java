package com.wcreators.ersstrategy.service.indicators.ma;


import com.wcreators.ersstrategy.dto.EmaRequestDTO;
import com.wcreators.ersstrategy.dto.EmaResponseDTO;
import com.wcreators.ersstrategy.feign.EmaFeignClient;
import com.wcreators.ersstrategy.model.Decimal;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;
import lombok.Getter;

public class EMA extends StorageIndicator<Decimal, Decimal> {

    @Getter
    private final int period;
    private final Decimal multiplier;
    private final EmaFeignClient emaFeignClient;

    public EMA(int period, EmaFeignClient emaFeignClient) {
        this.emaFeignClient = emaFeignClient;
        this.period = period;
        // 2 / (period + 1)
        this.multiplier = Decimal.TWO.divide(Decimal.valueOf(period).plus(Decimal.ONE));
    }

    @Override
    public Decimal calculate(Decimal value) {
        if (isEmpty()) {
            return value;
        }
        EmaResponseDTO response = emaFeignClient.calculate(EmaRequestDTO.builder().prev(lastAdded().doubleValue()).value(value.doubleValue()).period(period).build());
        Decimal res = Decimal.valueOf(multiplier.doubleValue() * value.doubleValue() + (1 - multiplier.doubleValue()) * lastAdded().doubleValue());
        System.out.println("result " + response.getValue() + " calculated " + res.doubleValue());
        return res;
    }
}
