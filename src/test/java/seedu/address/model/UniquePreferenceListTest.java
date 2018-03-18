package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPreferences.COMPUTERS;
import static seedu.address.testutil.TypicalPreferences.SHOES;
import static seedu.address.testutil.TypicalPreferences.VIDEO_GAMES;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.UniquePreferenceList;

public class UniquePreferenceListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() throws UniquePreferenceList.DuplicatePreferenceException {
        UniquePreferenceList firstPrefList = new UniquePreferenceList();
        firstPrefList.add(VIDEO_GAMES);
        UniquePreferenceList secondPrefList = new UniquePreferenceList();
        secondPrefList.add(COMPUTERS);

        // same object -> true
        assertTrue(firstPrefList.equals(firstPrefList));

        // different type -> false
        assertFalse(firstPrefList.equals(1));

        // different objects, same type -> false
        assertFalse(firstPrefList.equals(secondPrefList));
    }

    @Test
    public void asOrderInsensitiveList_compareListsWithSameItemsInDiffOrder_assertEqual()
            throws UniquePreferenceList.DuplicatePreferenceException {
        UniquePreferenceList firstPrefList = new UniquePreferenceList();
        firstPrefList.add(VIDEO_GAMES);
        firstPrefList.add(COMPUTERS);
        UniquePreferenceList secondPrefList = new UniquePreferenceList();
        secondPrefList.add(COMPUTERS);
        secondPrefList.add(VIDEO_GAMES);

        assertTrue(firstPrefList.equalsOrderInsensitive(secondPrefList));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePreferenceList uniquePreferenceList = new UniquePreferenceList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePreferenceList.asObservableList().remove(0);
    }

    @Test
    public void asUniqueList_addDuplicateOrder_throwsDuplicateOrderException()
            throws UniquePreferenceList.DuplicatePreferenceException {
        UniquePreferenceList uniquePrefList = new UniquePreferenceList();
        thrown.expect(UniquePreferenceList.DuplicatePreferenceException.class);
        uniquePrefList.add(SHOES);
        uniquePrefList.add(SHOES);
    }
}
