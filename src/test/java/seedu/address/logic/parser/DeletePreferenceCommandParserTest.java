package seedu.address.logic.parser;
//@@author SuxianAlicia-reused
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPreferences.COMPUTERS;

import org.junit.Test;

import seedu.address.logic.commands.DeletePreferenceCommand;

public class DeletePreferenceCommandParserTest {

    private DeletePreferenceCommandParser parser = new DeletePreferenceCommandParser();

    @Test
    public void parse_validArgs_returnsDeletePreferenceCommand() {
        assertParseSuccess(parser, "computers", new DeletePreferenceCommand(COMPUTERS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "Comp&ters", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePreferenceCommand.MESSAGE_USAGE));
    }
}
