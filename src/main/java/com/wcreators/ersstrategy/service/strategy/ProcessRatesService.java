package com.wcreators.ersstrategy.service.strategy;

import com.wcreators.ersstrategy.constant.Strategy;
import com.wcreators.ersstrategy.model.CupRatePoint;
import com.wcreators.ersstrategy.model.StrategyResponse;

import java.util.List;

public interface ProcessRatesService {
    List<StrategyResponse> addRate(CupRatePoint rate);

    Strategy getStrategy();
}
