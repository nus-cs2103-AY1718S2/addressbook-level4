package seedu.ptman.logic.parser;

import static seedu.ptman.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ptman.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ptman.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_FIRST_SHIFT;

import org.junit.Test;

import seedu.ptman.logic.commands.ViewShiftCommand;

//@@author hzxcaryn
public class ViewShiftCommandParserTest {

    private ViewShiftCommandParser parser = new ViewShiftCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new ViewShiftCommand(INDEX_FIRST_SHIFT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewShiftCommand.MESSAGE_USAGE));
    }
}
