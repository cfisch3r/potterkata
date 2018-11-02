import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class PotterCalculatorTest {

    @Test
    public void single_book_costs_base_price() {
        var calculator = new PotterCalculator();
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1);
        Assert.assertEquals(PotterCalculator.SINGLE_BOOK_PRICE,price);
    }
}
