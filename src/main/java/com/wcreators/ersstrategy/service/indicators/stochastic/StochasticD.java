package com.wcreators.ersstrategy.service.indicators.stochastic;

import com.wcreators.ersstrategy.feign.EmaFeignClient;
import com.wcreators.ersstrategy.model.Decimal;
import com.wcreators.ersstrategy.service.indicators.ma.EMA;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;

public class StochasticD extends StorageIndicator<Decimal, Decimal> {

    private final StochasticK stochasticK;
    private final EMA ema;

    public StochasticD(StochasticK stochasticK, int period, EmaFeignClient emaFeignClient) {
        this.stochasticK = stochasticK;
        this.ema = new EMA(period, emaFeignClient);
    }

    @Override
    public Decimal calculate(Decimal value) {
        Decimal kValue = stochasticK.lastAdded().get();
        return Decimal.valueOf(ema.addPoint(kValue.doubleValue()));
    }
}
