package com.wcreators.ersstrategy.model;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Decimal {

    public static final Decimal ZERO = valueOf(0);
    public static final Decimal ONE = valueOf(1);
    public static final Decimal TWO = valueOf(2);
    public static final Decimal HUNDRED = valueOf(100);

    private final BigDecimal delegate;
    private final MathContext context = new MathContext(10, RoundingMode.CEILING);

    private Decimal(BigDecimal val) {
        this.delegate = val;
    }

    private Decimal(double val) {
        this.delegate = new BigDecimal(val, context);
    }

    public Decimal(int val) {
        this.delegate = new BigDecimal(val, context);
    }

    public Decimal(long val) {
        this.delegate = new BigDecimal(val, context);
    }

    public Decimal plus(@NonNull Decimal val) {
        return new Decimal(this.delegate.add(val.delegate, context));
    }

    public Decimal minus(@NonNull Decimal val) {
        return new Decimal(this.delegate.subtract(val.delegate, context));
    }

    public Decimal multiply(Decimal multiplier) {
        return new Decimal(this.delegate.multiply(multiplier.delegate, context));
    }

    public Decimal divide(Decimal divisor) {
        return new Decimal(this.delegate.divide(divisor.delegate, context));
    }

    public Decimal abs() {
        return new Decimal(this.delegate.abs());
    }

    public int compareTo(Decimal val) {
        return delegate.compareTo(val.delegate);
    }

    public boolean isZero() {
        return this.delegate.compareTo(Decimal.ZERO.delegate) == 0;
    }

    public boolean isGain(Decimal val) {
        return this.delegate.compareTo(val.delegate) > 0;
    }

    public boolean isLoss(Decimal val) {
        return this.delegate.compareTo(val.delegate) < 0;
    }

    public double doubleValue() {
        return this.delegate.doubleValue();
    }

    public static Decimal valueOf(double val) {
        return new Decimal(val);
    }

    public static Decimal valueOf(int val) {
        return new Decimal(val);
    }

    public static Decimal valueOf(long val) {
        return new Decimal(val);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
