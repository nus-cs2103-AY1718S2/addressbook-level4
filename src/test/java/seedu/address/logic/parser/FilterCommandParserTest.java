package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.ExpectedGraduationYearInKeywordsRangePredicate;

public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new ExpectedGraduationYearInKeywordsRangePredicate(
                        new FilterRange<ExpectedGraduationYear>(
                                new ExpectedGraduationYear("2020"), new ExpectedGraduationYear("2020"))));
        assertParseSuccess(parser, " y/2020", expectedFilterCommand);

        expectedFilterCommand =
                new FilterCommand(new ExpectedGraduationYearInKeywordsRangePredicate(
                        new FilterRange<ExpectedGraduationYear>(
                                new ExpectedGraduationYear("2019"), new ExpectedGraduationYear("2021"))));
        assertParseSuccess(parser, " y/2019-2021", expectedFilterCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        //Missing input, invalid command format
        assertParseFailure(parser, "y/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   y/2025--2025",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   y/-",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   y/,",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   y/,,",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        //Correct command format but invalid expected graduation year
        assertParseFailure(parser, "   y/2o2o",
                ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
        assertParseFailure(parser, "   y/2025,,2025",
                ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
        //Both mistakes occured, detect invalid command format first
        assertParseFailure(parser, "   y/2025--2025,2o2o",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

    }
}
