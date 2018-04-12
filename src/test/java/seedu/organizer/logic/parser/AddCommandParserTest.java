package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.commons.core.Messages.MESSAGE_REPEATED_SAME_PREFIXES;
import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_REVISION;
import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_REVISION;
import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_REVISION;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.organizer.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_REVISION;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
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
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;
import seedu.organizer.testutil.TaskBuilder;

public class AddCommandParserTest {

    private Model model = new ModelManager();
    private AddCommandParser parser = new AddCommandParser();

    @Before
    public void setUp() {
        try {
            model.addUser(ADMIN_USER);
            model.loginUser(ADMIN_USER);
        } catch (DuplicateUserException du) {
            throw new AssertionError("There should not be any duplicate users");
        } catch (UserNotFoundException unf) {
            throw new AssertionError("Admin user should exist");
        } catch (CurrentlyLoggedInException cli) {
            throw new AssertionError("No user should be currently logged in");
        } catch (UserPasswordWrongException upw) {
            throw new AssertionError("Admin user password should not be wrong");
        }
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withName(VALID_NAME_STUDY).withPriority(VALID_PRIORITY_STUDY)
                .withDeadline(VALID_DEADLINE_STUDY).withDescription(VALID_DESCRIPTION_STUDY)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_STUDY + PRIORITY_DESC_STUDY + DEADLINE_DESC_STUDY
                + DESCRIPTION_DESC_STUDY + TAG_DESC_FRIEND, new AddCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder().withName(VALID_NAME_STUDY).withPriority(VALID_PRIORITY_STUDY)
                .withDeadline(VALID_DEADLINE_STUDY).withDescription(VALID_DESCRIPTION_STUDY)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + DEADLINE_DESC_STUDY + DESCRIPTION_DESC_STUDY
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedTaskMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedNoTagTask = new TaskBuilder().withName(VALID_NAME_EXAM).withPriority(VALID_PRIORITY_EXAM)
                .withDeadline(VALID_DEADLINE_EXAM).withDescription(VALID_DESCRIPTION_EXAM).withTags().build();
        assertParseSuccess(parser, NAME_DESC_EXAM + PRIORITY_DESC_EXAM + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM,
                new AddCommand(expectedNoTagTask));
        //@@author dominickenn
        // no priority
        Task expectedNoPriorityTask = new TaskBuilder().withName(VALID_NAME_EXAM)
                .withPriority(Priority.LOWEST_PRIORITY_LEVEL).withDeadline(VALID_DEADLINE_EXAM)
                .withDescription(VALID_DESCRIPTION_EXAM).withTags(VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_EXAM + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM + TAG_DESC_HUSBAND,
                new AddCommand(expectedNoPriorityTask));
        //@@author
        // no description
        Task expectedNoDescriptionTask = new TaskBuilder().withName(VALID_NAME_EXAM)
                .withPriority(VALID_PRIORITY_EXAM).withDeadline(VALID_DEADLINE_EXAM)
                .withDescription("").withTags(VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_EXAM + PRIORITY_DESC_EXAM + DEADLINE_DESC_EXAM
                + TAG_DESC_HUSBAND, new AddCommand(expectedNoDescriptionTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_STUDY + PRIORITY_DESC_STUDY + DEADLINE_DESC_STUDY
            + DESCRIPTION_DESC_STUDY, expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + VALID_DEADLINE_STUDY
                + DESCRIPTION_DESC_STUDY, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_STUDY + VALID_PRIORITY_STUDY + VALID_DEADLINE_STUDY
            + VALID_DESCRIPTION_STUDY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PRIORITY_DESC_STUDY + DEADLINE_DESC_STUDY
            + DESCRIPTION_DESC_STUDY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, NAME_DESC_STUDY + INVALID_PRIORITY_DESC + DEADLINE_DESC_STUDY
            + DESCRIPTION_DESC_STUDY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + INVALID_DEADLINE_DESC
            + DESCRIPTION_DESC_STUDY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + DEADLINE_DESC_STUDY
            + DESCRIPTION_DESC_STUDY + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_PRIORITY_DESC + DEADLINE_DESC_STUDY
            + DESCRIPTION_DESC_STUDY, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_STUDY + PRIORITY_DESC_STUDY
            + DEADLINE_DESC_STUDY + DESCRIPTION_DESC_STUDY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    //@@author guekling
    @Test
    public void parse_multipleSamePrefixes_failure() {
        String expectedMessage = String.format(MESSAGE_REPEATED_SAME_PREFIXES, AddCommand.MESSAGE_USAGE);

        // multiple name prefixes
        assertParseFailure(parser, NAME_DESC_STUDY + NAME_DESC_REVISION + PRIORITY_DESC_STUDY
            + DEADLINE_DESC_STUDY + DESCRIPTION_DESC_STUDY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, expectedMessage);

        // multiple priority prefixes
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + PRIORITY_DESC_REVISION
                + DEADLINE_DESC_STUDY + DESCRIPTION_DESC_STUDY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, expectedMessage);

        // multiple deadline prefixes
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + DEADLINE_DESC_REVISION
                + DEADLINE_DESC_STUDY + DESCRIPTION_DESC_STUDY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, expectedMessage);

        // multiple description prefixes
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + DEADLINE_DESC_STUDY
                + DESCRIPTION_DESC_STUDY + DESCRIPTION_DESC_REVISION + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                expectedMessage);

    }
}
