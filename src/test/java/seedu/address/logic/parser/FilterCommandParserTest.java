package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.DatePredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new DatePredicate(Arrays.asList("2018-03-23", "2018-03-24")));
        assertParseSuccess(parser, "2018-03-23 2018-03-24", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 2018-03-23 \n \t 2018-03-24  \t", expectedFilterCommand);
    }

}
