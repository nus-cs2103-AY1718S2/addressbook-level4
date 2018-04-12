package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.commons.core.Messages.MESSAGE_REPEATED_SAME_PREFIXES;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_ANSWER;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_USERNAME_BOBBY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_USERNAME_JOSHUA;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.AnswerCommand;

//@@author dominickenn
public class AnswerCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand
        .MESSAGE_USAGE);
    private static final String MESSAGE_MULTIPLE_SAME_PREFIXES =
            String.format(MESSAGE_REPEATED_SAME_PREFIXES, AnswerCommand.MESSAGE_USAGE);

    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedUsername = "admin";
        String expectedAnswer = "answer";
        assertParseSuccess(parser, " u/admin a/answer", new AnswerCommand(expectedUsername, expectedAnswer));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing username prefix
        assertParseFailure(parser, " admin a/answer", MESSAGE_INVALID_FORMAT);

        // missing answer prefix
        assertParseFailure(parser, " u/admin answer", MESSAGE_INVALID_FORMAT);

        // missing all prefixes
        assertParseFailure(parser, " admin answer", MESSAGE_INVALID_FORMAT);

        // missing username
        assertParseFailure(parser, "u/ a/answer", MESSAGE_INVALID_FORMAT);

        // missing answer
        assertParseFailure(parser, "u/admin a/", MESSAGE_INVALID_FORMAT);

        // missing all fields
        assertParseFailure(parser, "u/ a/", MESSAGE_INVALID_FORMAT);

        // no arguments
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    //@@author guekling
    @Test
    public void parse_multipleSamePrefixes_failure() {
        // repeated username prefix
        assertParseFailure(parser, " " + PREFIX_USERNAME + VALID_USERNAME_JOSHUA + " " + PREFIX_USERNAME
            + VALID_USERNAME_BOBBY + " " + PREFIX_ANSWER + VALID_ANSWER, MESSAGE_MULTIPLE_SAME_PREFIXES);

        // repeated password prefix
        assertParseFailure(parser, " " + PREFIX_USERNAME + VALID_USERNAME_JOSHUA + " " + PREFIX_ANSWER
            + VALID_ANSWER + " " + PREFIX_ANSWER + VALID_ANSWER, MESSAGE_MULTIPLE_SAME_PREFIXES);
    }
    //@@author
}
