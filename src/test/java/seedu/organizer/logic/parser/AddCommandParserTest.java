package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.commands.CommandTestUtil.ADDRESS_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.ADDRESS_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.EMAIL_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.EMAIL_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.organizer.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
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
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Address;
import seedu.organizer.model.task.Email;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Task;
import seedu.organizer.testutil.TaskBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withName(VALID_NAME_STUDY).withPriority(VALID_PRIORITY_STUDY)
                .withEmail(VALID_EMAIL_STUDY).withAddress(VALID_ADDRESS_STUDY).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY
                + ADDRESS_DESC_STUDY + TAG_DESC_FRIEND, new AddCommand(expectedTask));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_EXAM + NAME_DESC_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY
                + ADDRESS_DESC_STUDY + TAG_DESC_FRIEND, new AddCommand(expectedTask));

        // multiple prioritys - last priority accepted
        assertParseSuccess(parser, NAME_DESC_STUDY + PRIORITY_DESC_EXAM + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY
                + ADDRESS_DESC_STUDY + TAG_DESC_FRIEND, new AddCommand(expectedTask));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_EXAM + EMAIL_DESC_STUDY
                + ADDRESS_DESC_STUDY + TAG_DESC_FRIEND, new AddCommand(expectedTask));

        // multiple addresses - last organizer accepted
        assertParseSuccess(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY + ADDRESS_DESC_EXAM
                + ADDRESS_DESC_STUDY + TAG_DESC_FRIEND, new AddCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder().withName(VALID_NAME_STUDY).withPriority(VALID_PRIORITY_STUDY)
                .withEmail(VALID_EMAIL_STUDY).withAddress(VALID_ADDRESS_STUDY)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY + ADDRESS_DESC_STUDY
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedTaskMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Task expectedTask = new TaskBuilder().withName(VALID_NAME_EXAM).withPriority(VALID_PRIORITY_EXAM)
                .withEmail(VALID_EMAIL_EXAM).withAddress(VALID_ADDRESS_EXAM).withTags().build();
        assertParseSuccess(parser, NAME_DESC_EXAM + PRIORITY_DESC_EXAM + EMAIL_DESC_EXAM + ADDRESS_DESC_EXAM,
                new AddCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY + ADDRESS_DESC_STUDY,
                expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, NAME_DESC_STUDY + VALID_PRIORITY_STUDY + EMAIL_DESC_STUDY + ADDRESS_DESC_STUDY,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + VALID_EMAIL_STUDY + ADDRESS_DESC_STUDY,
                expectedMessage);

        // missing organizer prefix
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY + VALID_ADDRESS_STUDY,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_STUDY + VALID_PRIORITY_STUDY + VALID_EMAIL_STUDY + VALID_ADDRESS_STUDY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY + ADDRESS_DESC_STUDY
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, NAME_DESC_STUDY + INVALID_PRIORITY_DESC + EMAIL_DESC_STUDY + ADDRESS_DESC_STUDY
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + INVALID_EMAIL_DESC + ADDRESS_DESC_STUDY
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid organizer
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY + ADDRESS_DESC_STUDY
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY + INVALID_ADDRESS_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_STUDY + PRIORITY_DESC_STUDY + EMAIL_DESC_STUDY
                        + ADDRESS_DESC_STUDY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
