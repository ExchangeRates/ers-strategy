package com.wcreators.ersstrategy.service.indicators.rsi;

import com.wcreators.ersstrategy.dto.rsi.RsiRequestDTO;
import com.wcreators.ersstrategy.dto.rsi.RsiResponseDTO;
import com.wcreators.ersstrategy.feign.EmaFeignClient;
import com.wcreators.ersstrategy.feign.RsiFeignClient;
import com.wcreators.ersstrategy.model.Decimal;
import com.wcreators.ersstrategy.service.indicators.ma.EMA;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;

public class RSI extends StorageIndicator<Decimal, Decimal> {

    private final RsiFeignClient rsiFeignClient;
    private final StorageIndicator<Decimal, Decimal> maOfU;
    private final StorageIndicator<Decimal, Decimal> maOfD;
    private Decimal prevValue;

    public RSI(int period, EmaFeignClient emaFeignClient, RsiFeignClient rsiFeignClient) {
        this.rsiFeignClient = rsiFeignClient;
        this.maOfU = new EMA(period, emaFeignClient);
        this.maOfD = new EMA(period, emaFeignClient);
    }

    public RSI(StorageIndicator<Decimal, Decimal> maOfU, StorageIndicator<Decimal, Decimal> maOfD, RsiFeignClient rsiFeignClient) {
        this.rsiFeignClient = rsiFeignClient;
        this.maOfU = maOfU;
        this.maOfD = maOfD;
    }

    @Override
    public Decimal calculate(Decimal value) {
        if (prevValue == null) {
            prevValue = value;
            maOfU.addPoint(Decimal.ZERO);
            maOfD.addPoint(Decimal.ZERO);
            return Decimal.ZERO;
        }
        boolean isGain = value.isGain(prevValue);
        Decimal sub = value.minus(prevValue).abs();

        Decimal U = isGain ? sub : Decimal.ZERO;
        Decimal D = !isGain ? sub : Decimal.ZERO;

        Decimal emaOfUPoint = maOfU.addPoint(U);
        Decimal emaOfDPoint = maOfD.addPoint(D);

        boolean isLossZero = emaOfDPoint.isZero();
        if (isLossZero) {
            prevValue = value;
            return Decimal.HUNDRED;
        }

        Decimal RS = emaOfUPoint.divide(emaOfDPoint);
        Decimal ratio = Decimal.HUNDRED.divide(Decimal.ONE.plus(RS));

        prevValue = value;
        Decimal res = Decimal.HUNDRED.minus(ratio);

        return res;
    }
}
