//@@author ewaldhew
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

public class NotifyCommandParserTest {
    private static final String VALID_INPUT = "c/TEST";

    private NotifyCommandParser parser = new NotifyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        assertParseSuccess(parser, VALID_INPUT, parser.parse(VALID_INPUT));
    }
}
