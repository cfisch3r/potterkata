import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PotterCalculatorTest {

    private PotterCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new PotterCalculator();
    }

    @Test
    public void single_book_costs_base_price() {
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1);
        Assert.assertEquals(bd(8),price);
    }

    @Test
    public void multiple_identical_books_cost_multiple_base_price() {
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1,PotterCalculator.BOOKS.BOOK1);
        Assert.assertEquals(product(8,2),price);
    }

    @Test
    public void two_different_books_get_discount() {
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1,PotterCalculator.BOOKS.BOOK2);
        Assert.assertEquals(product(8,2,0.95),price);
    }

    @Test
    public void two_different_and_one_identical_book_get_combined_price() {
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1,PotterCalculator.BOOKS.BOOK2,PotterCalculator.BOOKS.BOOK1);
        Assert.assertEquals(product(8,2,0.95).add(bd(8)),price);
    }

    @Test
    public void two_different_and_one_identical_book_different_order_get_combined_price() {
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1,PotterCalculator.BOOKS.BOOK1,PotterCalculator.BOOKS.BOOK2);
        Assert.assertEquals(product(8,2,0.95).add(bd(8)),price);
    }

    @Test
    public void three_books_from_series_get_discount() {
        BigDecimal price = calculator.priceFor(PotterCalculator.BOOKS.BOOK1,PotterCalculator.BOOKS.BOOK2,PotterCalculator.BOOKS.BOOK3);
        Assert.assertEquals(product(8,3,0.9),price);
    }

    private BigDecimal bd(double amount) {
        return new BigDecimal(amount);
    }

    private BigDecimal product(double... values) {
        return Arrays.stream(values).boxed().map(BigDecimal::new).reduce((a,b) -> a.multiply(b)).orElse(new BigDecimal(0));
    }
}
