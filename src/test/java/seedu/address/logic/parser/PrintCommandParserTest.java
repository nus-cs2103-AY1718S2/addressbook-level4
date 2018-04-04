//@@author cxingkai
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PrintCommand;

public class PrintCommandParserTest {
    private PrintCommandParser parser = new PrintCommandParser();

    @Test
    public void parse_emptyArg_returnsLoginCommand() {
        int testInput = 1;
        Index testIndex = Index.fromOneBased(testInput);
        PrintCommand expectedPrintCommand = new PrintCommand(testIndex);

        assertParseSuccess(parser, Integer.toString(testInput), expectedPrintCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE));
    }
}