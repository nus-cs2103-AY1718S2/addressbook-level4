package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.HelpCommand;

//@@author jasmoon
public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_HELP_REQUEST, "a"));
    }
}
