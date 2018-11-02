import java.math.BigDecimal;

public class PotterCalculator {

    static final BigDecimal SINGLE_BOOK_PRICE = new BigDecimal(8);

    enum BOOKS {
        BOOK1
    }

    public BigDecimal priceFor(BOOKS... books) {
        return SINGLE_BOOK_PRICE;
    }
}
