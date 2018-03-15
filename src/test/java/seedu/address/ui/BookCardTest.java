package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysBook;

import org.junit.Test;

import guitests.guihandles.BookCardHandle;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

public class BookCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no categories
        Book bookWithNoCategories = new BookBuilder().withCategories().build();
        BookCard bookCard = new BookCard(bookWithNoCategories, 1);
        uiPartRule.setUiPart(bookCard);
        assertCardDisplay(bookCard, bookWithNoCategories, 1);

        // with categories
        Book bookWithCategories = new BookBuilder().build();
        bookCard = new BookCard(bookWithCategories, 2);
        uiPartRule.setUiPart(bookCard);
        assertCardDisplay(bookCard, bookWithCategories, 2);
    }

    @Test
    public void equals() {
        Book book = new BookBuilder().build();
        BookCard bookCard = new BookCard(book, 0);

        // same book, same index -> returns true
        BookCard copy = new BookCard(book, 0);
        assertTrue(bookCard.equals(copy));

        // same object -> returns true
        assertTrue(bookCard.equals(bookCard));

        // null -> returns false
        assertFalse(bookCard == null);

        // different types -> returns false
        assertFalse(bookCard.equals(0));

        // different book, same index -> returns false
        Book differentBook = new BookBuilder().withIsbn("111").build();
        assertFalse(bookCard.equals(new BookCard(differentBook, 0)));

        // same book, different index -> returns false
        assertFalse(bookCard.equals(new BookCard(book, 1)));
    }

    /**
     * Asserts that {@code bookCard} displays the details of {@code expectedBook} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(BookCard bookCard, Book expectedBook, int expectedId) {
        guiRobot.pauseForHuman();

        BookCardHandle bookCardHandle = new BookCardHandle(bookCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", bookCardHandle.getId());

        // verify book details are displayed correctly
        assertCardDisplaysBook(expectedBook, bookCardHandle);
    }
}
