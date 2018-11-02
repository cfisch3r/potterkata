import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PotterCalculator {

    static final BigDecimal SINGLE_BOOK_PRICE = new BigDecimal(8);
    private static final double DISCOUNT_RATE = 0.05;

    enum BOOKS {
        BOOK2, BOOK3, BOOK4, BOOK5, BOOK1
    }

    private static final Map<Integer,Double> DISCOUNT_RATES = new HashMap<>() {
        {
            put(2,0.95);
            put(3,0.9);
            put(4,0.8);
            put(5,0.75);
        }
    };

    public BigDecimal priceFor(BOOKS... books) {
        List<BOOKS> bookList = new ArrayList<>(Arrays.asList(books));
        List<BOOKS> uniqueBooks = getUniqueBooks(bookList);
        if (uniqueBooks.size() > 1){
            return getDiscountPrice(bookList, uniqueBooks);
        } else
            return basePrice(bookList.size());
    }

    private BigDecimal getDiscountPrice(List<BOOKS> bookList, List<BOOKS> uniqueBooks) {
        var price = new BigDecimal(0);
        price = price.add(basePrice(uniqueBooks.size()).multiply(discountFactor(uniqueBooks.size())));
        for (BOOKS book: uniqueBooks)
            bookList.remove(book);
        if (bookList.size() > 0) {
            price = price.add(priceFor(bookList.toArray(new BOOKS[]{})));
        }
        return price;
    }

    private List<BOOKS> getUniqueBooks(List<BOOKS> bookList) {
        return bookList.stream().distinct().collect(Collectors.toList());
    }

    private BigDecimal discountFactor(int numberOfBooks) {
        return new BigDecimal(DISCOUNT_RATES.get(numberOfBooks));
    }

    private BigDecimal basePrice(int count) {
        return SINGLE_BOOK_PRICE.multiply(new BigDecimal(count));
    }
}
