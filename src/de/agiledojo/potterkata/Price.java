package de.agiledojo.potterkata;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Price {
    private BigDecimal amount;

    public Price(double amount) {
        this(new BigDecimal(amount));
    }

    private Price(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    Price multiply(double factor) {
        return new Price(this.amount.multiply(new BigDecimal(factor)));
    }

    Price add(Price price) {
        return new Price(this.amount.add(price.amount));
    }


    @Override
    public String toString() {
        return amount + "€";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(amount, price.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
