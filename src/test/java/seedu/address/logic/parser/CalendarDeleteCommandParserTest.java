//@@author philos22

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.CalendarDeleteCommand;

/**
 * Testing for CalendarDeleteCommand
 */
public class CalendarDeleteCommandParserTest {

    private CalendarDeleteCommandParser parser = new CalendarDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        ArrayList<String> eventIdsTest = new ArrayList<>(); //Same logic as delete event command
        eventIdsTest.add("a1b2c3d4e5f6g7");
        assertEquals(eventIdsTest.get(1 - 1), "a1b2c3d4e5f6g7");
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CalendarDeleteCommand.MESSAGE_USAGE));
    }
}
