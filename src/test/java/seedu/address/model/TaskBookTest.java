package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalTasks.TASKONE;
import static seedu.address.testutil.TypicalTasks.TASKTWO;

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
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;

//@@author CYX28
public class TaskBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskBook taskBook = new TaskBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskBook.getTaskList());
        assertEquals(Collections.emptyList(), taskBook.getTaskCategoryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskBook.resetData(null);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        // Repeat TASKONE twice
        List<Task> newTasks = Arrays.asList(TASKONE, TASKONE);
        List<TaskCategory> newTaskCategories = new ArrayList<>(TASKONE.getTaskCategories());
        TaskBookStub newData = new TaskBookStub(newTasks, newTaskCategories);

        thrown.expect(AssertionError.class);
        taskBook.resetData(newData);
    }

    @Test
    public void removeTask_taskNotFound_throwsTaskNotFoundException() throws TaskNotFoundException {
        List<Task> newTasks = Arrays.asList(TASKONE);
        List<TaskCategory> newTaskCategories = new ArrayList<>(TASKONE.getTaskCategories());
        TaskBookStub newData = new TaskBookStub(newTasks, newTaskCategories);

        thrown.expect(TaskNotFoundException.class);
        taskBook.removeTask(TASKTWO);
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        taskBook.getTaskList().remove(0);
    }

    @Test
    public void getTaskCategoryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        taskBook.getTaskCategoryList().remove(0);
    }

    /**
     * A stub ReadOnlyTaskBook whose tasks and categories lists can violate interface constraints.
     */
    private static class TaskBookStub implements ReadOnlyTaskBook {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<TaskCategory> taskCategories = FXCollections.observableArrayList();

        TaskBookStub(Collection<Task> tasks, Collection<? extends TaskCategory> taskCategories) {
            this.tasks.setAll(tasks);
            this.taskCategories.setAll(taskCategories);
        }

        @Override
        public ObservableList<Task> getOriginalTaskList() {
            return tasks;
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
