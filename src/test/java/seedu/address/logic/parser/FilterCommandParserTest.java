package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.ExpectedGraduationYearInKeywordsRangePredicate;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.GradePointAverageInKeywordsRangePredicate;
import seedu.address.model.person.Rating;
import seedu.address.model.person.RatingInKeywordsRangePredicate;
import seedu.address.model.util.InterviewDateUtil;

public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();
    //@@author mhq199657
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

        expectedFilterCommand =
                new FilterCommand(new RatingInKeywordsRangePredicate(
                        new FilterRange<>(
                                new Rating(2.22, 2.22, 2.22, 2.22), new Rating(2.22, 2.22, 2.22, 2.22))));
        assertParseSuccess(parser, " r/2.22", expectedFilterCommand);

        expectedFilterCommand =
                new FilterCommand(new RatingInKeywordsRangePredicate(
                        new FilterRange<>(
                                new Rating(1.22, 1.22, 1.22, 1.22), new Rating(3.22, 3.22, 3.22, 3.22))));
        assertParseSuccess(parser, " r/1.22-3.22", expectedFilterCommand);

        expectedFilterCommand =
                new FilterCommand(new GradePointAverageInKeywordsRangePredicate(
                        new FilterRange<GradePointAverage>(
                                new GradePointAverage("4.00"), new GradePointAverage("4.00"))));
        assertParseSuccess(parser, " g/4.00", expectedFilterCommand);

        expectedFilterCommand =
                new FilterCommand(new GradePointAverageInKeywordsRangePredicate(
                        new FilterRange<GradePointAverage>(
                                new GradePointAverage("3.01"), new GradePointAverage("4.01"))));
        assertParseSuccess(parser, " g/3.01-4.01", expectedFilterCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        //Missing input, invalid command format
        assertParseFailure(parser, "y/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "r/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "g/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "d/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "x/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   y/2025--2025",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   y/-",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   y/,",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   d/,,",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " r/2.22 - ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " g/2.2 - ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        //Correct command format but invalid field value
        assertParseFailure(parser, "   y/2o2o",
                ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
        assertParseFailure(parser, "   y/2025,,2025",
                ExpectedGraduationYear.MESSAGE_EXPECTED_GRADUATION_YEAR_CONSTRAINTS);
        assertParseFailure(parser, "   y/2020 r/f",
                Rating.MESSAGE_RATING_CONSTRAINTS);
        assertParseFailure(parser, " r/-3.45 ",
                Rating.MESSAGE_RATING_CONSTRAINTS);
        assertParseFailure(parser, " g/3.3-3.4 ",
                GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);
        assertParseFailure(parser, " g/3.3-+6.4 ",
                GradePointAverage.MESSAGE_GRADE_POINT_AVERAGE_CONSTRAINTS);
        assertParseFailure(parser, " d/20188888 ",
                InterviewDateUtil.MESSAGE_INTERVIEW_DATE_CONSTRAINT);
        //Both mistakes occured, detect invalid command format first
        assertParseFailure(parser, "   y/2025--2025,2o2o",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "   y/2025 r/1--100",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

    }
}
