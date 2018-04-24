package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;

import org.junit.Test;

import seedu.address.logic.commands.RemoveCommand;

//@@author Kyomian
public class RemoveCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "task 1", new RemoveCommand("task", INDEX_FIRST_ACTIVITY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid activity option
        assertParseFailure(parser, "invalidOption 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand
                .MESSAGE_USAGE));

        // Only one argument
        assertParseFailure(parser, "task", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand
                .MESSAGE_USAGE));

    }
}
