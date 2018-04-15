package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.progresschecker.logic.parser.ParserUtil.MESSAGE_INVALID_WEEK_NUMBER;

import org.junit.Test;

import seedu.progresschecker.logic.commands.AnswerCommand;

//@@author iNekox3
public class AnswerCommandParserTest {

    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_invalidArgsIndex_throwsParseException() {
        assertParseFailure(parser, "ans 11.50.80 b", MESSAGE_INVALID_WEEK_NUMBER
                + " \n" + AnswerCommand.MESSAGE_USAGE);
    }
}
