package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalTasks.ALICE;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

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
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

public class OrganizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Organizer organizer = new Organizer();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), organizer.getPersonList());
        assertEquals(Collections.emptyList(), organizer.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        organizer.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Organizer newData = getTypicalAddressBook();
        organizer.resetData(newData);
        assertEquals(newData, organizer);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Task> newTasks = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        OrganizerStub newData = new OrganizerStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        organizer.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        organizer.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        organizer.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyOrganizer whose tasks and tags lists can violate interface constraints.
     */
    private static class OrganizerStub implements ReadOnlyOrganizer {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        OrganizerStub(Collection<Task> tasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Task> getPersonList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
