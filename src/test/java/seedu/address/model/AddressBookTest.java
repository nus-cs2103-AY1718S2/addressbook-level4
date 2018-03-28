package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalTasks.TASKONE;

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
import seedu.address.model.category.TaskCategory;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
        assertEquals(Collections.emptyList(), addressBook.getTaskList());
        assertEquals(Collections.emptyList(), addressBook.getTaskCategoryList());
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
        List<Task> newTasks = Arrays.asList();
        List<TaskCategory> newTaskCategories = new ArrayList<>();
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newTasks, newTaskCategories);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        List<Person> newPersons = Arrays.asList();
        List<Tag> newTags = new ArrayList<>();
        // Repeat TASKONE twice
        List<Task> newTasks = Arrays.asList(TASKONE, TASKONE);
        List<TaskCategory> newTaskCategories = new ArrayList<>(TASKONE.getTaskCategories());
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newTasks, newTaskCategories);

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
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskList().remove(0);
    }

    @Test
    public void getTaskCategoryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskCategoryList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<TaskCategory> taskCategories = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<? extends Tag> tags, Collection<Task> tasks,
                        Collection<? extends TaskCategory> taskCategories) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.tasks.setAll(tasks);
            this.taskCategories.setAll(taskCategories);
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
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<TaskCategory> getTaskCategoryList() {
            return taskCategories;
        }
    }

}
