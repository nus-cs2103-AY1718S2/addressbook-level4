package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_WITHOUT_TAG;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BENSON_WITH_FRIENDS_TAG_REMOVED;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.CARL_WITHOUT_TAG;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalPersons;

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
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
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
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void deleteTag_tagNotFound_throwsTagNotFoundException() throws Exception {
        AddressBook addressBook = TypicalPersons.getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        thrown.expect(TagNotFoundException.class);
        modelManager.deleteTag(new Tag("family"));
    }

    @Test
    public void deleteTag_tagIsFound_tagsDeleted() throws Exception {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(CARL).build();
        UserPrefs userPrefs = new UserPrefs();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE_WITHOUT_TAG)
                .withPerson(BENSON_WITH_FRIENDS_TAG_REMOVED).withPerson(CARL_WITHOUT_TAG).build();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        modelManager.deleteTag(new Tag("friends"));
        assertEquals(modelManager, new ModelManager(expectedAddressBook, userPrefs));
    }

    @Test
    public void sortPersonListAscOrder() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(GEORGE).build();
        UserPrefs userPrefs = new UserPrefs();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(GEORGE)
                .withPerson(ALICE).withPerson(BENSON).build();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        modelManager.sortPersonListAscOrder(SortCommand.SortField.RATING);
        assertEquals(modelManager, new ModelManager(expectedAddressBook, userPrefs));
    }

    @Test
    public void sortPersonListDescOrder() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(GEORGE).build();
        UserPrefs userPrefs = new UserPrefs();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(BENSON)
                .withPerson(ALICE).withPerson(GEORGE).build();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        modelManager.sortPersonListDescOrder(SortCommand.SortField.RATING);
        assertEquals(modelManager, new ModelManager(expectedAddressBook, userPrefs));
    }
}
