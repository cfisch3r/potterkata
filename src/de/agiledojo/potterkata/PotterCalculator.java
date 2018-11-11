package de.agiledojo.potterkata;

import java.util.List;

public class PotterCalculator {

    private final Combinator combinator = new Combinator();
    private Price singleBookPrice;
    private DiscountRates discountRates;

    public PotterCalculator(Price singleBookPrice, DiscountRates discountRates) {
        this.singleBookPrice = singleBookPrice;
        this.discountRates = discountRates;
    }

    public Price priceFor(List<BOOKS> bookList) {
        List<List<Integer>> seriesListSizes = combinator.getCombinations(bookList);

        return getLowestPrice(seriesListSizes);
    }

    private Price getLowestPrice(List<List<Integer>> seriesListSizes) {
        return seriesListSizes.stream().map(s -> totalPrice(s)).sorted((p1, p2) -> p1.compareTo(p2))
                .findFirst().orElseThrow();
    }

    private Price totalPrice(List<Integer> seriesListSizes) {
        var price = new Price(0);
        for (var series : seriesListSizes)
            price = price.add(seriesPrice(series));

        return price;
    }

    private Price seriesPrice(int seriesSize) {
        return basePrice(seriesSize).multiply(discountRates.factorFor(seriesSize));
    }


    private Price basePrice(int numberOfBooks) {
        return singleBookPrice.multiply(numberOfBooks);
    }
}
