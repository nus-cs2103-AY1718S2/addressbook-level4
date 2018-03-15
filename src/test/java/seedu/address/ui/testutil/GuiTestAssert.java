package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.BookCardHandle;
import guitests.guihandles.BookDetailsPanelHandle;
import guitests.guihandles.BookListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.SearchResultsPanelHandle;
import seedu.address.model.book.Book;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {

    /**
     * Asserts that {@code detailsPanel} displays the details of {@code expectedBook}.
     */
    public static void assertDetailsPanelDisplaysBook(Book expectedBook, BookDetailsPanelHandle detailsPanel) {
        assertEquals(expectedBook.getTitle().toString(), detailsPanel.getTitle());
        assertEquals(expectedBook.getIsbn().toString(), detailsPanel.getIsbn());
        assertEquals(expectedBook.getPublisher().toString(), detailsPanel.getPublisher());
        assertEquals(expectedBook.getPublicationDate().toString(), detailsPanel.getPublicationDate());
        assertEquals(expectedBook.getDescription().toString(), detailsPanel.getDescription());
        assertEquals(expectedBook.getAuthors().stream().map(author -> author.fullName)
                .collect(Collectors.toList()), detailsPanel.getAuthors());
        assertEquals(expectedBook.getCategories().stream().map(category -> category.category)
                .collect(Collectors.toList()), detailsPanel.getCategories());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(BookCardHandle expectedCard, BookCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAuthors(), actualCard.getAuthors());
        assertEquals(expectedCard.getCategories(), actualCard.getCategories());
        assertEquals(expectedCard.getTitle(), actualCard.getTitle());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedBook}.
     */
    public static void assertCardDisplaysBook(Book expectedBook, BookCardHandle actualCard) {
        assertEquals(expectedBook.getTitle().title, actualCard.getTitle());
        assertEquals(expectedBook.getAuthors().stream().map(author -> author.fullName)
                        .collect(Collectors.toList()), actualCard.getAuthors());
        assertEquals(expectedBook.getCategories().stream().map(category -> category.category)
                        .collect(Collectors.toList()), actualCard.getCategories());
    }

    /**
     * Asserts that the list in {@code bookListPanelHandle} displays the details of {@code books} correctly and
     * in the correct order.
     */
    public static void assertListMatching(BookListPanelHandle bookListPanelHandle, Book... books) {
        for (int i = 0; i < books.length; i++) {
            assertCardDisplaysBook(books[i], bookListPanelHandle.getBookCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code searchResultsPanelHandle} displays the details of {@code books} correctly and
     * in the correct order.
     */
    public static void assertListMatching(SearchResultsPanelHandle searchResultsPanelHandle, Book... books) {
        for (int i = 0; i < books.length; i++) {
            assertCardDisplaysBook(books[i], searchResultsPanelHandle.getBookCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code bookListPanelHandle} displays the details of {@code books} correctly and
     * in the correct order.
     */
    public static void assertListMatching(BookListPanelHandle bookListPanelHandle, List<Book> books) {
        assertListMatching(bookListPanelHandle, books.toArray(new Book[0]));
    }

    /**
     * Asserts that the list in {@code searchResultsPanelHandle} displays the details of {@code books} correctly and
     * in the correct order.
     */
    public static void assertListMatching(SearchResultsPanelHandle searchResultsPanelHandle, List<Book> books) {
        assertListMatching(searchResultsPanelHandle, books.toArray(new Book[0]));
    }

    /**
     * Asserts the size of the list in {@code bookListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(BookListPanelHandle bookListPanelHandle, int size) {
        int numberOfBooks = bookListPanelHandle.getListSize();
        assertEquals(size, numberOfBooks);
    }

    /**
     * Asserts the size of the list in {@code searchResultsPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(SearchResultsPanelHandle searchResultsPanelHandle, int size) {
        int numberOfBooks = searchResultsPanelHandle.getListSize();
        assertEquals(size, numberOfBooks);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
