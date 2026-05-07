package bookstoread;

import java.util.*;
import java.util.stream.Collectors;

public class BookShelf {
    private final List<Book> books = new ArrayList<>();

    public List<Book> books() {
        return Collections.unmodifiableList(books);
    }

    public void add(Book... booksToAdd) {
        books.addAll(Arrays.asList(booksToAdd));
    }

    public List<Book> arrange() {
        return arrange(Comparator.naturalOrder());
    }

    public List<Book> arrangeByAuthor() {
        return books.stream()
                .sorted(Comparator.comparing(Book::getAuthor))
                .collect(Collectors.toList());
    }
    public List<Book> arrange(Comparator<Book> criteria) {
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }

    public List<Book> arrangeByPublicationDate() {
        return books.stream()
                .sorted(Comparator.comparing(Book::getPublishedOn))
                .collect(Collectors.toList());
    }


}
