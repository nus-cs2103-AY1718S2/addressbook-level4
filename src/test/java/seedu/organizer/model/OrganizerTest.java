package seedu.organizer.model;

import static org.junit.Assert.assertEquals;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.EXAM;
import static seedu.organizer.testutil.TypicalTasks.GROCERY;
import static seedu.organizer.testutil.TypicalTasks.REVISION;
import static seedu.organizer.testutil.TypicalTasks.SPRINGCLEAN;
import static seedu.organizer.testutil.TypicalTasks.STUDY;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

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
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.User;
import seedu.organizer.testutil.OrganizerBuilder;
import seedu.organizer.testutil.TaskBuilder;

public class OrganizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Organizer organizer = new Organizer();
    private final Organizer organizerWithStudyAndExam = new OrganizerBuilder().withTask(STUDY)
            .withTask(EXAM).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), organizer.getTaskList());
        assertEquals(Collections.emptyList(), organizer.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        organizer.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyOrganizer_replacesData() {
        Organizer newData = getTypicalOrganizer();
        organizer.resetData(newData);
        assertEquals(newData, organizer);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        // Repeat GROCERY twice
        List<Task> newTasks = Arrays.asList(GROCERY, GROCERY);
        List<Tag> newTags = new ArrayList<>(GROCERY.getTags());
        List<User> users = Arrays.asList(new User("admin", "admin"));
        OrganizerStub newData = new OrganizerStub(newTasks, newTags, users);

        thrown.expect(AssertionError.class);
        organizer.resetData(newData);
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        organizer.getTaskList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        organizer.getTagList().remove(0);
    }

    //@@author natania-reused
    @Test
    public void updateTask_detailsChanged_tasksAndTagsListUpdated() throws Exception {
        Organizer organizerUpdatedToExam = new OrganizerBuilder().withTask(STUDY).build();
        organizerUpdatedToExam.updateTask(STUDY, EXAM);

        Organizer expectedOrganizer = new OrganizerBuilder().withTask(EXAM).build();

        assertEquals(expectedOrganizer, organizerUpdatedToExam);
    }

    @Test
    public void removeTag_nonExistentTag_organizerUnchanged() throws Exception {
        organizerWithStudyAndExam.removeTag(new Tag(VALID_TAG_UNUSED));

        Organizer expectedOrganizer = new OrganizerBuilder().withTask(STUDY).withTask(EXAM).build();

        assertEquals(expectedOrganizer, organizerWithStudyAndExam);
    }

    @Test
    public void removeTag_tagUsedByMultipleTasks_tagRemoved() throws Exception {
        organizerWithStudyAndExam.removeTag(new Tag(VALID_TAG_FRIEND));

        Task examWithoutFriendTag = new TaskBuilder(EXAM).withTags().build();
        Task studyWithoutFriendTag = new TaskBuilder(STUDY).withTags(VALID_TAG_HUSBAND).build();
        Organizer expectedOrganizer = new OrganizerBuilder().withTask(studyWithoutFriendTag)
                .withTask(examWithoutFriendTag).build();

        assertEquals(expectedOrganizer, organizerWithStudyAndExam);
    }

    //@@author dominickenn
    @Test
    public void addTask_alwaysSorted() throws DuplicateTaskException {
        Organizer addRevision = new OrganizerBuilder().withTask(STUDY)
                .withTask(EXAM).build();
        addRevision.addTask(REVISION);
        Organizer expectedOrganizer = new OrganizerBuilder().withTask(STUDY)
                .withTask(REVISION).withTask(EXAM).build();
        assertEquals(expectedOrganizer, addRevision);
    }

    @Test
    public void editTask_alwaysSorted() throws DuplicateTaskException, TaskNotFoundException {
        Organizer editExam = new OrganizerBuilder().withTask(STUDY)
                .withTask(REVISION).withTask(EXAM).build();
        editExam.updateTask(EXAM, SPRINGCLEAN);
        Organizer expectedOrganizer = new OrganizerBuilder().withTask(STUDY)
                .withTask(REVISION).withTask(SPRINGCLEAN).build();
        assertEquals(expectedOrganizer, editExam);
    }
    //@@author

    /**
     * A stub ReadOnlyOrganizer whose tasks and tags lists can violate interface constraints.
     */
    private static class OrganizerStub implements ReadOnlyOrganizer {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<User> users = FXCollections.observableArrayList();

        OrganizerStub(Collection<Task> tasks, Collection<? extends Tag> tags, Collection<? extends User> users) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
            this.users.setAll(users);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Task> getCurrentUserTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<User> getUserList() {
            return users;
        }

        @Override
        public User getCurrentLoggedInUser() {
            return ADMIN_USER;
        }
    }

}
