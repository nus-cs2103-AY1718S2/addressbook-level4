package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_CATEGORY_PERSONAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_CATEGORY_WORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DUE_DATE_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DUE_DATE_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_STATUS_TASKSECOND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

//@@author CYX28
/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task TASKONE = new TaskBuilder().withTaskName("TaskOne")
            .withTaskPriority("medium")
            .withTaskDescription("Tasks to be done for task 1")
            .withTaskDueDate("2018-06-15")
            .withTaskStatus("undone")
            .withTaskCategories("work").build();
    public static final Task TASKTWO = new TaskBuilder().withTaskName("TaskTwo")
            .withTaskPriority("high")
            .withTaskDescription("Agenda for task 2")
            .withTaskDueDate("2018-03-28")
            .withTaskStatus("undone")
            .withTaskCategories("personal").build();
    public static final Task TASKTHREE = new TaskBuilder().withTaskName("TaskThree")
            .withTaskPriority("low")
            .withTaskDescription("Purchase office supplies")
            .withTaskDueDate("2018-04-10")
            .withTaskStatus("done")
            .withTaskCategories("work").build();

    // Manuall added - Task's details found in {@code CommandTestUtil}
    public static final Task TASKFIRST = new TaskBuilder().withTaskName(VALID_TASK_NAME_TASKFIRST)
            .withTaskPriority(VALID_TASK_PRIORITY_TASKFIRST).withTaskDescription(VALID_TASK_DESCRIPTION_TASKFIRST)
            .withTaskDueDate(VALID_TASK_DUE_DATE_TASKFIRST).withTaskStatus(VALID_TASK_STATUS_TASKFIRST)
            .withTaskCategories(VALID_TASK_CATEGORY_WORK).build();
    public static final Task TASKSECOND = new TaskBuilder().withTaskName(VALID_TASK_NAME_TASKSECOND)
            .withTaskPriority(VALID_TASK_PRIORITY_TASKSECOND).withTaskDescription(VALID_TASK_DESCRIPTION_TASKSECOND)
            .withTaskDueDate(VALID_TASK_DUE_DATE_TASKSECOND).withTaskStatus(VALID_TASK_STATUS_TASKSECOND)
            .withTaskCategories(VALID_TASK_CATEGORY_PERSONAL).build();

    // Manually added
    public static final Task TASKFOUR = new TaskBuilder().withTaskName("Task 4")
            .withTaskPriority("medium")
            .withTaskDescription("Work in progress: Development and documentation")
            .withTaskDueDate("2018-05-10")
            .withTaskStatus("undone").build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns a {@code TaskBook} with all the typical tasks.
     */
    public static TaskBook getTypicalTaskBook() {
        TaskBook tb = new TaskBook();
        for (Task task : TypicalTasks.getTypicalTasks()) {
            try {
                tb.addTask(task);
            } catch (DuplicateTaskException dte) {
                throw new AssertionError("not possible");
            }
        }

        return tb;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TASKONE, TASKTWO, TASKTHREE));
    }

}
