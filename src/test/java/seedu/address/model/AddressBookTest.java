package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTeams.CHELSEA;

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
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();
    private final AddressBook addressBookWithBobAndAmy = new AddressBookBuilder().withPerson(BOB)
            .withPerson(AMY).build();

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
        List<Team> newTeams = Arrays.asList(CHELSEA);
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newTeams);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTeams_throwsAssertionError() {
        // Repeat CHELSEA twice
        List<Person> newPersons = Arrays.asList(BOB);
        List<Tag> newTags = new ArrayList<>(BOB.getTags());
        List<Team> newTeams = Arrays.asList(CHELSEA, CHELSEA);
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newTeams);

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
    public void updatePerson_detailsChanged_personsAndTagsListUpdated() throws Exception {
        AddressBook addressBookUpdatedToAmy = new AddressBookBuilder().withPerson(BOB).build();
        addressBookUpdatedToAmy.updatePerson(BOB, AMY);

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(AMY).build();

        assertEquals(expectedAddressBook, addressBookUpdatedToAmy);
    }

    @Test
    public void removePerson_nonExistentPerson_throwsPersonNotFoundException() throws Exception {
        thrown.expect(PersonNotFoundException.class);
        addressBookWithBobAndAmy.removePerson(ALICE);
    }

    @Test
    public void removeTag_nonExistentTag_addressBookUnchanged() throws Exception {
        addressBookWithBobAndAmy.removeTag(new Tag(VALID_TAG_UNUSED));

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(BOB).withPerson(AMY).build();

        assertEquals(expectedAddressBook, addressBookWithBobAndAmy);
    }

    @Test
    public void removeTag_tagUsedByMultiplePersons_tagRemoved() throws Exception {
        addressBookWithBobAndAmy.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(bobWithoutFriendTag)
                .withPerson(amyWithoutFriendTag).build();

        assertEquals(expectedAddressBook, addressBookWithBobAndAmy);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Team> teams = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<? extends Tag> tags, Collection<Team> teams) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.teams.setAll(teams);
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
        public ObservableList<Team> getTeamList() {
            return teams;
        }
    }

}
