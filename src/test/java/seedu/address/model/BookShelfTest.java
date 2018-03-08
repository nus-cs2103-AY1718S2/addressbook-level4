package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalBooks.ARTEMIS;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;

public class BookShelfTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final BookShelf bookShelf = new BookShelf();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), bookShelf.getBookList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookShelf.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyBookShelf_replacesData() {
        ReadOnlyBookShelf newData = getTypicalBookShelf();
        bookShelf.resetData(newData);
        assertEquals(newData, bookShelf);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ARTEMIS twice
        List<Book> newBooks = Arrays.asList(ARTEMIS, ARTEMIS);
        BookShelfStub newData = new BookShelfStub(newBooks);

        thrown.expect(AssertionError.class);
        bookShelf.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        bookShelf.getBookList().remove(0);
    }

    /**
     * A stub ReadOnlyBookShelf whose book list can violate interface constraints.
     */
    private static class BookShelfStub implements ReadOnlyBookShelf {
        private final ObservableList<Book> books = FXCollections.observableArrayList();

        BookShelfStub(Collection<Book> books) {
            this.books.setAll(books);
        }

        @Override
        public ObservableList<Book> getBookList() {
            return books;
        }
    }

}
