package seedu.address.logic.parser;
//@@author SuxianAlicia-reused
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalGroups.FRIENDS;

import org.junit.Test;

import seedu.address.logic.commands.DeleteGroupCommand;

public class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGroupCommand() {
        assertParseSuccess(parser, "friends", new DeleteGroupCommand(FRIENDS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "fr!ends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteGroupCommand.MESSAGE_USAGE));
    }
}
