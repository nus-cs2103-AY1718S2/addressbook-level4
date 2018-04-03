package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_WITHOUT_TAG;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BENSON_WITH_FRIENDS_TAG_REMOVED;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.CARL_WITHOUT_TAG;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.AddressBookBuilder;

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
    public void sortDesc_sortByRating_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortDesc(SortCommand.SortField.RATING);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(BENSON).withPerson(ALICE)
                .withPerson(GEORGE).withPerson(FIONA).withPerson(ELLE).withPerson(DANIEL).withPerson(CARL).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortAsc_sortByRating_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortAsc(SortCommand.SortField.RATING);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(CARL).withPerson(DANIEL)
                .withPerson(ELLE).withPerson(FIONA).withPerson(GEORGE).withPerson(ALICE).withPerson(BENSON).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortDesc_sortByGradePointAverage_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortDesc(SortCommand.SortField.GPA);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(CARL).withPerson(ALICE)
                .withPerson(FIONA).withPerson(BENSON).withPerson(ELLE).withPerson(DANIEL).withPerson(GEORGE).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortAsc_sortByGradePointAverage_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortAsc(SortCommand.SortField.GPA);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(GEORGE).withPerson(DANIEL)
                .withPerson(ELLE).withPerson(BENSON).withPerson(FIONA).withPerson(ALICE).withPerson(CARL).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortDesc_sortByName_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortDesc(SortCommand.SortField.NAME);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(GEORGE).withPerson(FIONA)
                .withPerson(ELLE).withPerson(DANIEL).withPerson(CARL).withPerson(BENSON).withPerson(ALICE).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortAsc_sortByName_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortAsc(SortCommand.SortField.NAME);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(CARL).withPerson(DANIEL).withPerson(ELLE).withPerson(FIONA).withPerson(GEORGE).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    @Test
    public void removeTag_tagNotFound_throwsTagNotFoundException() throws Exception {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        thrown.expect(TagNotFoundException.class);
        addressBook.removeTag(new Tag("family"));
    }

    @Test
    public void removeTag_tagIsFound_tagRemoved() throws Exception {
        AddressBook newData = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(CARL).build();
        addressBook.resetData(newData);

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE_WITHOUT_TAG)
                .withPerson(BENSON_WITH_FRIENDS_TAG_REMOVED).withPerson(CARL_WITHOUT_TAG).build();

        addressBook.removeTag(new Tag("friends"));
        assertEquals(expectedAddressBook, addressBook);
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
