package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Task;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

//@@author YuanQQLer
/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivities {

    public static final Task ASSIGNMENT1 = new TaskBuilder().withName("CS2101Assignment")
            .withRemark(" ")
            .withDateTime("04/03/2018 23:59")
            .withTags("CS2101").build();
    public static final Task ASSIGNMENT2 = new TaskBuilder().withName("CS2102Assignment")
            .withRemark(" ")
            .withDateTime("15/03/2018 23:59")
            .withTags("CS2102").build();
    public static final Task QUIZ = new TaskBuilder().withName("CS2101Quiz")
            .withDateTime("19/03/2018 23:59")
            .withRemark("IVLE Quiz").build();
    public static final Event CCA = new EventBuilder().withName("CCA")
            .withStartDateTime("01/04/2018 20:00")
            .withEndDateTime("01/04/2018 21:00")
            .withLocation("Campus")
            .withRemark("nil").build();
    public static final Event CIP = new EventBuilder().withName("CIP")
            .withStartDateTime("02/04/2018 08:00")
            .withEndDateTime("02/04/2018 12:00")
            .withLocation("michegan ave")
            .withRemark("nil")
            .withTags("CIP").build();
    public static final Event EXAM1 = new EventBuilder().withName("CS2101Exam")
            .withStartDateTime("28/04/2018 09:00")
            .withEndDateTime("28/04/2018 11:00")
            .withLocation("MPSH")
            .withRemark("nil")
            .withTags("CS2101").build();
    public static final Event IFG = new EventBuilder().withName("InterFacultyGame")
            .withStartDateTime("04/01/2018 20:00")
            .withEndDateTime("04/01/2018 22:00")
            .withLocation("MPSH 1")
            .withRemark("nil").build();

    // Manually added
    public static final Task ASSIGNMENT3 = new TaskBuilder().withName("CS2102Assignment")
            .withDateTime("01/04/2018 20:00")
            .withRemark("nil").build();
    public static final Event DEMO1 = new EventBuilder().withName("CS2102ProjectDemo")
            .withStartDateTime("04/04/2018 09:00")
            .withEndDateTime("04/04/2018 10:00")
            .withRemark("FinalDemo").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalActivities() {} // prevents instantiation

    /**
     * Returns an {@code DeskBoard} with all the typical activities.
     */
    public static DeskBoard getTypicalDeskBoard() {
        DeskBoard deskBoard = new DeskBoard();
        for (Activity activity : getTypicalActivities()) {
            try {
                deskBoard.addActivity(activity);
            } catch (DuplicateActivityException e) {
                throw new AssertionError("Not possible");
            }
        }
        return deskBoard;
    }

    public static List<Activity> getTypicalActivities() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT1, ASSIGNMENT2, QUIZ, CCA, CIP, EXAM1, IFG));
    }

    public static List<Activity> getTypicalTask() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT1, ASSIGNMENT2, QUIZ));
    }
}
