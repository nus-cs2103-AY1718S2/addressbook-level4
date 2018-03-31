package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.person.LinkedInCommand;
import seedu.address.logic.parser.person.LinkedInCommandParser;

public class LinkedInCommandParserTest {

    private LinkedInCommandParser parser = new LinkedInCommandParser();

    @Test
    public void parse_validArgs_returnsLinkedInCommand() {
        assertParseSuccess(parser, "1", new LinkedInCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkedInCommand.MESSAGE_USAGE));
    }
}
