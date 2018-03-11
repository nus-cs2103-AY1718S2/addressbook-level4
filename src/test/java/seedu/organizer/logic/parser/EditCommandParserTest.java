package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.EditCommand;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.testutil.EditTaskDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_EXAM, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_EXAM, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_EXAM, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser,
                "1" + INVALID_PRIORITY_DESC, Priority.MESSAGE_PRIORITY_CONSTRAINTS); // invalid priority
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC,
            Deadline.MESSAGE_DEADLINE_CONSTRAINTS); // invalid deadline
        assertParseFailure(parser,
                "1" + INVALID_DESCRIPTION_DESC, Description.MESSAGE_DESCRIPTION_CONSTRAINTS); // invalid organizer
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid priority followed by valid deadline
        assertParseFailure(parser,
                "1" + INVALID_PRIORITY_DESC + DEADLINE_DESC_EXAM, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // valid priority followed by invalid priority. The test case for invalid priority followed by valid priority
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser,
                "1" + PRIORITY_DESC_STUDY + INVALID_PRIORITY_DESC, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser,
                "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_NAME_DESC + INVALID_DEADLINE_DESC + VALID_DESCRIPTION_EXAM + VALID_PRIORITY_EXAM,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + PRIORITY_DESC_STUDY + TAG_DESC_HUSBAND
                + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM + NAME_DESC_EXAM + TAG_DESC_FRIEND;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_EXAM)
                .withPriority(VALID_PRIORITY_STUDY).withDeadline(VALID_DEADLINE_EXAM)
                .withDescription(VALID_DESCRIPTION_EXAM).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + PRIORITY_DESC_STUDY + DEADLINE_DESC_EXAM;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withPriority(VALID_PRIORITY_STUDY)
                .withDeadline(VALID_DEADLINE_EXAM).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + NAME_DESC_EXAM;
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_EXAM).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // priority
        userInput = targetIndex.getOneBased() + PRIORITY_DESC_EXAM;
        descriptor = new EditTaskDescriptorBuilder().withPriority(VALID_PRIORITY_EXAM).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_EXAM;
        descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_EXAM).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // organizer
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_EXAM;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_EXAM).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + PRIORITY_DESC_EXAM + DESCRIPTION_DESC_EXAM + DEADLINE_DESC_EXAM
                + TAG_DESC_FRIEND + PRIORITY_DESC_EXAM + DESCRIPTION_DESC_EXAM + DEADLINE_DESC_EXAM + TAG_DESC_FRIEND
                + PRIORITY_DESC_STUDY + DESCRIPTION_DESC_STUDY + DEADLINE_DESC_STUDY + TAG_DESC_HUSBAND;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withPriority(VALID_PRIORITY_STUDY)
                .withDeadline(VALID_DEADLINE_STUDY).withDescription(VALID_DESCRIPTION_STUDY)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_PRIORITY_DESC + PRIORITY_DESC_STUDY;
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withPriority(VALID_PRIORITY_STUDY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_STUDY + INVALID_PRIORITY_DESC + DESCRIPTION_DESC_STUDY
                + PRIORITY_DESC_STUDY;
        descriptor = new EditTaskDescriptorBuilder().withPriority(VALID_PRIORITY_STUDY).withDeadline
                (VALID_DEADLINE_STUDY)
                .withDescription(VALID_DESCRIPTION_STUDY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
