//@@author sarahgoh97
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCells.FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.AddCellCommand;

public class AddCellCommandParserTest {

    private AddCellCommandParser parser = new AddCellCommandParser();

    @Test
    public void parse_validArgs_returnsAddCellCommand() {
        assertParseSuccess(parser, "ac 1 1-1", new AddCellCommand(INDEX_FIRST_PERSON, FIRST_CELL_ADDRESS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "ac 1 sdf32",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCellCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCellAddress_throwsParseException() {
        assertParseFailure(parser, "ac 1 2=1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCellCommand.MESSAGE_USAGE));
    }
}
