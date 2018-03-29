package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalBooks.ARTEMIS;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.BookReviewsPanelHandle;

public class BookReviewsPanelTest extends GuiUnitTest {

    private BookReviewsPanel bookReviewsPanel;
    private BookReviewsPanelHandle bookReviewsPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> bookReviewsPanel = new BookReviewsPanel(false));
        uiPartRule.setUiPart(bookReviewsPanel);
        bookReviewsPanelHandle = new BookReviewsPanelHandle(bookReviewsPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // hidden by default
        assertFalse(bookReviewsPanelHandle.isVisible());

        // associated reviews page of a book
        new GuiRobot().interact(() -> bookReviewsPanel.loadPageForBook(ARTEMIS));
        URL expectedPage = new URL(BookReviewsPanel.SEARCH_PAGE_URL.replace("%isbn", ARTEMIS.getIsbn().isbn));
        assertEquals(expectedPage, bookReviewsPanelHandle.getLoadedUrl());
    }

}
