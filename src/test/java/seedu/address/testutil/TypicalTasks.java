package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.Task;
import seedu.address.model.task.exceptions.TimingClashException;

/**
 * A utility class for Task. For now, this is customized to the personal task class
 */
//@@author a-shakra
public class TypicalTasks {

    public static final Task EXAMPLE1 = new TaskBuilder().withTuteeName(null).withDescription("exampleTask1")
            .withDuration("3h20m").withDateTime("02/05/2018 03:20").buildPersonalTask();
    public static final Task EXAMPLE2 = new TaskBuilder().withTuteeName(null).withDescription("exampleTask2")
            .withDuration("3h20m").withDateTime("02/04/2018 13:20").buildPersonalTask();
    public static final Task EXAMPLE3 = new TaskBuilder().withTuteeName(null).withDescription("exampleTask3")
            .withDuration("3h20m").withDateTime("02/06/2018 23:20").buildPersonalTask();
    public static final Task EXAMPLE4 = new TaskBuilder().withTuteeName(null).withDescription("exampleTask4")
            .withDuration("3h20m").withDateTime("02/07/2018 23:20").buildPersonalTask();

    private TypicalTasks() {} // prevents instantiation
    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (TimingClashException e) {
                throw new AssertionError("Timing Clash");
            }
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(EXAMPLE1, EXAMPLE2, EXAMPLE3));
    }

}
