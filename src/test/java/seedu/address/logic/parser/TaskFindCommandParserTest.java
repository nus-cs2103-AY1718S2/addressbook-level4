package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.TaskFindCommand;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

//@@author CYX28
public class TaskFindCommandParserTest {

    private TaskFindCommandParser parser = new TaskFindCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsTaskFindCommand() {
        // no leading and trailing whitespaces
        TaskFindCommand expectedTaskFindCommand =
                new TaskFindCommand(new TaskNameContainsKeywordsPredicate(Arrays.asList("discuss", "meeting")));
        assertParseSuccess(parser, "discuss meeting", expectedTaskFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n discuss \n \t meeting  \t", expectedTaskFindCommand);
    }

}
