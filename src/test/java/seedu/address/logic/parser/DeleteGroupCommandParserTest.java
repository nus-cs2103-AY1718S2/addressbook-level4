//@@author jas5469
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalGroups.GROUP_A;

import org.junit.Test;

import seedu.address.logic.commands.DeleteGroupCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteGroupCommand code. For example, inputs "Group" and "Group A" take the
 * same path through the DeleteGroupCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGroupCommand() {
        assertParseSuccess(parser, "Group A", new DeleteGroupCommand(GROUP_A.getInformation()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "!", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteGroupCommand.MESSAGE_USAGE));
    }
}
