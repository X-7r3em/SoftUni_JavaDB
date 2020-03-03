package Entities.Shampoos;

import Entities.BasicLabel;

import java.math.BigDecimal;

public class FiftyShades extends BasicShampoo {
    private static final BigDecimal PRICE = new BigDecimal("6.69");
    private static final String BRAND = "Fifty Shades";
    private static final Size SIZE = Size.SMALL;

    public FiftyShades() {
    }

    public FiftyShades(BasicLabel label) {
        super(PRICE, BRAND, SIZE, label);
    }
}
