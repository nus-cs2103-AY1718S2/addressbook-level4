package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalBooks.ARTEMIS;
import static seedu.address.testutil.TypicalBooks.BABYLON_ASHES;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BookDetailsPanelHandle;
import seedu.address.commons.events.ui.BookListSelectionChangedEvent;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;
import seedu.address.ui.testutil.GuiTestAssert;

public class BookDetailsPanelTest extends GuiUnitTest {

    private BookDetailsPanelHandle bookDetailsPanelHandle;

    @Before
    public void setUp() {
        BookDetailsPanel bookDetailsPanel = new BookDetailsPanel();
        uiPartRule.setUiPart(bookDetailsPanel);
        bookDetailsPanelHandle = new BookDetailsPanelHandle(bookDetailsPanel.getRoot());
    }

    @Test
    public void display() {
        // hidden by default
        assertFalse(bookDetailsPanelHandle.isVisible());

        // check that the correct details are displayed
        postNow(new BookListSelectionChangedEvent(new BookCard(ARTEMIS, 0)));
        assertTrue(bookDetailsPanelHandle.isVisible());
        assertDetailsPanelDisplaysBook(ARTEMIS);

        // check that the correct details are displayed
        postNow(new BookListSelectionChangedEvent(new BookCard(BABYLON_ASHES, 1)));
        assertTrue(bookDetailsPanelHandle.isVisible());
        assertDetailsPanelDisplaysBook(BABYLON_ASHES);

        // no categories
        Book bookWithNoCategories = new BookBuilder().withCategories().build();
        postNow(new BookListSelectionChangedEvent(new BookCard(bookWithNoCategories, 0)));
        assertTrue(bookDetailsPanelHandle.isVisible());
        assertDetailsPanelDisplaysBook(bookWithNoCategories);

        // no authors
        Book bookWithNoAuthors = new BookBuilder().withAuthors().build();
        postNow(new BookListSelectionChangedEvent(new BookCard(bookWithNoAuthors, 0)));
        assertTrue(bookDetailsPanelHandle.isVisible());
        assertDetailsPanelDisplaysBook(bookWithNoAuthors);

        // empty book
        Book emptyBook = new BookBuilder().withGid("").withTitle("").withAuthors().withCategories()
                .withDescription("").withIsbn("").withPublicationDate("").withPublisher("").build();
        postNow(new BookListSelectionChangedEvent(new BookCard(emptyBook, 0)));
        assertTrue(bookDetailsPanelHandle.isVisible());
        assertDetailsPanelDisplaysBook(emptyBook);
    }

    /**
     * Asserts that the {@code BookDetailsPanel} displays the details of {@code expectedBook} correctly.
     */
    private void assertDetailsPanelDisplaysBook(Book expectedBook) {
        guiRobot.pauseForHuman();
        GuiTestAssert.assertDetailsPanelDisplaysBook(expectedBook, bookDetailsPanelHandle);
    }
}
