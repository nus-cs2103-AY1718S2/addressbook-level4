package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalGroups.FAMILY;
import static seedu.address.testutil.TypicalGroups.FRIENDS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPreferences.COMPUTERS;
import static seedu.address.testutil.TypicalPreferences.SHOES;

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
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.exceptions.GroupNotFoundException;
import seedu.address.model.tag.exceptions.PreferenceNotFoundException;
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
        assertEquals(Collections.emptyList(), addressBook.getGroupList());
        assertEquals(Collections.emptyList(), addressBook.getPreferenceList());
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
        List<Group> newGroups = new ArrayList<>(ALICE.getGroupTags());
        List<Preference> newPreferences = new ArrayList<>(ALICE.getPreferenceTags());
        AddressBookStub newData = new AddressBookStub(newPersons, newGroups, newPreferences);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getGroupList().remove(0);
    }

    @Test
    public void getPreferenceList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPreferenceList().remove(0);
    }

    @Test
    public void removeGroup_removeNonExistentGroup_throwsGroupNotFoundException() {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).build();
        Assert.assertThrows(GroupNotFoundException.class, () -> testAddressBook.removeGroup(FAMILY));
    }

    @Test
    public void removeGroup_removeExistingGroup_addressBookNotEqual() {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        try {
            testAddressBook.removeGroup(FRIENDS);
        } catch (GroupNotFoundException e) {
            fail("Group " + FRIENDS.toString() + " should exist in testAddressBook.");
        }
        assertNotEquals(testAddressBook, expectedAddressBook);
    }

    @Test
    public void removeUnusedGroup_removeNonExistentGroupsWhenPersonIsDeleted_addressBookEqual() {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).build();

        // When person with groups unique to him is deleted, such groups should not exist anymore in master group list.
        try {
            testAddressBook.removePerson(BENSON);
        } catch (PersonNotFoundException e) {
            fail("Person " + BENSON.getName() + " should exist in testAddressBook.");
        }
        assertEquals(testAddressBook, expectedAddressBook);
    }

    @Test
    public void removeUnusedGroups_removeNonExistentGroupsWhenPersonIsUpdated_addressBookEqual() {
        Person editedAlice = new PersonBuilder().withGroups().build();
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(editedAlice).build();

        // When removing groups while updating a person, such groups should not exist anymore in master group list.
        try {
            testAddressBook.updatePerson(ALICE, editedAlice);
        } catch (PersonNotFoundException e) {
            fail("Person " + ALICE.getName() + " should exist in testAddressBook.");
        } catch (DuplicatePersonException e) {
            fail("Duplicate Persons should not exist in testAddressBook.");
        }
        assertEquals(testAddressBook, expectedAddressBook);
    }

    @Test
    public void removePreference_removeNonExistentPreference_throwsPreferenceNotFoundException() {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).build();
        Assert.assertThrows(PreferenceNotFoundException.class, () -> testAddressBook.removePreference(COMPUTERS));
    }

    @Test
    public void removePreference_removeExistingPreference_addressBookNotEqual() {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        try {
            testAddressBook.removePreference(SHOES);
        } catch (PreferenceNotFoundException e) {
            fail("Preference " + SHOES.toString() + " should exist in testAddressBook.");
        }
        assertNotEquals(testAddressBook, expectedAddressBook);
    }

    @Test
    public void removeUnusedPreferences_removeNonExistentPreferencesWhenPersonIsDeleted_addressBookEqual() {
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).build();

        // When person with preferences unique to him is deleted,
        // such preferences should not exist anymore in master preference list.
        try {
            testAddressBook.removePerson(BENSON);
        } catch (PersonNotFoundException e) {
            fail("Person " + BENSON.getName() + " should exist in testAddressBook.");
        }
        assertEquals(testAddressBook, expectedAddressBook);

    }

    @Test
    public void removeUnusedPreferences_removeNonExistentPreferencesWhenPersonIsUpdated_addressBookEqual() {
        Person editedAlice = new PersonBuilder().withPreferences().build();
        AddressBook testAddressBook = new AddressBookBuilder().withPerson(ALICE).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(editedAlice).build();

        // When removing preferences while updating a person,
        // such preferences should not exist anymore in master preference list.
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
     * A stub ReadOnlyAddressBook whose persons, groups and preferences lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Group> groupTags = FXCollections.observableArrayList();
        private final ObservableList<Preference> prefTags = FXCollections.observableArrayList();
        private final ObservableList<Order> orders = FXCollections.observableArrayList();
        private final ObservableList<CalendarEvent> calendarEvents = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<? extends Group> groups,
                        Collection<? extends Preference> preferences) {
            this.persons.setAll(persons);
            this.groupTags.setAll(groups);
            this.prefTags.setAll(preferences);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return groupTags;
        }

        @Override
        public ObservableList<Preference> getPreferenceList() {
            return prefTags;
        }

        @Override
        public ObservableList<Order> getOrderList() {
            return orders;
        }

        @Override
        public ObservableList<CalendarEvent> getEventList() {
            return calendarEvents;
        }
    }
}
