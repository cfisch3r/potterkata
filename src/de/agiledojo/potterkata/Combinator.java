package de.agiledojo.potterkata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Combinator {
    public Combinator() {
    }

    List<List<Integer>> getCombinations(List<BOOKS> bookList) {
        var combinations = new ArrayList<List<Integer>>();
        var series = distinctBooks(bookList);
        var remainingBooks = substract(bookList, series);

        combinations.add(getCombination(remainingBooks, series.size()));

        if (series.size() > 1)
            series.remove(series.size() -1);
        remainingBooks = substract(bookList, series);

        combinations.add(getCombination(remainingBooks, series.size()));


        return combinations;
    }

    private ArrayList<Integer> getCombination(List<BOOKS> remainingBooks, int seriesSize) {
        var seriesListSizes = new ArrayList<Integer>();
        seriesListSizes.add(seriesSize);
        while (remainingBooks.size() > 0) {
            List<BOOKS> series1 = distinctBooks(remainingBooks);
            seriesListSizes.add(series1.size());
            remainingBooks = substract(remainingBooks, series1);
        }
        return seriesListSizes;
    }

    private List<BOOKS> substract(List<BOOKS> bookList, List<BOOKS> series) {
        var remainingBooks = new ArrayList<BOOKS>(bookList);
        for (BOOKS book : series)
            remainingBooks.remove(book);
        return remainingBooks;
    }

    private List<BOOKS> distinctBooks(List<BOOKS> bookList) {
        return bookList.stream().distinct().collect(Collectors.toList());
    }
}