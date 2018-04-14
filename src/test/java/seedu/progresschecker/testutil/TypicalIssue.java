package seedu.progresschecker.testutil;

import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_ASSIGNEE_ANMIN;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_ASSIGNEE_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_BODY_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_BODY_TWO;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_LABEL_STORY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_LABEL_TASK;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_MILESTONE_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_MILESTONE_TWO;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_TITLE_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_TITLE_TWO;

import seedu.progresschecker.model.issues.Issue;

//@@author adityaa1998
/**
 * A utility class containing a list of {@code Issue} objects to be used in tests.
 */
public class TypicalIssue {

    public static final Issue TEST_ONE = new IssueBuilder().withTitle("Test one")
            .withAssignees("anminkang").withBody("Test 1 body")
            .withMilestone("v1.1").withLabels("test1").build();
    public static final Issue TEST_TWO = new IssueBuilder().withTitle("Test two")
            .withAssignees("adityaa1998").withBody("Test 2 body")
            .withMilestone("v1.2").withLabels("test2").build();
    public static final Issue TEST_THREE = new IssueBuilder().withTitle("Test three")
            .withAssignees("kush1509").withBody("Test 3 body")
            .withMilestone("v1.3").withLabels("test3").build();
    public static final Issue TEST_FOUR = new IssueBuilder().withTitle("Test four")
            .withBody("Test 4 body")
            .withMilestone("v1.3").withLabels("test4").build();
    public static final Issue TEST_FIVE = new IssueBuilder().withTitle("Test five")
            .withAssignees("anminkang")
            .withMilestone("v1.3").withLabels("test5").build();
    public static final Issue TEST_SIX = new IssueBuilder().withTitle("Test six")
            .withAssignees("anminkang").withBody("Test 6 body")
            .withLabels("test6").build();
    public static final Issue TEST_SEVEN = new IssueBuilder().withTitle("Test seven")
            .withAssignees("anminkang").withBody("Test 7 body")
            .withMilestone("v1.3").build();

    //Manually added - Issue's details found in {@code CommandTestUtil}
    public static final Issue ISSUE_ONE = new IssueBuilder().withTitle(VALID_TITLE_ONE)
            .withAssignees(VALID_ASSIGNEE_ANMIN)
            .withBody(VALID_BODY_ONE).withMilestone(VALID_MILESTONE_ONE)
            .withLabels(VALID_LABEL_TASK).build();
    public static final Issue ISSUE_TWO = new IssueBuilder().withTitle(VALID_TITLE_TWO)
            .withAssignees(VALID_ASSIGNEE_BOB)
            .withBody(VALID_BODY_TWO).withMilestone(VALID_MILESTONE_TWO)
            .withLabels(VALID_LABEL_STORY).build();
}
