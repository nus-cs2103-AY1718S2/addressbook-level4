package seedu.progresschecker.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.progresschecker.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.progresschecker.testutil.TypicalPersons.ALICE;
import static seedu.progresschecker.testutil.TypicalPersons.BENSON;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.progresschecker.model.person.NameContainsKeywordsPredicate;
import seedu.progresschecker.testutil.ProgressCheckerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void equals() {
        ProgressChecker progressChecker = new ProgressCheckerBuilder().withPerson(ALICE).withPerson(BENSON).build();
        ProgressChecker differentProgressChecker = new ProgressChecker();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(progressChecker, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(progressChecker, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different progressChecker -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentProgressChecker, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(progressChecker, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setProgressCheckerName("differentName");
        assertTrue(modelManager.equals(new ModelManager(progressChecker, differentUserPrefs)));
    }
}
