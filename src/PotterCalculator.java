import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PotterCalculator {

    static final BigDecimal SINGLE_BOOK_PRICE = new BigDecimal(8);
    private static final double DISCOUNT_RATE = 0.05;

    enum BOOKS {
        BOOK2, BOOK1
    }

    public BigDecimal priceFor(BOOKS... books) {
        List<BOOKS> bookList = new ArrayList<>(Arrays.asList(books));
        var price = new BigDecimal(0);
        if (books.length > 1 && books[0] != books[1]){
            price = price.add(basePrice(2).multiply(discountFactor()));
            bookList.remove(books[0]);
            bookList.remove(books[1]);
            if (bookList.size() > 0) {
                price = price.add(priceFor(bookList.toArray(new BOOKS[]{})));
            }
            return price;
        }
        return basePrice(books.length);
    }

    private BigDecimal discountFactor() {
        return new BigDecimal(1 - DISCOUNT_RATE);
    }

    private BigDecimal basePrice(int count) {
        return SINGLE_BOOK_PRICE.multiply(new BigDecimal(count));
    }
}
