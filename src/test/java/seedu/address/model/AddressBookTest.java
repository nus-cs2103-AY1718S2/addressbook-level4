package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.FRIENDS_TAG;
import static seedu.address.testutil.TypicalTags.HUSBAND_TAG;

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
import seedu.address.model.person.Celebrity;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();
    private final AddressBook addressBookWithBobAndAmy = new AddressBookBuilder().withPerson(BOB)
            .withPerson(AMY).build();
    private final AddressBook addressBookWithBobAndAlice = new AddressBookBuilder().withPerson(BOB)
            .withPerson(ALICE).build();
    private final AddressBook addressBookWithAlice = new AddressBookBuilder().withPerson(ALICE).build();
    private final AddressBook addressBookWithAmy = new AddressBookBuilder().withPerson(AMY).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
    }

    //@@author WJY-norainu
    @Test
    public void countPersonsWithTag_countsFriendsTag_returnNumberOfPersonsWithFriendsTag() {
        assertEquals(addressBookWithAlice.countPersonsWithTag(FRIENDS_TAG), 1);
    }

    @Test
    public void countPersonsWithTag_countsHusbandTag_returnNumberOfPersonsWithHusbandTag() {
        assertEquals(addressBookWithAlice.countPersonsWithTag(HUSBAND_TAG), 0);
    }

    @Test
    public void removeTag_existentTag_tagRemoved() throws Exception {
        addressBookWithBobAndAmy.removeTag(new Tag(VALID_TAG_FRIEND));

        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(bobWithoutFriendTag)
                .withPerson(amyWithoutFriendTag).build();

        assertEquals(expectedAddressBook, addressBookWithBobAndAmy);
    }

    //@@author
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

    //@@author WJY-norainu
    @Test
    public void removePerson_theOnlyPersonWithHusbandTagRemoved_tagListUpdated() throws Exception {
        addressBookWithBobAndAlice.removePerson(BOB);

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).build();
        assertEquals(expectedAddressBook, addressBookWithBobAndAlice);
    }

    @Test
    public void updatePerson_theOnlyPersonWithFriendTagUpdated_tagListUpdated() throws Exception {
        Person aliceWithoutFriendTag = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        addressBookWithAlice.updatePerson(ALICE, aliceWithoutFriendTag);

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(aliceWithoutFriendTag).build();
        assertEquals(expectedAddressBook, addressBookWithAlice);
    }

    //@@author
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

    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ArrayList<Celebrity> celebrities = new ArrayList<>();

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

        @Override
        public ArrayList<Celebrity> getCelebritiesList() {
            return celebrities;
        }
    }

}
