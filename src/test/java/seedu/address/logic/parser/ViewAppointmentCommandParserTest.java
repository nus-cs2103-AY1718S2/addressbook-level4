//@@author Kyholmes-test
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ViewAppointmentCommand;

public class ViewAppointmentCommandParserTest {

    private ViewAppointmentCommandParser parser = new ViewAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsViewAppointmentCommand() {
        ViewAppointmentCommand expectedCommand = new ViewAppointmentCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "Alice", expectedCommand);
        assertParseSuccess(parser, "\n Alice \n", expectedCommand);
    }
}
