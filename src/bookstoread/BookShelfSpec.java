package bookstoread;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BookShelfSpec {
    private BookShelf shelf;
    Book effectiveJava;
    Book codeComplete;
    Book mythicalManMonth;
    private Book cleanCode;

    @BeforeEach
    void init() {
        shelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bloch",
                LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel",
                LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month",
                "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
        cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
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
    @DisplayName("La bibliothèque est organisée lexicographiquement par titre de livre")
    void bookshelfArrangedByBookTitle() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = shelf.arrangeByTitle();
        assertEquals(Arrays.asList(codeComplete, effectiveJava, mythicalManMonth), books,
                "Books should be arranged lexicographically by title");
    }


    @Test
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        shelf.arrangeByTitle();
        assertEquals(asList(effectiveJava, codeComplete, mythicalManMonth), shelf.books(),
                "Books in bookshelf are in insertion order");
    }
    @Test
    void bookshelfArrangedByUserProvidedCriteria() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = shelf.arrange(Comparator.<Book>naturalOrder().reversed());
        assertEquals(asList(mythicalManMonth, effectiveJava, codeComplete), books, () -> "Books in a bookshelf are arranged in descending order of book title");
    }
    @Test
    void bookshelfArrangeByBookDate(){
        shelf.add(effectiveJava,codeComplete,mythicalManMonth);
        List<Book> books=shelf.arrangeByPublicationDate();
        assertEquals(asList(mythicalManMonth, codeComplete, effectiveJava), books,
                "Books should be arranged by publication date in ascending order");
    }
    @Test
    @DisplayName("books inside bookshelf are grouped by publication year")
    void groupBooksInsideBookShelfByPublicationYear() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
        Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();
        assertThat(booksByPublicationYear).containsKey(Year.of(2008)).containsValues(Arrays.asList(effectiveJava, cleanCode));
        assertThat(booksByPublicationYear).containsKey(Year.of(2004)).containsValues(Collections.singletonList(codeComplete));
        assertThat(booksByPublicationYear).containsKey(Year.of(1975)).containsValues(Collections.singletonList(mythicalManMonth));

    }
    @Test
    @DisplayName("Les livres à l'intérieur de la bibliothèque sont regroupés selon les critères fournis par l'utilisateur (regroupés par nom d'auteur)")
    void groupBooksByUserProvidedCriteria() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
        Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);
        assertThat(booksByAuthor).containsKey("Joshua Bloch").containsValues(Collections.singletonList(effectiveJava));
        assertThat(booksByAuthor).containsKey("Steve McConnel").containsValues(Collections.singletonList(codeComplete));
        assertThat(booksByAuthor).containsKey("Frederick Phillips Brooks").containsValues(Collections.singletonList(mythicalManMonth));
        assertThat(booksByAuthor).containsKey("Robert C. Martin").containsValues(Collections.singletonList(cleanCode));
    }

}
