package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.RestoreCommand;
import seedu.address.model.person.Category;

//@@author olimhc
public class RestoreCommandParserTest {

    private static final Category CATEGORY_STUDENT = new Category("s");
    private static final Category CATEGORY_TUTOR =  new Category("t");

    private RestoreCommandParser parser = new RestoreCommandParser();

    @Test
    public void parse_validArgs_returnsRestoreCommand() {
        assertParseSuccess(parser, "1 c/s", new RestoreCommand(INDEX_FIRST_PERSON, CATEGORY_STUDENT));
        assertParseSuccess(parser, "1 c/t", new RestoreCommand(INDEX_FIRST_PERSON, CATEGORY_TUTOR));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "z c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 c/z", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 w/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser,  "   c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        assertParseFailure(parser,  "   c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        //no category prefix and category
        assertParseFailure(parser,  "1 ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        //no category specified
        assertParseFailure(parser,  "1 c/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        //negative index
        assertParseFailure(parser,  "-1 c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        //zero index
        assertParseFailure(parser,  "0 c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));
    }

}
