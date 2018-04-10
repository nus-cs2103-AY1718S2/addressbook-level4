package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.AnswerCommand;

//@@author dominickenn
public class AnswerCommandParserTest {
    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedUsername = "admin";
        String expectedAnswer = "answer";
        assertParseSuccess(parser, " u/admin a/answer", new AnswerCommand(expectedUsername, expectedAnswer));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, " admin a/answer", expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, " u/admin answer", expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, " admin answer", expectedMessage);

        // missing username
        assertParseFailure(parser, "u/ a/answer", expectedMessage);

        // missing answer
        assertParseFailure(parser, "u/admin a/", expectedMessage);

        // missing all fields
        assertParseFailure(parser, "u/ a/", expectedMessage);

        // no arguments
        assertParseFailure(parser, "", expectedMessage);
    }
}
