package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATE_EXCEEDRANGE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATE_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.Rate.MESSAGE_RATE_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RateCommand;
import seedu.address.model.person.Rate;

//@@author sherlynng
public class RateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = MESSAGE_INVALID_COMMAND_FORMAT + RateCommand.MESSAGE_USAGE;

    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        String userInput = PREFIX_RATE + INVALID_RATE_EXCEEDRANGE;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        String userInput = "-5" + " " + PREFIX_RATE + VALID_RATE_AMY;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // zero index
        userInput = "0" + " " + PREFIX_RATE + VALID_RATE_AMY;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidRate_failure() {
        // exceed rate range
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + INVALID_RATE_EXCEEDRANGE;
        assertParseFailure(parser, userInput, MESSAGE_RATE_CONSTRAINTS);

        // negative rate
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + INVALID_RATE_NEGATIVE;
        assertParseFailure(parser, userInput, MESSAGE_RATE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecifiedAbsoluteRate_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE + VALID_RATE_AMY + "-";

        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), true);
        RateCommand expectedCommand = new RateCommand(targetIndex, rate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecifiedAccumulatedRate_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE + VALID_RATE_BOB;

        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), false);
        RateCommand expectedCommand = new RateCommand(targetIndex, rate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
