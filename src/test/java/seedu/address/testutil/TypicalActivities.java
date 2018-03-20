package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Activity} objects to be used in tests.
 */
public class TypicalActivities {

    public static final Activity ASSIGNMENT1 = new TaskBuilder().withName("CS2101Assignment")
            .withRemark(" ")
            .withDateTime("04/03/2018 23:59")
            .withTags("CS2101").build();
    public static final Activity ASSIGNMENT2 = new TaskBuilder().withName("CS2102Assignment")
            .withRemark(" ")
            .withDateTime("15/03/2018 23:59")
            .withTags("CS2102").build();
    public static final Activity QUIZ = new TaskBuilder().withName("CS2101Quiz")
            .withDateTime("19/03/2018 23:59")
            .withRemark("IVLE Quiz").build();
    public static final Activity CCA = new EventBuilder().withName("CCA")
            .withStartDateTime("01/04/2018 20:00")
            .withEndDateTime("01/04/2018 21:00")
            .withLocation("Campus")
            .withRemark("nil").build();
    public static final Activity CIP1 = new EventBuilder().withName("CIP")
            .withStartDateTime("02/04/2018 08:00")
            .withEndDateTime("02/04/2018 12:00")
            .withLocation("michegan ave")
            .withTags("CIP").build();
    public static final Activity EXAM1 = new EventBuilder().withName("CS2101Exam")
            .withStartDateTime("28/04/2018 09:00")
            .withEndDateTime("28/04/2018 11:00")
            .withLocation("MPSH")
            .withTags("CS2101").build();
    public static final Activity IFG = new EventBuilder().withName("InterFacultyGame")
            .withStartDateTime("04/01/2018 20:00")
            .withEndDateTime("04/01/2018 22:00")
            .withLocation("MPSH 1").build();

    // Manually added
    public static final Activity ASSIGNMENT3 = new TaskBuilder().withName("CS2102Assignment")
            .withDateTime("01/04/2018 20:00")
            .withRemark("nil").build();
    public static final Activity DEMO1 = new EventBuilder().withName("CS2102ProjectDemo")
            .withStartDateTime("04/04/2018 09:00")
            .withEndDateTime("04/04/2018 10:00")
            .withRemark("FinalDemo").build();

    // Manually added - Activity's details found in {@code CommandTestUtil}
    public static final Activity MA2108_HOMEWORK = new TaskBuilder().withName(VALID_NAME_MA2108_HOMEWORK).withDateTime(VALID_DATE_TIME_MA2108_HOMEWORK)
            .withRemark(VALID_REMARK_MA2108_HOMEWORK).withTags(VALID_TAG_CS2010).build();
    public static final Activity CS2010_QUIZ = new TaskBuilder().withName(VALID_NAME_CS2010_QUIZ).withDateTime(VALID_DATE_TIME_CS2010_QUIZ)
            .withRemark(VALID_REMARK_CS2010_QUIZ).withTags(VALID_TAG_MA2108, VALID_TAG_CS2010)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalActivities() {} // prevents instantiation

    /**
     * Returns an {@code DeskBoard} with all the typical persons.
     */
    public static DeskBoard getTypicalDeskBoard() {
        DeskBoard deskBoard = new DeskBoard();
        for (Activity activity : getTypicalActivitiess()) {
            try {
                deskBoard.addActivity(activity);
            } catch (DuplicateActivityException e) {
                throw new AssertionError("not possible");
            }
        }
        return deskBoard;
    }

    public static List<Activity> getTypicalActivitiess() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT1, ASSIGNMENT2, QUIZ, CCA, CIP1, EXAM1, IFG));
    }
}
