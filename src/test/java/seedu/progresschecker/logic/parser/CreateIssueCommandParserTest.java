package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.commands.CommandTestUtil.ASSIGNEE_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.ASSIGNEE_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.BODY_DESC_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.BODY_DESC_TWO;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_ASSIGNEE_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_BODY_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_LABEL_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.LABEL_DEC_STORY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.LABEL_DEC_TASK;
import static seedu.progresschecker.logic.commands.CommandTestUtil.MILESTONE_DESC_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.MILESTONE_DESC_TWO;
import static seedu.progresschecker.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.TITLE_DESC_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.TITLE_DESC_TWO;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_ASSIGNEE_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_ASSIGNEE_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_BODY_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_LABEL_STORY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_LABEL_TASK;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_MILESTONE_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_TITLE_ONE;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.progresschecker.logic.commands.CreateIssueCommand;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.issues.Labels;
import seedu.progresschecker.model.issues.Title;
import seedu.progresschecker.testutil.IssueBuilder;

public class CreateIssueCommandParserTest {
    private CreateIssueParser parser = new CreateIssueParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Issue expectedIssue = new IssueBuilder().withTitle(VALID_TITLE_ONE).withAssignees(VALID_ASSIGNEE_AMY)
                .withBody(VALID_BODY_ONE).withMilestone(VALID_MILESTONE_ONE)
                .withLabels(VALID_LABEL_STORY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_ONE + ASSIGNEE_DESC_AMY
                        + BODY_DESC_ONE + MILESTONE_DESC_ONE + LABEL_DEC_STORY,
                new CreateIssueCommand(expectedIssue));

        // multiple titles - last title accepted
        assertParseSuccess(parser, TITLE_DESC_TWO + TITLE_DESC_ONE + BODY_DESC_ONE + ASSIGNEE_DESC_AMY
                         + MILESTONE_DESC_ONE + LABEL_DEC_STORY,
                new CreateIssueCommand(expectedIssue));

        // multiple body - last body accepted
        assertParseSuccess(parser, TITLE_DESC_ONE + BODY_DESC_TWO + BODY_DESC_ONE + ASSIGNEE_DESC_AMY
                         + MILESTONE_DESC_ONE + LABEL_DEC_STORY,
                new CreateIssueCommand(expectedIssue));

        // multiple milestone - last milestone accepted
        assertParseSuccess(parser, TITLE_DESC_ONE + BODY_DESC_ONE + ASSIGNEE_DESC_AMY + MILESTONE_DESC_TWO
                        + MILESTONE_DESC_ONE + LABEL_DEC_STORY,
                new CreateIssueCommand(expectedIssue));


        // multiple assignees - all accepted
        Issue expectedIssueMultipleAssignees = new IssueBuilder().withTitle(VALID_TITLE_ONE)
                .withAssignees(VALID_ASSIGNEE_BOB, VALID_ASSIGNEE_AMY)
                .withBody(VALID_BODY_ONE).withMilestone(VALID_MILESTONE_ONE)
                .withLabels(VALID_LABEL_STORY).build();

        assertParseSuccess(parser, TITLE_DESC_ONE + ASSIGNEE_DESC_BOB + ASSIGNEE_DESC_AMY
                        + BODY_DESC_ONE + MILESTONE_DESC_ONE + LABEL_DEC_STORY,
                new CreateIssueCommand(expectedIssueMultipleAssignees));

        // multiple labels - all accepted
        Issue expectedIssueMultipleLabels = new IssueBuilder().withTitle(VALID_TITLE_ONE)
                .withAssignees(VALID_ASSIGNEE_BOB)
                .withBody(VALID_BODY_ONE).withMilestone(VALID_MILESTONE_ONE)
                .withLabels(VALID_LABEL_STORY, VALID_LABEL_TASK).build();

        assertParseSuccess(parser, TITLE_DESC_ONE + ASSIGNEE_DESC_BOB
                        + BODY_DESC_ONE + MILESTONE_DESC_ONE + LABEL_DEC_STORY + LABEL_DEC_TASK,
                new CreateIssueCommand(expectedIssueMultipleLabels));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero labels
        Issue expectedIssue = new IssueBuilder().withTitle(VALID_TITLE_ONE).withAssignees(VALID_ASSIGNEE_AMY)
                .withBody(VALID_BODY_ONE).withMilestone(VALID_MILESTONE_ONE)
                .withLabels().build();

        assertParseSuccess(parser, TITLE_DESC_ONE + BODY_DESC_ONE + MILESTONE_DESC_ONE + ASSIGNEE_DESC_AMY,
                new CreateIssueCommand(expectedIssue));

        // zero body
        Issue expectedIssueWithoutBody = new IssueBuilder().withTitle(VALID_TITLE_ONE).withAssignees(VALID_ASSIGNEE_AMY)
                .withBody("").withMilestone(VALID_MILESTONE_ONE)
                .withLabels(VALID_LABEL_STORY).build();

        assertParseSuccess(parser, TITLE_DESC_ONE + MILESTONE_DESC_ONE
                        + ASSIGNEE_DESC_AMY + LABEL_DEC_STORY,
                new CreateIssueCommand(expectedIssueWithoutBody));

        // zero assignees
        Issue expectedIssueWithoutAssignee = new IssueBuilder().withTitle(VALID_TITLE_ONE)
                .withAssignees(VALID_ASSIGNEE_AMY)
                .withBody(VALID_BODY_ONE).withMilestone(VALID_MILESTONE_ONE).withLabels().build();

        assertParseSuccess(parser, TITLE_DESC_ONE + BODY_DESC_ONE + MILESTONE_DESC_ONE + ASSIGNEE_DESC_AMY,
                new CreateIssueCommand(expectedIssueWithoutAssignee));

        // zero milestone
        Issue expectedIssueWithoutMilestone = new IssueBuilder().withTitle(VALID_TITLE_ONE)
                .withAssignees(VALID_ASSIGNEE_AMY)
                .withBody(VALID_BODY_ONE).withMilestone("")
                .withLabels(VALID_LABEL_STORY).build();

        //assertParseSuccess(parser, TITLE_DESC_ONE + BODY_DESC_ONE + ASSIGNEE_DESC_AMY + LABEL_DEC_STORY
        //        , new CreateIssueCommand(expectedIssueWithoutMilestone));

        // only title
        Issue expectedIssueWithOnlyTitle = new IssueBuilder().withTitle(VALID_TITLE_ONE).withAssignees()
                .withBody("").withMilestone("")
                .withLabels().build();

        //assertParseSuccess(parser, TITLE_DESC_ONE, new CreateIssueCommand(expectedIssue));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateIssueCommand.MESSAGE_USAGE);

        //missing title
        assertParseFailure(parser,  ASSIGNEE_DESC_AMY
                        + BODY_DESC_ONE + MILESTONE_DESC_ONE + LABEL_DEC_STORY,
                expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + BODY_DESC_ONE + ASSIGNEE_DESC_AMY + MILESTONE_DESC_ONE
                        + LABEL_DEC_STORY,
                Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid assignee
        assertParseFailure(parser, TITLE_DESC_ONE + BODY_DESC_ONE + INVALID_ASSIGNEE_DESC + MILESTONE_DESC_ONE
                        + LABEL_DEC_STORY,
                Assignees.MESSAGE_ASSIGNEES_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TITLE_DESC_ONE + BODY_DESC_ONE + ASSIGNEE_DESC_AMY + MILESTONE_DESC_ONE
                        + INVALID_LABEL_DESC,
                Labels.MESSAGE_LABEL_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + INVALID_BODY_DESC + ASSIGNEE_DESC_AMY + MILESTONE_DESC_ONE
                        + LABEL_DEC_STORY,
                Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + BODY_DESC_ONE + ASSIGNEE_DESC_AMY
                        + MILESTONE_DESC_ONE + LABEL_DEC_STORY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateIssueCommand.MESSAGE_USAGE));
    }


}
