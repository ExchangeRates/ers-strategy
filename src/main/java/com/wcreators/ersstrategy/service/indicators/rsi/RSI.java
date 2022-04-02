package com.wcreators.ersstrategy.service.indicators.rsi;

import com.wcreators.ersstrategy.dto.rsi.RsiRequestDTO;
import com.wcreators.ersstrategy.dto.rsi.RsiResponseDTO;
import com.wcreators.ersstrategy.feign.EmaFeignClient;
import com.wcreators.ersstrategy.feign.RsiFeignClient;
import com.wcreators.ersstrategy.service.indicators.ma.EMA;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;

public class RSI extends StorageIndicator<Double, Double> {

    private final RsiFeignClient rsiFeignClient;
    private final StorageIndicator<Double, Double> maOfU;
    private final StorageIndicator<Double, Double> maOfD;
    private Double prevValue;
    private final Integer period;

    public RSI(int period, EmaFeignClient emaFeignClient, RsiFeignClient rsiFeignClient) {
        this.rsiFeignClient = rsiFeignClient;
        this.maOfU = new EMA(period, emaFeignClient);
        this.maOfD = new EMA(period, emaFeignClient);
        this.period = period;
    }

    @Override
    public Double calculate(Double value) {

        RsiRequestDTO request = RsiRequestDTO.builder()
          .value(value)
          .prev(prevValue)
          .prevU(maOfU.lastAdded().orElse(null))
          .prevD(maOfD.lastAdded().orElse(null))
          .period(this.period)
          .build();
        RsiResponseDTO response = rsiFeignClient.calculate(request);
        maOfU.addPointWithoutCalc(response.getMaOfU());
        maOfD.addPointWithoutCalc(response.getMaOfD());
        prevValue = value;

        return response.getValue();
    }
}
