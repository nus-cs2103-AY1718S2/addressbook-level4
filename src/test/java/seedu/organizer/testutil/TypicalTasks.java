package seedu.organizer.testutil;

import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.organizer.model.Organizer;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final String KEYWORD_MATCHING_DO = "Do"; // A keyword that matches DO
    public static final String KEYWORD_MATCHING_REVISION = "Revision"; // A keyword that matches REVISION
    public static final String KEYWORD_MATCHING_SPRING = "Spring"; // A keyword that matches SPRING

    private static String currentDate = LocalDate.now().toString();

    public static final Task GROCERY = new TaskBuilder().withName("Grocery")
            .withDescription("Go to NTUC").withDeadline("2019-03-11")
            .withDateAdded(currentDate).withPriority("0")
            .withTags("friends").build();
    public static final Task SPRINGCLEAN = new TaskBuilder().withName("Spring cleaning")
            .withDescription("Clean the entire house except the study room")
            .withDeadline("2019-09-08").withDateAdded(currentDate).withPriority("1")
            .withTags("owesMoney", "friends")
            .withStatus(true).build();
    public static final Task PREPAREBREAKFAST = new TaskBuilder().withName("Prepare breakfast").withPriority("2")
            .withDeadline("2019-11-12").withDateAdded(currentDate)
            .withDescription("French Toast with coffee").build();
    public static final Task HOMEWORK = new TaskBuilder().withName("Do homework").withPriority("3")
            .withDeadline("2019-03-21").withDateAdded(currentDate)
            .withDescription("CS2103T, CS2101 and MA1101R").withStatus(true).build();
    public static final Task PROJECT = new TaskBuilder().withName("Do project").withPriority("4")
            .withDeadline("2019-09-14").withDateAdded(currentDate)
            .withDescription("Finish writing script for OP1").build();
    public static final Task REVISION = new TaskBuilder().withName("Revision").withPriority("5")
            .withDeadline("2019-04-05").withDateAdded(currentDate)
            .withDescription("Revise for CS2106 midterms").withStatus(true).build();
    public static final Task MOCKEXAM = new TaskBuilder().withName("Mock exam").withPriority("6")
            .withDeadline("2019-05-23").withDateAdded(currentDate)
            .withDescription("Mock exam for CS1101S - Revise!").addSubtask("Study").build();

    // Manually added
    public static final Task MAKEPRESENT = new TaskBuilder().withName("Make present").withPriority("7")
            .withDeadline("2019-08-06").withDateAdded(currentDate)
            .withDescription("Make photo frame for Denise").build();
    public static final Task INTERVIEWPREP = new TaskBuilder().withName("Interview prep").withPriority("8")
            .withDeadline("2019-04-05").withDateAdded(currentDate)
            .withDescription("Prepare technical questions").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task EXAM = new TaskBuilder().withName(VALID_NAME_EXAM).withPriority(VALID_PRIORITY_EXAM)
            .withDeadline(VALID_DEADLINE_EXAM).withDateAdded(currentDate)
            .withDescription(VALID_DESCRIPTION_EXAM)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Task STUDY = new TaskBuilder().withName(VALID_NAME_STUDY).withPriority(VALID_PRIORITY_STUDY)
            .withDeadline(VALID_DEADLINE_STUDY).withDateAdded(currentDate)
            .withDescription(VALID_DESCRIPTION_STUDY).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns an {@code Organizer} with all the typical tasks.
     */
    public static Organizer getTypicalOrganizer() {
        Organizer ab = new Organizer();
        for (Task task : getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (DuplicateTaskException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(GROCERY, SPRINGCLEAN,
                PREPAREBREAKFAST, HOMEWORK, PROJECT, REVISION, MOCKEXAM));
    }
}
