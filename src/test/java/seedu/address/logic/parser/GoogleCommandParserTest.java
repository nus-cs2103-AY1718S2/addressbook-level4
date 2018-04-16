package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GoogleCommand;

//@@author x3tsunayh

public class GoogleCommandParserTest {

    private GoogleCommandParser parser = new GoogleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty input not allowed
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GoogleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_returnsGoogleCommand() {
        //invalid args that are not indexes
        assertParseFailure(parser, "one", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GoogleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        GoogleCommand expectedGoogleCommand =
                new GoogleCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedGoogleCommand);
    }

}
