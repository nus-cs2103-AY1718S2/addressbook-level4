package seedu.address.testutil.typicaladdressbook;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Task;
import seedu.address.model.personal.PersonalTask;
import seedu.address.model.tutee.TuitionTask;
import seedu.address.testutil.TaskBuilder;


/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    public static final TuitionTask TASK_ALICE = new TaskBuilder().withTuteeName("Alice Pauline")
            .withDateTime("01/10/2018 10:00").withDuration("2h0m").withDescription("Calculus page 24")
            .buildTuitionTask();
    public static final TuitionTask TASK_BENSON = new TaskBuilder().withTuteeName("Benson Meier")
            .withDateTime("01/10/2018 14:30").withDuration("2h0m").withDescription("Math exam")
            .buildTuitionTask();
    public static final TuitionTask TASK_CARL = new TaskBuilder().withTuteeName("Carl Kurtz")
            .withDateTime("31/12/2018 09:15").withDuration("1h20m").withoutDescription()
            .buildTuitionTask();
    public static final PersonalTask TASK_GROCERRY_SHOPPING = new TaskBuilder()
            .withDateTime("25/04/2017 14:30").withDuration("1h0m").withDescription("grocery shopping")
            .buildPersonalTask();
    public static final PersonalTask TASK_YOGA = new TaskBuilder()
            .withDateTime("28/02/2019 14:30").withDuration("3h0m").withDescription("yoga")
            .buildPersonalTask();

    // Manually added - Task details found in {@code CommandTestUtil}
    public static final TuitionTask TASK_AMY = new TaskBuilder().withTuteeName(VALID_NAME_AMY)
            .withDateTime(VALID_DATE_TIME_AMY).withDuration(VALID_DURATION_AMY).withDescription(VALID_TASK_DESC_AMY)
            .buildTuitionTask();
    public static final TuitionTask TASK_BOB = new TaskBuilder().withTuteeName(VALID_NAME_BOB)
            .withDateTime(VALID_DATE_TIME_BOB).withDuration(VALID_DURATION_BOB).withDescription(VALID_TASK_DESC_BOB)
            .buildTuitionTask();

    private TypicalTasks() {} // prevents instantiation

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TASK_ALICE, TASK_BENSON, TASK_CARL, TASK_GROCERRY_SHOPPING, TASK_YOGA));
    }
}
