package seedu.organizer.model;

import static junit.framework.TestCase.assertEquals;
import static seedu.organizer.model.task.Priority.HIGHEST_SETTABLE_PRIORITY_LEVEL;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.UniqueTaskList;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.testutil.TaskBuilder;

public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }

    //@@author dominickenn
    @Test
    public void priorityAutoUpdateTest() throws DuplicateTaskException {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        LocalDate currentDate = LocalDate.now();

        // CurrentDate equals to AddedDate and not before Deadline
        Task taskCurrentDateEqualsToAddedDate = new TaskBuilder().withDeadline("2999-01-01")
                                            .withDateAdded(currentDate.toString()).build();
        Task expectedTaskCurrentDateEqualsToAddedDate = new TaskBuilder().withDeadline("2999-01-01")
                                                    .withDateAdded(currentDate.toString())
                                                    .withPriority(TaskBuilder.DEFAULT_PRIORITY).build();
        uniqueTaskList.add(taskCurrentDateEqualsToAddedDate);
        expectedUniqueTaskList.add(expectedTaskCurrentDateEqualsToAddedDate);
        assertEquals(uniqueTaskList, expectedUniqueTaskList);

        // Reset lists
        uniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList = new UniqueTaskList();

        // CurrentDate before Deadline
        Task taskCurrentDateBeforeDeadline = new TaskBuilder().withDeadline("2035-01-01")
                .withDateAdded("1900-01-01").build();
        Task expectedTaskCurrentDateBeforeDeadline = new TaskBuilder().withDeadline("2035-01-01")
                .withDateAdded("1900-01-01")
                .withPriority("8").build();
        uniqueTaskList.add(taskCurrentDateBeforeDeadline);
        expectedUniqueTaskList.add(expectedTaskCurrentDateBeforeDeadline);
        assertEquals(uniqueTaskList, expectedUniqueTaskList);

        // Reset lists
        uniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList = new UniqueTaskList();

        // CurrentDate after Deadline
        Task taskCurrentDateAfterDeadline = new TaskBuilder().withDeadline("1999-01-01")
                .withDateAdded("1950-01-01").build();
        Task expectedTaskCurrentDateAfterDeadline = new TaskBuilder().withDeadline("1999-01-01")
                .withDateAdded("1950-01-01")
                .withPriority(HIGHEST_SETTABLE_PRIORITY_LEVEL).build();
        uniqueTaskList.add(taskCurrentDateAfterDeadline);
        expectedUniqueTaskList.add(expectedTaskCurrentDateAfterDeadline);
        assertEquals(uniqueTaskList, expectedUniqueTaskList);
    }
    //@@author
}
