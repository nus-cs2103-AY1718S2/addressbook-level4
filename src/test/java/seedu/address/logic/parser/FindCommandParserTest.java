package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.FieldContainKeyphrasesPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainKeyphrasesPredicate(
                        Arrays.asList("Alice Bob", "Carol"),
                        Arrays.asList("Friends", "Family"),
                        Arrays.asList("5")));
        assertParseSuccess(parser, " n/Alice Bob n/Carol t/Friends t/Family r/5", expectedFindCommand);

        // multiple whitespaces between keyphrases
        assertParseSuccess(parser,
                " \n n/Alice Bob\n \t n/Carol  \t t/Friends \n t/Family \t r/5",
                expectedFindCommand);
    }

}
