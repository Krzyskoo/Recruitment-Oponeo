package com.example.e_commerce.component;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class TaxCalculator {
    private static final int SCALE = 2;

    public BigDecimal netToGross(BigDecimal net, BigDecimal vatRate) {
        return scale(net.multiply(BigDecimal.ONE.add(vatRate)));
    }
    public BigDecimal grossToNet(BigDecimal gross, BigDecimal vatRate) {
        return scale(gross.divide(BigDecimal.ONE.add(vatRate), SCALE, BigDecimal.ROUND_HALF_UP));
    }
    public BigDecimal vatAmountFromNet(BigDecimal net, BigDecimal vatRate) {
        return scale(net.multiply(vatRate));
    }
    public BigDecimal vatAmountFromGross(BigDecimal gross, BigDecimal vatRate) {
        var net = grossToNet(gross, vatRate);
        return scale(gross.subtract(net));
    }
    public BigDecimal scale(BigDecimal value) {
        if (value == null) return null;
        return value.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }
}
