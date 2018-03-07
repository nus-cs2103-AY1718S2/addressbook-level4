package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.FAMILY;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        AddressBookStub newData = new AddressBookStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    @Test
    public void removeTag_removeNonExistentTag_throwsTagNotFoundException() {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).build();
        Assert.assertThrows(TagNotFoundException.class, () -> testAddressBook.removeTag(FAMILY));
    }

    @Test
    public void removeTag_removeExistingTag_addressBookNotEqual() {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        try {
            testAddressBook.removeTag(FRIENDS);
        } catch (TagNotFoundException e) {
            fail("Tag " + FRIENDS.toString() + " should exist in testAddressBook.");
        }
        assertNotEquals(testAddressBook, expectedAddressBook);
    }

    @Test
    public void removeUnusedTag_removeNonExistentTagsWhenPersonIsDeleted_addressBookNotEqual() {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).build();

        // When person with tags unique to him is deleted, such tags should not exist anymore in master tag list.
        try {
            testAddressBook.removePerson(BENSON);
        } catch (PersonNotFoundException e) {
            fail("Person " + BENSON.getName() + " should exist in testAddressBook.");
        }
        assertEquals(testAddressBook, expectedAddressBook);

    }

    @Test
    public void removeUnusedTag_removeNonExistentTagsWhenPersonIsUpdated_addressBookNotEqual() {
        Person editedAlice = new PersonBuilder().withTags().build();
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(editedAlice).build();

        // When removing tags while updating a person, such tags should not exist anymore in master tag list.
        try {
            testAddressBook.updatePerson(ALICE, editedAlice);
        } catch (PersonNotFoundException e) {
            fail("Person " + ALICE.getName() + " should exist in testAddressBook.");
        } catch (DuplicatePersonException e) {
            fail("Duplicate Persons should not exist in testAddressBook.");
        }
        assertEquals(testAddressBook, expectedAddressBook);

    }
    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
