package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.student.dashboard.Task;

//@@author yapni
/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task TASK_1 = new TaskBuilder().withName("Learn syntax").withDescription("Refer to website")
            .build();
    public static final Task TASK_2 = new TaskBuilder().withName("Practice ex22").withDescription("From problem set 4")
            .build();
    public static final Task TASK_3 = new TaskBuilder().withName("Learn framework").withDescription("Can be any one")
            .build();

    public List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TASK_1, TASK_2, TASK_3));
    }
}
