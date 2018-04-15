//@@author LeonidAgarth
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INFORMATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFORMATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ScheduleGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;

/**
 * Tests for the parsing of input arguments and creating a new ScheduleGroupCommand object
 */
public class ScheduleGroupCommandParserTest {

    private ScheduleGroupCommandParser parser = new ScheduleGroupCommandParser();

    @Test
    public void parse_validArgs_returnsScheduleGroupCommand() {
        Group group = new Group(new Information(VALID_INFORMATION));
        assertParseSuccess(parser, VALID_INFORMATION, new ScheduleGroupCommand(group));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INFORMATION,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleGroupCommand.MESSAGE_USAGE));
    }
}
