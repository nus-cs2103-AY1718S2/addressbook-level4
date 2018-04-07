package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.organizer.logic.commands.FindNameCommand;
import seedu.organizer.model.task.predicates.NameContainsKeywordsPredicate;

public class FindNameCommandParserTest {

    private FindNameCommandParser parser = new FindNameCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindNameCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindNameCommand() {
        // no leading and trailing whitespaces
        FindNameCommand expectedFindNameCommand =
                new FindNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("CS2102", "ES2660")));
        assertParseSuccess(parser, "CS2102 ES2660", expectedFindNameCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS2102 \n \t ES2660  \t", expectedFindNameCommand);
    }

}
