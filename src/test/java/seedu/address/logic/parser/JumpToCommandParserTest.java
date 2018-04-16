package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.YearMonth;

import org.junit.Test;

import seedu.address.logic.commands.JumpToCommand;

//@@author x3tsunayh

public class JumpToCommandParserTest {

    private JumpToCommandParser parser = new JumpToCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty input not allowed
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // incorrect yyyy input
        assertParseFailure(parser, "18999-12", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // incorrect mm input
        assertParseFailure(parser, "18999-112", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // incorrect format
        assertParseFailure(parser, "18999#12", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // yyyy out of bounds
        assertParseFailure(parser, "1899-12", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // yyyy out of bounds
        assertParseFailure(parser, "2301-01", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // mm out of bounds
        assertParseFailure(parser, "2105-00", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));
        // mm out of bounds
        assertParseFailure(parser, "2109-13", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                JumpToCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_validArgs_returnsJumpToCommand() {
        JumpToCommand expectedJumpToCommand =
                new JumpToCommand(YearMonth.of(2018, 5));

        assertParseSuccess(parser, " 2018-05", expectedJumpToCommand);
    }

}
