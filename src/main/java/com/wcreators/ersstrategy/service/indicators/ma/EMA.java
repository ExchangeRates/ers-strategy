package com.wcreators.ersstrategy.service.indicators.ma;


import com.wcreators.ersstrategy.dto.EmaRequestDTO;
import com.wcreators.ersstrategy.dto.EmaResponseDTO;
import com.wcreators.ersstrategy.feign.EmaFeignClient;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;

import lombok.Getter;

public class EMA extends StorageIndicator<Double, Double> {

    @Getter
    private final int period;
    private final EmaFeignClient emaFeignClient;

    public EMA(int period, EmaFeignClient emaFeignClient) {
        this.emaFeignClient = emaFeignClient;
        this.period = period;
    }

    @Override
    public Double calculate(Double value) {
        Double prev = lastAdded().orElse(null);
        EmaRequestDTO request = EmaRequestDTO.builder().prev(prev).value(value).period(period).build();
        EmaResponseDTO response = emaFeignClient.calculate(request);
        return response.getValue();
    }
}
