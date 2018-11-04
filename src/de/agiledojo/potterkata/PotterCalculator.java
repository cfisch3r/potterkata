package de.agiledojo.potterkata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class PotterCalculator {

    private static final Map<Integer,Double> DISCOUNT_RATES = new HashMap<>() {
        {
            put(1,1d);
            put(2,0.95);
            put(3,0.9);
            put(4,0.8);
            put(5,0.75);
        }
    };
    private Price singleBookPrice;

    public PotterCalculator(Price singleBookPrice) {
        this.singleBookPrice = singleBookPrice;
    }

    public Price priceFor(BOOKS... books) {
        return priceFor(asList(books));
    }


    private Price priceFor(List<BOOKS> bookList) {
        var series = distinctBooks(bookList);
        var remainingBooks = substract(bookList, series);

        if (series.size() == 5 && distinctBooks(remainingBooks).size() == 3)
            splitSeriesEqually(series, remainingBooks);

        return calculateDiscountPrice(series.size(), remainingBooks);
    }

    private Price calculateDiscountPrice(int seriesSize, List<BOOKS> remainingBooks) {
        var price = basePrice(seriesSize).multiply(discountFactor(seriesSize));

        if (remainingBooks.size() > 0)
            price = price.add(priceFor(remainingBooks));

        return price;
    }

    private void splitSeriesEqually(List<BOOKS> series, List<BOOKS> remainingBooks) {
        for (BOOKS book: series) {
            if (!remainingBooks.contains(book)) {
                remainingBooks.add(book);
                series.remove(book);
                break;
            }
        }
    }

    private List<BOOKS> substract(List<BOOKS> bookList, List<BOOKS> series) {
        var remainingBooks = new ArrayList<>(bookList);
        for (BOOKS book: series)
            remainingBooks.remove(book);
        return remainingBooks;
    }

    private List<BOOKS> distinctBooks(List<BOOKS> bookList) {
        return bookList.stream().distinct().collect(Collectors.toList());
    }

    private double discountFactor(int numberOfBooks) {
        return DISCOUNT_RATES.get(numberOfBooks);
    }

    private Price basePrice(int numberOfBooks) {
        return singleBookPrice.multiply(numberOfBooks);
    }
}
