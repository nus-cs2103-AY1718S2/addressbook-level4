//@@author jaronchan
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see SelectCommandParserTest
 */

public class NavigateCommandParserTest {

    private NavigateCommandParser parser = new NavigateCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(ParserUtil.MESSAGE_INVALID_EVENT_INDEX));
    }
}
