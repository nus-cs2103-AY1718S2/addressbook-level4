package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.InterviewCommand;

public class InterviewCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE);
    private static final String VALID_DATE = "14th Mar 2pm";
    private static final String INVALID_DATE = "My Birthday";

    private InterviewCommandParser parser = new InterviewCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // No index specified
        assertParseFailure(parser, VALID_DATE, MESSAGE_INVALID_FORMAT);

        // No date specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // No index and no date specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_throwsParseException() {
        assertParseFailure(parser, "1 " + INVALID_DATE,
                String.format(InterviewCommandParser.MESSAGE_DATETIME_PARSE_FAIL, INVALID_DATE));
    }

    @Test
    public void parse_validValue_returnsInterviewCommand() {
        LocalDateTime dateTime = LocalDateTime.of(2018, 3, 14, 14, 0, 0);
        InterviewCommand expectedInterviewCommand =
                new InterviewCommand(Index.fromOneBased(2), dateTime);

        assertParseSuccess(parser, "2 " + VALID_DATE, expectedInterviewCommand);
    }

}
