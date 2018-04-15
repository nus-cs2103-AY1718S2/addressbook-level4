package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCells.FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.INVALID_FIRST_CELL_ADDRESS;

import org.junit.Test;

import seedu.address.logic.commands.ListCellCommand;
import seedu.address.model.person.AddressContainsCellPredicate;

public class ListCellCommandParserTest {

    private ListCellCommandParser parser = new ListCellCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCellCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "1=1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCellCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validAddress_success() {
        ListCellCommand expectedListCellCommand = new ListCellCommand(
                new AddressContainsCellPredicate(FIRST_CELL_ADDRESS), FIRST_CELL_ADDRESS);
        assertParseSuccess(parser, "1-1", expectedListCellCommand);
    }

    @Test
    public void parse_invalidAddress_success() {
        ListCellCommand expectedListCellCommand = new ListCellCommand(
                new AddressContainsCellPredicate(INVALID_FIRST_CELL_ADDRESS), INVALID_FIRST_CELL_ADDRESS);
        assertParseSuccess(parser, "0-1", expectedListCellCommand);
    }


}
