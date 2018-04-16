package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.organizer.logic.commands.DeleteRecurredTasksCommand;

//@@author natania-d-reused
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteRecurredTasksCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteRecurredTasksCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteRecurredTasksCommandParserTest {

    private DeleteRecurredTasksCommandParser parser = new DeleteRecurredTasksCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRecurredTasksCommand() {
        assertParseSuccess(parser, "1", new DeleteRecurredTasksCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRecurredTasksCommand.MESSAGE_USAGE));
    }
}
