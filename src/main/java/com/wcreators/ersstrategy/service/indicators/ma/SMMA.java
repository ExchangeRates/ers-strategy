package com.wcreators.ersstrategy.service.indicators.ma;

import com.wcreators.ersstrategy.feign.EmaFeignClient;

public class SMMA extends EMA {
    public SMMA(int period, EmaFeignClient emaFeignClient) {
        super(period - 1, emaFeignClient);
    }
}
