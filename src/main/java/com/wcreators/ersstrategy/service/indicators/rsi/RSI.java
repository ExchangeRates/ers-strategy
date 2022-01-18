package com.wcreators.ersstrategy.service.indicators.rsi;

import com.wcreators.ersstrategy.model.Decimal;
import com.wcreators.ersstrategy.service.indicators.ma.EMA;
import com.wcreators.ersstrategy.service.storage.StorageIndicator;

public class RSI extends StorageIndicator<Decimal, Decimal> {

    private final StorageIndicator<Decimal, Decimal> maOfU;
    private final StorageIndicator<Decimal, Decimal> maOfD;
    private Decimal prevValue;

    public RSI(int period) {
        this.maOfU = new EMA(period);
        this.maOfD = new EMA(period);
    }

    public RSI(StorageIndicator<Decimal, Decimal> maOfU, StorageIndicator<Decimal, Decimal> maOfD) {
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
        return Decimal.HUNDRED.minus(ratio);
    }
}
