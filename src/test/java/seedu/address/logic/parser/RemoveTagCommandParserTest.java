package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.RemoveTagCommand;

public class RemoveTagCommandParserTest {

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "abc**",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

}



