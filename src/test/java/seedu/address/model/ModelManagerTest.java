package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;
import static seedu.address.testutil.TypicalBooks.ARTEMIS;
import static seedu.address.testutil.TypicalBooks.BABYLON_ASHES;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.BookShelfBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredBookList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredBookList().remove(0);
    }

    @Test
    public void equals() {
        BookShelf bookShelf = new BookShelfBuilder().withBook(ARTEMIS).withBook(BABYLON_ASHES).build();
        BookShelf differentBookShelf = new BookShelf();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(bookShelf, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(bookShelf, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager == null);

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different bookShelf -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentBookShelf, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setBookShelfName("differentName");
        assertTrue(modelManager.equals(new ModelManager(bookShelf, differentUserPrefs)));
    }
}
