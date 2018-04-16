package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Comparator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.testutil.TypicalTasks;

//@@author CYX28
public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }

    @Test
    public void sortByStatusDueDateAndPriority_undoneToDoneDateInAscOrderAndPriorityHighToLow_success() {
        // Setup actual result
        TaskBook taskBook = TypicalTasks.getTypicalTaskBook();
        taskBook.sortTasksByStatusDueDateAndPriority();
        ObservableList<Task> actualTaskList = taskBook.getOriginalTaskList();

        // Setup expected result
        List<Task> taskList = TypicalTasks.getTypicalTasks();

        taskList.sort(Comparator.comparing(Task::getTaskStatus, (s1, s2) -> {
            return TaskStatus.STATUS_ORDER.indexOf(s1.value.toLowerCase())
                    - TaskStatus.STATUS_ORDER.indexOf(s2.value.toLowerCase());
        }).thenComparing(Comparator.comparing(Task::getTaskDueDate, (dd1, dd2) -> {
            return dd1.value.compareTo(dd2.value);
        })).thenComparing(Comparator.comparing(Task::getTaskPriority, (p1, p2) -> {
            return TaskPriority.PRIORITY_ORDER.indexOf(p1.value.toLowerCase())
                    - TaskPriority.PRIORITY_ORDER.indexOf(p2.value.toLowerCase());
        })));

        ObservableList<Task> expectedTaskList = FXCollections.observableList(taskList);

        assertEquals(actualTaskList, expectedTaskList);
    }

    @Test
    public void sortByStatusDueDateAndPriority_doneToUndoneDateInAscOrderAndPriorityHighToLow_fail() {
        // Setup actual result
        TaskBook taskBook = TypicalTasks.getTypicalTaskBook();
        taskBook.sortTasksByStatusDueDateAndPriority();
        ObservableList<Task> actualTaskList = taskBook.getOriginalTaskList();

        // Setup expected result
        List<Task> taskList = TypicalTasks.getTypicalTasks();

        taskList.sort(Comparator.comparing(Task::getTaskStatus, (s1, s2) -> {
            return TaskStatus.STATUS_ORDER.indexOf(s1.value.toLowerCase())
                    - TaskStatus.STATUS_ORDER.indexOf(s2.value.toLowerCase());
        }).reversed().thenComparing(Comparator.comparing(Task::getTaskDueDate, (dd1, dd2) -> {
            return dd1.value.compareTo(dd2.value);
        })).thenComparing(Comparator.comparing(Task::getTaskPriority, (p1, p2) -> {
            return TaskPriority.PRIORITY_ORDER.indexOf(p1.value.toLowerCase())
                    - TaskPriority.PRIORITY_ORDER.indexOf(p2.value.toLowerCase());
        })));

        ObservableList<Task> expectedTaskList = FXCollections.observableList(taskList);

        assertNotEquals(actualTaskList, expectedTaskList);
    }

    @Test
    public void sortByStatusDueDateAndPriority_undoneToDoneDateInDscOrderAndPriorityHighToLow_fail() {
        // Setup actual result
        TaskBook taskBook = TypicalTasks.getTypicalTaskBook();
        taskBook.sortTasksByStatusDueDateAndPriority();
        ObservableList<Task> actualTaskList = taskBook.getOriginalTaskList();

        // Setup expected result
        List<Task> taskList = TypicalTasks.getTypicalTasks();

        taskList.sort(Comparator.comparing(Task::getTaskStatus, (s1, s2) -> {
            return TaskStatus.STATUS_ORDER.indexOf(s1.value.toLowerCase())
                    - TaskStatus.STATUS_ORDER.indexOf(s2.value.toLowerCase());
        }).thenComparing(Comparator.comparing(Task::getTaskDueDate, (dd1, dd2) -> {
            return dd1.value.compareTo(dd2.value);
        })).reversed().thenComparing(Comparator.comparing(Task::getTaskPriority, (p1, p2) -> {
            return TaskPriority.PRIORITY_ORDER.indexOf(p1.value.toLowerCase())
                    - TaskPriority.PRIORITY_ORDER.indexOf(p2.value.toLowerCase());
        })));

        ObservableList<Task> expectedTaskList = FXCollections.observableList(taskList);

        assertNotEquals(actualTaskList, expectedTaskList);
    }

    @Test
    public void sortByStatusDueDateAndPriority_undoneToDoneDateInAscOrderAndPriorityLowToHigh_fail() {
        // Setup actual result
        TaskBook taskBook = TypicalTasks.getTypicalTaskBook();
        taskBook.sortTasksByStatusDueDateAndPriority();
        ObservableList<Task> actualTaskList = taskBook.getOriginalTaskList();

        // Setup expected result
        List<Task> taskList = TypicalTasks.getTypicalTasks();

        taskList.sort(Comparator.comparing(Task::getTaskStatus, (s1, s2) -> {
            return TaskStatus.STATUS_ORDER.indexOf(s1.value.toLowerCase())
                    - TaskStatus.STATUS_ORDER.indexOf(s2.value.toLowerCase());
        }).thenComparing(Comparator.comparing(Task::getTaskDueDate, (dd1, dd2) -> {
            return dd1.value.compareTo(dd2.value);
        })).thenComparing(Comparator.comparing(Task::getTaskPriority, (p1, p2) -> {
            return TaskPriority.PRIORITY_ORDER.indexOf(p1.value.toLowerCase())
                    - TaskPriority.PRIORITY_ORDER.indexOf(p2.value.toLowerCase());
        })).reversed());

        ObservableList<Task> expectedTaskList = FXCollections.observableList(taskList);

        assertNotEquals(actualTaskList, expectedTaskList);
    }

}
