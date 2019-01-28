package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

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
    public void getFilteredPetPatientList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPetPatientList().remove(0);
    }

    @Test
    public void getFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredAppointmentList().remove(0);
    }

    // Reused from https://github.com/se-edu/addressbook-level4/pull/790/files with minor modifications
    @Test
    public void deleteUnusedTag_modelUnchanged() throws Exception {
        AddressBook addressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_UNUSED));

        //unused tag removed, should remain the same
        assertTrue(modelManager.equals(modelManagerCopy));
    }

    // Reused from https://github.com/se-edu/addressbook-level4/pull/790/files with minor modifications
    @Test
    public void deleteTag_multiplePersons_modelChanged() throws Exception {
        AddressBook addressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook addressBookAfterChange =
                new AddressBookBuilder().withPerson(amyWithoutFriendTag).withPerson(bobWithoutFriendTag).build();
        ModelManager modelManagerAfterChange = new ModelManager(addressBookAfterChange, userPrefs);

        assertTrue(modelManager.equals(modelManagerAfterChange));
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
        Predicate<Person> namePredicate =  person -> Arrays.stream(keywords)
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        modelManager.updateFilteredPersonList(namePredicate);
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
