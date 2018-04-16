package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ResetPictureCommand;

//@@author dezhanglee
public class ResetPictureCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResetPictureCommand.MESSAGE_USAGE);

    private ResetPictureCommandParser parser = new ResetPictureCommandParser();

    @Test
    public void parse_validArgs_returnsResetPictureCommand() {
        assertParseSuccess(parser, "1", new ResetPictureCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidInput_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // multiple repeated index
        assertParseFailure(parser, "1 1", MESSAGE_INVALID_FORMAT);

        // multiple distinct index
        assertParseFailure(parser, "1 2", MESSAGE_INVALID_FORMAT);


        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

    }

}
