package de.agiledojo.potterkata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class PotterCalculator {

    private Price singleBookPrice;
    private DiscountRates discountRates;

    public PotterCalculator(Price singleBookPrice, DiscountRates discountRates) {
        this.singleBookPrice = singleBookPrice;
        this.discountRates = discountRates;
    }

    public Price priceFor(BOOKS... books) {
        return priceFor(asList(books));
    }


    private Price priceFor(List<BOOKS> bookList) {
        var series = distinctBooks(bookList);
        var remainingBooks = substract(bookList, series);

        optimizeSeriesSplitting(series, remainingBooks);

        return calculateDiscountPrice(series.size(), remainingBooks);
    }

    private void optimizeSeriesSplitting(List<BOOKS> series, List<BOOKS> remainingBooks) {
        if (series.size() == 5 && distinctBooks(remainingBooks).size() == 3) {
            if (seriesPrice(4).multiply(2).compareTo(seriesPrice(5)
                    .add(seriesPrice(3))) == -1)
                splitSeriesEqually(series, remainingBooks);
        }

    }

    private Price calculateDiscountPrice(int seriesSize, List<BOOKS> remainingBooks) {
        var price = seriesPrice(seriesSize);

        if (remainingBooks.size() > 0)
            price = price.add(priceFor(remainingBooks));

        return price;
    }

    private Price seriesPrice(int seriesSize) {
        return basePrice(seriesSize).multiply(discountRates.factorFor(seriesSize));
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

    private Price basePrice(int numberOfBooks) {
        return singleBookPrice.multiply(numberOfBooks);
    }
}
