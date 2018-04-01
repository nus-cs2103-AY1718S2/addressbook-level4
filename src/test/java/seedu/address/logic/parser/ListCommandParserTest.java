package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListCommand;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

//@@author jasmoon
public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "hello",
                String.format(Messages.MESSAGE_INVALID_LIST_REQUEST, "hello"));
    }
}
