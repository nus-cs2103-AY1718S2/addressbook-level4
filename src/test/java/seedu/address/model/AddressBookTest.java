package seedu.address.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
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
import seedu.address.model.pair.Pair;
import seedu.address.model.pair.exceptions.PairNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalPairs;


public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();
    private final AddressBook addressBookWithAmyandBob =
            new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();


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

    //@@author aussiroth
    @Test
    public void resetData_withDuplicatePairs_throwsAssertionError() {
        // Repeat RANDOM_PAIR_A twice
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Pair> newPairs = Arrays.asList(TypicalPairs.RANDOM_PAIR_A, TypicalPairs.RANDOM_PAIR_A);
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newPairs);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
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

    //@@author aussiroth
    @Test
    public void removePersonOrPair_doesNotExist_throwsNotFoundException() throws Exception {
        addressBook.addPerson(AMY);
        assertThrows(PersonNotFoundException.class, () -> addressBook.removePerson(BOB));
        assertThrows(PairNotFoundException.class, () -> addressBook.removePair(TypicalPairs.RANDOM_PAIR_A));
    }

    @Test
    public void checkHashCodeMethod() {
        AddressBook first = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        AddressBook copy = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        assertTrue(first.hashCode() == copy.hashCode());
    }

    //@@author
    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Pair> pairs = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        AddressBookStub(Collection<Person> persons, Collection<? extends Tag> tags, Collection<Pair> pairs) {
            this(persons, tags);
            this.pairs.setAll(pairs);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        //A dummy method, needs to be completed
        @Override
        public ObservableList<Pair> getPairList() {
            return pairs;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
