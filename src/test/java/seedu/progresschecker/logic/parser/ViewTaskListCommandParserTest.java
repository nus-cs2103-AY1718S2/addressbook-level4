package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.progresschecker.logic.parser.ParserUtil.MESSAGE_INVALID_TASK_FILTER;
import static seedu.progresschecker.testutil.TypicalTaskArgs.COMPULSORY;
import static seedu.progresschecker.testutil.TypicalTaskArgs.COM_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.FIRST_WEEK;
import static seedu.progresschecker.testutil.TypicalTaskArgs.FIRST_WEEK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INVALID_CHARSET;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INVALID_NEGATIVE;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INVALID_ZERO;
import static seedu.progresschecker.testutil.TypicalTaskArgs.SUBMISSION;
import static seedu.progresschecker.testutil.TypicalTaskArgs.SUB_INT;

import org.junit.Test;

import seedu.progresschecker.logic.commands.ViewTaskListCommand;

//@@author EdwardKSG
public class ViewTaskListCommandParserTest {

    private ViewTaskListCommandParser parser = new ViewTaskListCommandParser();

    @Test
    public void parse_validArgsFirstWeek_returnsViewTaskListCommand() {
        assertParseSuccess(parser, FIRST_WEEK, new ViewTaskListCommand(FIRST_WEEK_INT));
    }

    @Test
    public void parse_validArgsCompulsory_returnsViewTaskListCommand() {
        assertParseSuccess(parser, COMPULSORY, new ViewTaskListCommand(COM_INT));
    }

    @Test
    public void parse_validArgsSubmission_returnsViewTaskListCommand() {
        assertParseSuccess(parser, SUBMISSION, new ViewTaskListCommand(SUB_INT));
    }

    @Test
    public void parse_invalidArgsNegative_throwsParseException() {
        assertParseFailure(parser, INVALID_NEGATIVE, String.format(MESSAGE_INVALID_TASK_FILTER,
                ViewTaskListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsZero_throwsParseException() {
        assertParseFailure(parser, INVALID_ZERO, String.format(MESSAGE_INVALID_TASK_FILTER,
                ViewTaskListCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_invalidArgsCharset_throwsParseException() {
        assertParseFailure(parser, INVALID_CHARSET, String.format(MESSAGE_INVALID_TASK_FILTER,
                ViewTaskListCommand.MESSAGE_USAGE));
    }
}
