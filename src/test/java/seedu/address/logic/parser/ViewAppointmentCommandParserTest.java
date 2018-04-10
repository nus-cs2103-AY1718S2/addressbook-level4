//@@author Kyholmes-test
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class ViewAppointmentCommandParserTest {

    private ViewAppointmentCommandParser parser = new ViewAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsViewAppointmentCommand() {
        ViewAppointmentCommand expectedCommand = new ViewAppointmentCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, "Alice", expectedCommand);
        assertParseSuccess(parser, "\n Alice \n", expectedCommand);
    }
}
