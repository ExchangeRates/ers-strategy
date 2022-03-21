package com.wcreators.ersstrategy.service.strategy;

import com.wcreators.ersstrategy.constant.Strategy;
import com.wcreators.ersstrategy.feign.EmaFeignClient;
import com.wcreators.ersstrategy.feign.RsiFeignClient;
import com.wcreators.ersstrategy.model.CupRatePoint;
import com.wcreators.ersstrategy.model.Decimal;
import com.wcreators.ersstrategy.model.StrategyResponse;
import com.wcreators.ersstrategy.service.cup.Cup;
import com.wcreators.ersstrategy.service.indicators.ma.EMA;
import com.wcreators.ersstrategy.service.indicators.rsi.RSI;
import com.wcreators.ersstrategy.service.indicators.stochastic.StochasticD;
import com.wcreators.ersstrategy.service.indicators.stochastic.StochasticK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

// https://www.bestbinar.ru/strategiya-skalpinga-na-1-minute/
@Service
@Slf4j
public class StrategyEmaRsiStochastic implements ProcessRatesService {

    private final Cup cup;
    private final EMA ema;
    private final RSI rsi;
    private final StochasticK stochasticK;
    private final StochasticD stochasticD;

    public StrategyEmaRsiStochastic(Cup cup, EmaFeignClient emaFeignClient, RsiFeignClient rsiFeignClient) {
        this.cup = cup;
        this.ema = new EMA(7, emaFeignClient);
        this.rsi = new RSI(3, emaFeignClient, rsiFeignClient);
        this.stochasticK = new StochasticK(6);
        this.stochasticD = new StochasticD(stochasticK, 3, emaFeignClient);
    }

    @Override
    public List<StrategyResponse> addRate(CupRatePoint cupPoint) {
        List<StrategyResponse> responses = new LinkedList<>();

        cup.addPoint(cupPoint);
        Decimal point = cupPoint.getDecimalClose();
        ema.addPoint(point);
        rsi.addPoint(point);
        stochasticK.addPoint(point);
        stochasticD.addPoint(point);


        if (cup.size() > 1) {
            StrategyResponse response = testStrategyCall(cupPoint);
            responses.add(response);
            if (response.isAction()) {
                return responses;
            }
        }

        if (cup.size() > 1) {
            StrategyResponse response = testStrategyPut(cupPoint);
            responses.add(response);
            if (response.isAction()) {
                return responses;
            }
        }

        return responses;
    }

    public StrategyResponse testStrategyCall(CupRatePoint lastCupPoint) {
        StrategyResponse negativeResponse = StrategyResponse.builder().isAction(false).build();
        Decimal lastEmaPoint = ema.lastAdded();
        Decimal prevCupHigh = cup.get(cup.size() - 2).getDecimalHigh();
        Decimal prevCupLow = cup.get(cup.size() - 2).getDecimalLow();
        if (!(prevCupHigh.compareTo(lastEmaPoint) > 0 && prevCupLow.compareTo(lastEmaPoint) < 0)) {
            String data = String.format("Call 1 | high: %s, low: %s, ema: %s", prevCupHigh, prevCupLow, lastEmaPoint);
            log.warn(data);
            return negativeResponse.withData(data);
        }
        Decimal lastCupLow = lastCupPoint.getDecimalLow();
        if (!(lastCupLow.compareTo(lastEmaPoint) > 0)) {
            String data = String.format("Call 2 | low: %s, ema: %s", lastCupLow, lastEmaPoint);
            log.warn(data);
            return negativeResponse.withData(data);
        }

        Decimal lastRsiPoint = rsi.lastAdded();
        if (!(lastRsiPoint.compareTo(Decimal.valueOf(50)) > 0)) { // 80 is better
            String data = String.format("Call 3 | rsi: %s", lastRsiPoint);
            log.warn(data);
            return negativeResponse.withData(data);
        }

        Decimal prevStochasticK = stochasticK.get(stochasticK.size() - 2);
        Decimal prevStochasticD = stochasticD.get(stochasticD.size() - 2);
        Decimal lastStochasticK = stochasticK.lastAdded();
        Decimal lastStochasticD = stochasticD.lastAdded();
        if (!(lastStochasticK.compareTo(prevStochasticK) > 0 && lastStochasticD.compareTo(prevStochasticD) > 0)) {
            String data = String.format("Call 4 | lastK: %s, prevK: %s / lastD: %s, prevD: %s", lastStochasticK, prevStochasticK, lastStochasticD, prevStochasticD);
            log.warn(data);
            return negativeResponse.withData(data);
        }
        if (!(prevStochasticK.compareTo(Decimal.valueOf(30)) < 0 && prevStochasticD.compareTo(Decimal.valueOf(30)) < 0)) {
            String data = String.format("Call 5 | prevK: %s, prevD: %s", prevStochasticK, prevStochasticD);
            log.warn(data);
            return negativeResponse.withData(data);
        }

        log.info("Need try to buy, check this and after 5 min value");

        return StrategyResponse.builder().isAction(true).data("try to buy (call)").build();
    }

    public StrategyResponse testStrategyPut(CupRatePoint lastCupPoint) {
        StrategyResponse negativeResponse = StrategyResponse.builder().isAction(false).build();
        Decimal lastEmaPoint = ema.lastAdded();
        Decimal prevCupHigh = cup.get(cup.size() - 2).getDecimalHigh();
        Decimal prevCupLow = cup.get(cup.size() - 2).getDecimalLow();
        if (!(prevCupHigh.compareTo(lastEmaPoint) > 0 && prevCupLow.compareTo(lastEmaPoint) < 0)) {
            String data = String.format("Put 1 | high: %s, low: %s, ema: %s", prevCupHigh, prevCupLow, lastEmaPoint);
            log.warn(data);
            return negativeResponse.withData(data);
        }
        Decimal lastCupHigh = lastCupPoint.getDecimalHigh();
        if (!(lastCupHigh.compareTo(lastEmaPoint) < 0)) {
            String data = String.format("Put 2 | low: %s, ema: %s", lastCupHigh, lastEmaPoint);
            log.warn(data);
            return negativeResponse.withData(data);
        }

        Decimal lastRsiPoint = rsi.lastAdded();
        if (!(lastRsiPoint.compareTo(Decimal.valueOf(50)) < 0)) { // better if bottom line was crossed in (20 or 30)
            String data = String.format("Put 3 | rsi: %s", lastRsiPoint);
            log.warn(data);
            return negativeResponse.withData(data);
        }

        Decimal prevStochasticK = stochasticK.get(stochasticK.size() - 2);
        Decimal prevStochasticD = stochasticD.get(stochasticD.size() - 2);
        Decimal lastStochasticK = stochasticK.lastAdded();
        Decimal lastStochasticD = stochasticD.lastAdded();
        if (!(lastStochasticK.compareTo(prevStochasticK) < 0 && lastStochasticD.compareTo(prevStochasticD) < 0)) {
            String data = String.format("Put 4 | lastK: %s, prevK: %s / lastD: %s, prevD: %s", lastStochasticK, prevStochasticK, lastStochasticD, prevStochasticD);
            log.warn(data);
            return negativeResponse.withData(data);
        }
        if (!(prevStochasticK.compareTo(Decimal.valueOf(70)) > 0 && prevStochasticD.compareTo(Decimal.valueOf(70)) > 0)) {
            String data = String.format("Put 5 | prevK: %s, prevD: %s", prevStochasticK, prevStochasticD);
            log.warn(data);
            return negativeResponse.withData(data);
        }

        log.info("Need try to sell, check this and after 5 min value");

        return StrategyResponse.builder().isAction(true).data("try to sell (put)").build();
    }

    @Override
    public Strategy getStrategy() {
        return Strategy.EMA_RSI_STOCH;
    }
}
