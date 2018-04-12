package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE_STRING;

import org.junit.Test;

import seedu.address.logic.commands.CalendarJumpCommand;

public class CalendarJumpCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarJumpCommand.MESSAGE_USAGE);

    private CalendarJumpCommandParser parser = new CalendarJumpCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing Target Date prefix
        assertParseFailure(parser, NORMAL_DATE_STRING,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTargetDate_failure() {
        assertParseFailure(parser, " " + PREFIX_TARGET_DATE + "06.06.1990", MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_validTargetDate_success() {
        assertParseSuccess(parser, " " + PREFIX_TARGET_DATE + NORMAL_DATE_STRING,
                new CalendarJumpCommand(NORMAL_DATE, NORMAL_DATE_STRING));
    }
}
