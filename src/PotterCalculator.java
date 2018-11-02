import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PotterCalculator {

    static final BigDecimal SINGLE_BOOK_PRICE = new BigDecimal(8);
    private static final double DISCOUNT_RATE = 0.05;

    enum BOOKS {
        BOOK2, BOOK1
    }

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
        price = price.add(basePrice(uniqueBooks.size()).multiply(discountFactor()));
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

    private BigDecimal discountFactor() {
        return new BigDecimal(1 - DISCOUNT_RATE);
    }

    private BigDecimal basePrice(int count) {
        return SINGLE_BOOK_PRICE.multiply(new BigDecimal(count));
    }
}
