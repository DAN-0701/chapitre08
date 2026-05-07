package bookstoread;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class BookShelfSpec {
    private BookShelf shelf;
    Book effectiveJava;
    Book codeComplete;
    Book mythicalManMonth;

    @BeforeEach
    void init() {
        shelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bloch",
                LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel",
                LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month",
                "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
    }

    @Test
    public void shelfEmptyWhenNoBookAdded() {
        assertTrue(shelf.books().isEmpty(), "BookShelf should be empty.");
    }

    @Test
    void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
        shelf.add(effectiveJava, codeComplete);
        assertEquals(2, shelf.books().size(), "BookShelf should have two books.");
    }

    @Test
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        shelf.add();
        assertTrue(shelf.books().isEmpty(), "BookShelf should be empty.");
    }

    @Test
    void booksReturnedFromBookShelfIsImmutableForClient() {
        shelf.add(effectiveJava, codeComplete);
        List<Book> books = shelf.books();
        assertThrows(UnsupportedOperationException.class, () -> books.add(mythicalManMonth));
    }

    @Test
    void bookshelfArrangedByBookTitle() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = shelf.arrange();
        assertEquals(asList(codeComplete, effectiveJava, mythicalManMonth), books,
                "Books should be arranged lexicographically by title");
    }

    @Test
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        shelf.arrange();
        assertEquals(asList(effectiveJava, codeComplete, mythicalManMonth), shelf.books(),
                "Books in bookshelf are in insertion order");
    }
    @Test
    void bookshelfArrangedByUserProvidedCriteria() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = shelf.arrange(Comparator.<Book>naturalOrder().reversed());
        assertEquals(asList(mythicalManMonth, effectiveJava, codeComplete), books, () -> "Books in a bookshelf are arranged in descending order of book title");
    }
}
