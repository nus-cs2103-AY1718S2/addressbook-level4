package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.progresschecker.logic.parser.ParserUtil.MESSAGE_INVALID_TAB_TYPE;
import static seedu.progresschecker.logic.parser.ParserUtil.MESSAGE_INVALID_WEEK_NUMBER;
import static seedu.progresschecker.testutil.TypicalTabTypes.TYPE_EXERCISE;

import org.junit.Test;

import seedu.progresschecker.logic.commands.ViewCommand;

//@@author iNekox3
public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgsType_returnsViewCommand() {
        assertParseSuccess(parser, "exercise", new ViewCommand(TYPE_EXERCISE, -1,
                false));
    }

    @Test
    public void parse_validArgsWeekNumber_returnsViewCommand() {
        assertParseSuccess(parser, "exercise 5", new ViewCommand(TYPE_EXERCISE, 5,
                true));
    }

    @Test
    public void parse_invalidArgsType_throwsParseException() {
        assertParseFailure(parser, "invalid type", MESSAGE_INVALID_TAB_TYPE
                + " \n" + ViewCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidArgsWeekNumber_throwsParseException() {
        assertParseFailure(parser, "exercise 0", MESSAGE_INVALID_WEEK_NUMBER
                + " \n" + ViewCommand.MESSAGE_USAGE);
    }
}
