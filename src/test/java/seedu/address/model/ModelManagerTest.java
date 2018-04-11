package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACTIVITY;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT1;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT2;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT3;
import static seedu.address.testutil.TypicalActivities.DEMO1;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.activity.NameContainsKeywordsPredicate;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.testutil.DeskBoardBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredActivitiesList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredActivityList().remove(0);
    }

    @Test
    public void addActivities_validDeskBoard_success() throws DuplicateActivityException {
        ModelManager modelManager = new ModelManager();
        DeskBoard deskBoard = new DeskBoard();
        deskBoard.addActivity(ASSIGNMENT3);
        deskBoard.addActivity(DEMO1);
        modelManager.addActivities(deskBoard);
        assertEquals(deskBoard, modelManager.getDeskBoard());
    }


    @Test
    public void equals() {
        DeskBoard addressBook = new DeskBoardBuilder().withActivity(ASSIGNMENT1).withActivity(ASSIGNMENT2).build();
        DeskBoard differentAddressBook = new DeskBoard();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ASSIGNMENT1.getName().fullName.split("\\s+");
        modelManager.updateFilteredActivityList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITY);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setDeskBoardName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
