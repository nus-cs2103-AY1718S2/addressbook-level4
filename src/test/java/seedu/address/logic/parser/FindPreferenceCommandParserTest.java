package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.model.person.PreferencesContainKeywordsPredicate;

public class FindPreferenceCommandParserTest {
    private FindPreferenceCommandParser parser = new FindPreferenceCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPreferenceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPreferenceCommand() {
        // no leading and trailing whitespaces
        FindPreferenceCommand expectedFindPreferenceCommand =
                new FindPreferenceCommand(new PreferencesContainKeywordsPredicate(Arrays.asList("Computers", "Shoes")));
        assertParseSuccess(parser, "Computers Shoes", expectedFindPreferenceCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Computers \n \t Shoes  \t", expectedFindPreferenceCommand);
    }
}
