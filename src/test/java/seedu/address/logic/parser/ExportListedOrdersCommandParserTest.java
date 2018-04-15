//@@author A0143487X
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.ExportListedOrdersCommandParser.MESSAGE_FILENAME_CONSTRAINTS;

import org.junit.Test;

public class ExportListedOrdersCommandParserTest {

    private ExportListedOrdersCommandParser parser = new ExportListedOrdersCommandParser();

    @Test
    public void parse_invalidArgsWrongFormat_throwsParseException() {
        assertParseFailure(parser, "aaa!", MESSAGE_FILENAME_CONSTRAINTS);
    }

    @Test
    public void parse_invalidArgsTooLong_throwsParseException() {
        assertParseFailure(parser, "123451234512345123451234512345X", MESSAGE_FILENAME_CONSTRAINTS);
    }
}
