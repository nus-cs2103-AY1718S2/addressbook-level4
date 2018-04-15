package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FindInterviewCommand;
import seedu.address.model.interview.InterviewMatchInterviewee;


public class FindInterviewCommandParserTest {

    private FindInterviewCommandParser parser = new FindInterviewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindInterviewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindInterviewCommand expectedFindInterviewCommand =
                new FindInterviewCommand(new InterviewMatchInterviewee("Alice"));
        assertParseSuccess(parser, "Alice", expectedFindInterviewCommand);

    }

}
