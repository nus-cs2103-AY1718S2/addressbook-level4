package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.testutil.TypicalEvents.F1RACE;
import static seedu.address.testutil.TypicalGroups.GROUP_A;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalToDos.TODO_A;

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
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.ToDo;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();
    private final AddressBook amyNBobAddressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
        assertEquals(Collections.emptyList(), addressBook.getToDoList());
        assertEquals(Collections.emptyList(), addressBook.getGroupList());
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

        AddressBook newData2 = (AddressBook) SampleDataUtil.getSampleAddressBook();
        addressBook.resetData(newData2);
        assertEquals(newData2, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<ToDo> newToDos = Arrays.asList(TODO_A);
        List<Group> newGroups = Arrays.asList(GROUP_A);
        List<Event> newEvents = Arrays.asList(F1RACE);
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newToDos, newGroups, newEvents);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateToDos_throwsAssertionError() {
        // Repeat TODO_A twice
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<ToDo> newToDos = Arrays.asList(TODO_A, TODO_A);
        List<Group> newGroups = Arrays.asList(GROUP_A);
        List<Event> newEvents = Arrays.asList(F1RACE);
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newToDos, newGroups, newEvents);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateGroups_throwsAssertionError() {
        // Repeat GROUP_A twice
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<ToDo> newToDos = Arrays.asList(TODO_A);
        List<Group> newGroups = Arrays.asList(GROUP_A, GROUP_A);
        List<Event> newEvents = Arrays.asList(F1RACE);
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newToDos, newGroups, newEvents);

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
    public void getToDoList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getToDoList().remove(0);
    }

    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getToDoList().remove(0);
    }

    @Test
    public void removeTag_removeNonexistentTag_addressBookUnchanged() throws Exception {
        amyNBobAddressBook.removeTag(new Tag(VALID_TAG_UNUSED));

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        assertEquals(expectedAddressBook, amyNBobAddressBook);
    }

    @Test
    public void removeTag_tagUsedByMultiplePersons_tagRemoved() throws Exception {
        amyNBobAddressBook.removeTag(new Tag(VALID_TAG_FRIEND));

        Person expectedAmy = new PersonBuilder(AMY).withTags().build();
        Person expectedBob = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder()
                .withPerson(expectedAmy).withPerson(expectedBob).build();

        assertEquals(expectedAddressBook, amyNBobAddressBook);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<ToDo> todos = FXCollections.observableArrayList();
        private final ObservableList<Group> groups = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<? extends Tag> tags,
                        Collection<ToDo> todos, Collection<Group> groups, Collection<Event> events) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.todos.setAll(todos);
            this.groups.setAll(groups);
            this.events.setAll(events);
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
        public ObservableList<ToDo> getToDoList() {
            return todos;
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return groups;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
    }
}
