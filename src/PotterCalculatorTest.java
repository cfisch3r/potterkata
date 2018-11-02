import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class PotterCalculatorTest {

    private PotterCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new PotterCalculator();
    }

    @Test
    public void single_book_costs_base_price() {
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1);
        Assert.assertEquals(PotterCalculator.SINGLE_BOOK_PRICE,price);
    }

    @Test
    public void multiple_identical_books_cost_multiple_base_price() {
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1,PotterCalculator.BOOKS.BOOK1);
        Assert.assertEquals(PotterCalculator.SINGLE_BOOK_PRICE.multiply(new BigDecimal(2)),price);
    }

    @Test
    public void two_different_books_get_discount() {
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1,PotterCalculator.BOOKS.BOOK2);
        Assert.assertEquals(PotterCalculator.SINGLE_BOOK_PRICE.multiply(new BigDecimal(2)).multiply(new BigDecimal(0.95)),price);
    }
}
