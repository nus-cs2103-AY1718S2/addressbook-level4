package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMUNICATION_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROBLEM_SOLVING_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICAL_SKILLS_SCORE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.RatingEditCommand;
import seedu.address.model.person.Rating;

//@@author kexiaowen
public class RatingEditCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingEditCommand.MESSAGE_USAGE);
    private static final String MESSAGE_MISSING_INDEX =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingEditCommand.MESSAGE_MISSING_INDEX);

    private RatingEditCommandParser parser = new RatingEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_MISSING_INDEX);

        // no field specified
        assertParseFailure(parser, "1", RatingEditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_RATING_DESC, Rating.MESSAGE_RATING_CONSTRAINTS);
        assertParseFailure(parser, "1" + " " + PREFIX_TECHNICAL_SKILLS_SCORE
                + " " + PREFIX_COMMUNICATION_SKILLS_SCORE
                + " " + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, RatingEditCommand.MESSAGE_EMPTY_SCORE);
    }

}
