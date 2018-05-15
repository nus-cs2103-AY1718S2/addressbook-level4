package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalBooks.getTypicalBooks;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysBook;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BookCardHandle;
import guitests.guihandles.BookListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToBookListIndexRequestEvent;
import seedu.address.model.book.Book;

public class BookListPanelTest extends GuiUnitTest {
    private static final ObservableList<Book> TYPICAL_BOOKS =
            FXCollections.observableList(getTypicalBooks());

    private static final JumpToBookListIndexRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToBookListIndexRequestEvent(INDEX_SECOND_BOOK);

    private BookListPanelHandle bookListPanelHandle;

    @Before
    public void setUp() {
        BookListPanel bookListPanel = new BookListPanel(TYPICAL_BOOKS);
        uiPartRule.setUiPart(bookListPanel);

        bookListPanelHandle = new BookListPanelHandle(getChildNode(bookListPanel.getRoot(),
                BookListPanelHandle.BOOK_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_BOOKS.size(); i++) {
            bookListPanelHandle.navigateToCard(TYPICAL_BOOKS.get(i));
            Book expectedBook = TYPICAL_BOOKS.get(i);
            BookCardHandle actualCard = bookListPanelHandle.getBookCardHandle(i).get();

            assertCardDisplaysBook(expectedBook, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToBookListIndexRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        BookCardHandle expectedCard = bookListPanelHandle.getBookCardHandle(INDEX_SECOND_BOOK.getZeroBased()).get();
        BookCardHandle selectedCard = bookListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
