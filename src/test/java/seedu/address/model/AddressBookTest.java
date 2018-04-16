package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.PersonBuilder.DEFAULT_TAGS;
import static seedu.address.testutil.TypicalTasks.EXAMPLE1;
import static seedu.address.testutil.typicaladdressbook.TypicalAddressBookCompiler.getTypicalAddressBook1;
import static seedu.address.testutil.typicaladdressbook.TypicalPersons.ALICE;

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
import seedu.address.model.tag.Tag;

import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
        assertEquals(Collections.emptyList(), addressBook.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook1();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Task> newTasks = Arrays.asList(EXAMPLE1);
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newTasks);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }
    //@@author a-shakra
    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        // Repeat EXAMPLE1 twice
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Task> newTasks = Arrays.asList(EXAMPLE1, EXAMPLE1);
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newTasks);

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
    //@@author a-shakra
    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskList().remove(0);
    }
    //@@author
    @Test
    public void removeTag_existingTag_tagRemoved() throws Exception {
        Person person = new PersonBuilder().build();
        addressBook.addPerson(person);
        addressBook.removeTagFromPerson(new Tag(DEFAULT_TAGS), person);

        Person expectedPerson = new PersonBuilder().withTags().build();

        assertEquals(person, expectedPerson);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<? extends Tag> tags, Collection<Task> tasks) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
