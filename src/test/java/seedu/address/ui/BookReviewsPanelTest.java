package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalBooks.ARTEMIS;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BookReviewsPanelHandle;
import seedu.address.commons.events.ui.ShowBookReviewsRequestEvent;

public class BookReviewsPanelTest extends GuiUnitTest {

    private BookReviewsPanel bookReviewsPanel;
    private BookReviewsPanelHandle bookReviewsPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> bookReviewsPanel = new BookReviewsPanel());
        uiPartRule.setUiPart(bookReviewsPanel);
        bookReviewsPanelHandle = new BookReviewsPanelHandle(bookReviewsPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // hidden by default
        assertFalse(bookReviewsPanelHandle.isVisible());

        // associated reviews page of a book
        postNow(new ShowBookReviewsRequestEvent(ARTEMIS));
        URL expectedPage = new URL(BookReviewsPanel.SEARCH_PAGE_URL.replace("%isbn", ARTEMIS.getIsbn().isbn));
        assertEquals(expectedPage, bookReviewsPanelHandle.getLoadedUrl());
    }

}
