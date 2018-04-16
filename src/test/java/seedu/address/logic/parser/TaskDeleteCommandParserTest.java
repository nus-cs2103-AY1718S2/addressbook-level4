package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.TaskDeleteCommand;

//@@author CYX28
public class TaskDeleteCommandParserTest {

    private TaskDeleteCommandParser parser = new TaskDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsTaskDeleteCommand() {
        assertParseSuccess(parser, "1", new TaskDeleteCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TaskDeleteCommand.MESSAGE_USAGE));
    }

}
