package seedu.organizer.logic.parser;

//@@author agus
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.EditSubtaskCommand;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.Name;

public class EditSubtaskCommandParserTest {

    private static final String VALID_NAME = " " + PREFIX_NAME + VALID_NAME_EXAM;
    private static final String INVALID_NAME = " " + INVALID_NAME_DESC;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSubtaskCommand.MESSAGE_USAGE);

    private EditSubtaskCommandParser parser = new EditSubtaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_EXAM, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1" + VALID_NAME_EXAM, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1 1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 1" + VALID_NAME, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 -2" + VALID_NAME, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 1" + VALID_NAME, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 0" + VALID_NAME, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "hue 1", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1 1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index taskIndex = INDEX_SECOND_TASK;
        Index subtaskIndex = INDEX_FIRST_TASK;
        String userInput = taskIndex.getOneBased() + " " + subtaskIndex.getOneBased() + VALID_NAME;

        EditSubtaskCommand expectedCommand = new EditSubtaskCommand(taskIndex, subtaskIndex,
                new Subtask(new Name(VALID_NAME_EXAM)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index taskIndex = INDEX_SECOND_TASK;
        Index subtaskIndex = INDEX_FIRST_TASK;
        String userInput = taskIndex.getOneBased() + " " + subtaskIndex.getOneBased() + VALID_NAME + VALID_NAME;

        EditSubtaskCommand expectedCommand = new EditSubtaskCommand(taskIndex, subtaskIndex,
                new Subtask(new Name(VALID_NAME_EXAM)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index taskIndex = INDEX_FIRST_TASK;
        Index subtaskIndex = INDEX_FIRST_TASK;
        String userInput = taskIndex.getOneBased() + " " + subtaskIndex.getOneBased() + INVALID_NAME + VALID_NAME;
        EditSubtaskCommand expectedCommand = new EditSubtaskCommand(taskIndex, subtaskIndex,
                new Subtask(new Name(VALID_NAME_EXAM)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
