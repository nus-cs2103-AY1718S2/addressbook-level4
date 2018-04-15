package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SelectPersonCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Test scope: similar to {@code DeletePersonCommandParserTest}.
 * @see DeletePersonCommandParserTest
 */
public class SelectPersonCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() throws CommandException {
        assertParseSuccess(parser, "1", new SelectPersonCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws CommandException {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectPersonCommand.MESSAGE_USAGE));
    }
}
