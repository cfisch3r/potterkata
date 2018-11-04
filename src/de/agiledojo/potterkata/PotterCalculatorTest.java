package de.agiledojo.potterkata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static de.agiledojo.potterkata.BOOKS.*;
import static java.util.Arrays.asList;

public class PotterCalculatorTest {

    private PotterCalculator calculator;
    private DiscountRatesMock discountRates;

    @Before
    public void setUp() {
        discountRates = new DiscountRatesMock();
        calculator = new PotterCalculator(new Price(8), discountRates);
    }

    @Test
    public void single_book_costs_base_price() {
        var price = calculator.priceFor(asList(BOOK1));
        Assert.assertEquals(new Price(8),price);
    }

    @Test
    public void multiple_identical_books_cost_multiple_base_price() {
        var price = calculator.priceFor(asList(BOOK1, BOOK1));
        Assert.assertEquals(new Price(8*2),price);
    }

    @Test
    public void two_different_books_get_discount() {
        var price = calculator.priceFor(asList(BOOK1, BOOK2));
        Assert.assertEquals(new Price(8*2*0.95),price);
    }

    @Test
    public void two_different_and_one_identical_book_get_combined_price() {
        var price = calculator.priceFor(asList(BOOK1, BOOK2, BOOK1));
        Assert.assertEquals(new Price(8*2*0.95+8),price);
    }

    @Test
    public void two_different_and_one_identical_book_different_order_get_combined_price() {
        var price = calculator.priceFor(asList(BOOK1, BOOK1, BOOK2));
        Assert.assertEquals(new Price(8*2*0.95+8),price);
    }

    @Test
    public void three_books_from_series_get_discount() {
        var price = calculator.priceFor(asList(BOOK1, BOOK2, BOOK3));
        Assert.assertEquals(new Price(8*3*0.9),price);
    }

    @Test
    public void four_books_from_series_get_discount() {
        var price = calculator.priceFor(asList(BOOK1, BOOK2, BOOK3, BOOK4));
        Assert.assertEquals(new Price(8*4*0.8),price);
    }

    @Test
    public void five_books_from_series_get_discount() {
        var price = calculator.priceFor(asList(BOOK1, BOOK2, BOOK3, BOOK4, BOOK5));
        Assert.assertEquals(new Price(8*5*0.75),price);
    }

    @Test
    public void two_4_series_are_preferred() {
        var price = calculator.priceFor(asList(BOOK1, BOOK1, BOOK2, BOOK2,
                BOOK3, BOOK3, BOOK4, BOOK5));
        Assert.assertEquals(new Price(8*4*0.8*2),price);
    }

    @Test
    public void three_identical_books_and_5_series_get_combined_price() {
        var price = calculator.priceFor(asList(BOOK1, BOOK2, BOOK3,
                BOOK4, BOOK5, BOOK5, BOOK5, BOOK5));
        Assert.assertEquals(new Price(8*5*0.75+8*3),price);
    }

    @Test
    public void a_4_and_3_series_are_preferred_when_cheaper() {
        discountRates.discountMap.replace(3,0.8);
        var price = calculator.priceFor(asList(BOOK1, BOOK1, BOOK2, BOOK2,
                BOOK3, BOOK3, BOOK4, BOOK5));
        Assert.assertEquals(new Price((8*5*0.75)+(8*3*0.8)),price);
    }

    @Test
    public void two_2_series_are_preferred_when_cheaper() {
        discountRates.discountMap.replace(2,0.9);
        var price = calculator.priceFor(asList(BOOK1, BOOK1, BOOK2, BOOK3));
        Assert.assertEquals(new Price((8*2*0.9)*2),price);
    }

    private static class DiscountRatesMock implements DiscountRates {
        private Map<Integer, Double> discountMap = new HashMap<>() {
            {
                put(1, 1d);
                put(2, 0.95);
                put(3, 0.9);
                put(4, 0.8);
                put(5, 0.75);
            }
        };

        @Override
        public double factorFor(int numberOfBooks) {
            return discountMap.get(numberOfBooks);
        }
    }
}
