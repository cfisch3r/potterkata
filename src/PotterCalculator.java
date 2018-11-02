import java.math.BigDecimal;

public class PotterCalculator {

    static final BigDecimal SINGLE_BOOK_PRICE = new BigDecimal(8);
    private static final double DISCOUNT_RATE = 0.05;

    enum BOOKS {
        BOOK2, BOOK1
    }

    public BigDecimal priceFor(BOOKS... books) {
        if (books.length > 1 && books[0] != books[1]){
            return basePrice(books.length).multiply(new BigDecimal(1- DISCOUNT_RATE));
        }
        return basePrice(books.length);
    }

    private BigDecimal basePrice(int count) {
        return SINGLE_BOOK_PRICE.multiply(new BigDecimal(count));
    }
}
