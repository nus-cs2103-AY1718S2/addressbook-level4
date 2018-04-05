package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalGroups.COLLEAGUES;
import static seedu.address.testutil.TypicalGroups.FRIENDS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.UniqueGroupList;

//@@author SuxianAlicia
public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //@@author amad-person
    @Test
    public void equals() throws UniqueGroupList.DuplicateGroupException {
        UniqueGroupList firstGroupList = new UniqueGroupList();
        firstGroupList.add(FRIENDS);
        UniqueGroupList secondGroupList = new UniqueGroupList();
        secondGroupList.add(COLLEAGUES);

        // same object -> true
        assertTrue(firstGroupList.equals(firstGroupList));

        // different type -> false
        assertFalse(firstGroupList.equals(1));

        // different objects, same type -> false
        assertFalse(firstGroupList.equals(secondGroupList));
    }
    //@@author

    @Test
    public void asOrderInsensitiveList_compareListsWithSameItemsInDiffOrder_assertEqual()
            throws UniqueGroupList.DuplicateGroupException {
        UniqueGroupList firstGroupList = new UniqueGroupList();
        firstGroupList.add(FRIENDS);
        firstGroupList.add(COLLEAGUES);
        UniqueGroupList secondGroupList = new UniqueGroupList();
        secondGroupList.add(COLLEAGUES);
        secondGroupList.add(FRIENDS);

        assertTrue(firstGroupList.equalsOrderInsensitive(secondGroupList));
    }

    //@@author amad-person
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asObservableList().remove(0);
    }

    @Test
    public void asUniqueList_addDuplicateGroup_throwsDuplicateGroupException()
            throws UniqueGroupList.DuplicateGroupException {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UniqueGroupList.DuplicateGroupException.class);
        uniqueGroupList.add(FRIENDS);
        uniqueGroupList.add(FRIENDS);
    }
    //@@author
}
