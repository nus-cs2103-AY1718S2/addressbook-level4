package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.student.dashboard.Date;
import seedu.address.model.student.dashboard.Milestone;

//@@author yapni
/**
 * A utility class containing a list of {@code Milestone} objects to be used in tests.
 */
public class TypicalMilestones {

    public static final Milestone MILESTONE_1 = new MilestoneBuilder().withDescription("Arrays")
            .withDueDate(new Date("31/12/2018 23:59")).build();
    public static final Milestone MILESTONE_2 = new MilestoneBuilder().withDescription("Recursion")
            .withDueDate(new Date("01/12/2018 10:00")).build();
    public static final Milestone MILESTONE_3 = new MilestoneBuilder().withDescription("Strings")
            .withDueDate(new Date("22/05/2018 11:59")).build();

    public static List<Milestone> getTypicalMilestones() {
        return new ArrayList<>(Arrays.asList(MILESTONE_1, MILESTONE_2, MILESTONE_3));
    }
}
