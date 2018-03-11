package seedu.organizer.testutil;

import static seedu.organizer.logic.commands.CommandTestUtil.VALID_ADDRESS_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_ADDRESS_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_EMAIL_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_EMAIL_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

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

    public static final Task GROCERY = new TaskBuilder().withName("Grocery")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPriority("0")
            .withTags("friends").build();
    public static final Task SPRINGCLEAN = new TaskBuilder().withName("Spring cleaning")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPriority("1")
            .withTags("owesMoney", "friends").build();
    public static final Task PREPAREBREAKFAST = new TaskBuilder().withName("Prepare breakfast").withPriority("2")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Task HOMEWORK = new TaskBuilder().withName("Do homework").withPriority("3")
            .withEmail("cornelia@example.com").withAddress("10th street").build();
    public static final Task PROJECT = new TaskBuilder().withName("Do project").withPriority("4")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Task REVISION = new TaskBuilder().withName("Revision").withPriority("5")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Task MOCKEXAM = new TaskBuilder().withName("Mock exam").withPriority("6")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Task MAKEPRESENT = new TaskBuilder().withName("Make present").withPriority("7")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Task INTERVIEWPREP = new TaskBuilder().withName("Interview prep").withPriority("8")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task EXAM = new TaskBuilder().withName(VALID_NAME_EXAM).withPriority(VALID_PRIORITY_EXAM)
            .withEmail(VALID_EMAIL_EXAM).withAddress(VALID_ADDRESS_EXAM).withTags(VALID_TAG_FRIEND).build();
    public static final Task STUDY = new TaskBuilder().withName(VALID_NAME_STUDY).withPriority(VALID_PRIORITY_STUDY)
            .withEmail(VALID_EMAIL_STUDY).withAddress(VALID_ADDRESS_STUDY).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_SPRING = "Spring"; // A keyword that matches SPRING
    public static final String KEYWORD_MATCHING_DO = "Do"; // A keyword that matches DO

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
