//@@author ewaldhew
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

public class NotifyCommandParserTest {
    private NotifyCommandParser parser = new NotifyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        assertParseSuccess(parser, "c/TEST", parser.parse("c/TEST"));
    }
}
