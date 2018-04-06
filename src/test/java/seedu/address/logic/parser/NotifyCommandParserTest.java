//@@author ewaldhew
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.NotifyCommand;

public class NotifyCommandParserTest {
    private NotifyCommandParser parser = new NotifyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, "n/TEST", new NotifyCommand("n/TEST"));
    }
}
