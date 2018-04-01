package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.ExpectedGraduationYearInKeywordsRangePredicate;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.GradePointAverageInKeywordsRangePredicate;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.InterviewDateInKeywordsRangePredicate;
import seedu.address.model.person.Rating;
import seedu.address.model.person.RatingInKeywordsRangePredicate;
import seedu.address.model.util.InterviewDateUtil;

public class FilterUtilTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseExpectedGraduationYear_invalidCommandFormat_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        FilterUtil.parseExpectedGraduationYear("  ");
    }

    @Test
    public void parseExpectedGraduationYear_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        FilterUtil.parseExpectedGraduationYear("3000");
    }

    @Test
    public void parseExpectedGraduationYear_validInput_success() throws Exception {
        //Single value
        //No whitespaces
        assertEquals(new ExpectedGraduationYearInKeywordsRangePredicate(
                new FilterRange<>(new ExpectedGraduationYear("2020"))),
                FilterUtil.parseExpectedGraduationYear("2020"));
        //With whitespaces
        assertEquals(new ExpectedGraduationYearInKeywordsRangePredicate(
                        new FilterRange<>(new ExpectedGraduationYear("2020"))),
                FilterUtil.parseExpectedGraduationYear("    2020     "));
        //Multiple values with whitespaces
        assertEquals(new ExpectedGraduationYearInKeywordsRangePredicate(
                        new FilterRange<>(new ExpectedGraduationYear("2020"), new ExpectedGraduationYear("2024"))),
                FilterUtil.parseExpectedGraduationYear("    2020    - 2024 "));
    }

    @Test
    public void parseRating_invalidCommandFormat_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        FilterUtil.parseRating("  ");
    }

    @Test
    public void parseRating_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        FilterUtil.parseRating("6");
    }

    @Test
    public void parseGradePointAverage_validInput_success() throws Exception {
        //Single value
        //No whitespaces
        assertEquals(new GradePointAverageInKeywordsRangePredicate(
                        new FilterRange<>(new GradePointAverage("2.20"))),
                FilterUtil.parseGradePointAverage("2.20"));
        //With whitespaces
        assertEquals(new GradePointAverageInKeywordsRangePredicate(
                        new FilterRange<>(new GradePointAverage("2.99"))),
                FilterUtil.parseGradePointAverage("    2.99     "));
        //Multiple values with whitespaces
        assertEquals(new GradePointAverageInKeywordsRangePredicate(
                        new FilterRange<>(new GradePointAverage("1.00"), new GradePointAverage("5.00"))),
                FilterUtil.parseGradePointAverage("    1.00  -   5.00   "));
    }

    @Test
    public void parseGradePointAverage_invalidCommandFormat_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        FilterUtil.parseGradePointAverage("  ");
    }

    @Test
    public void parseGradePointAverage_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        FilterUtil.parseGradePointAverage("5.01");
    }

    @Test
    public void parseRating_validInput_success() throws Exception {
        //Single value
        //No whitespaces
        assertEquals(new RatingInKeywordsRangePredicate(
                        new FilterRange<>(new Rating(2.2, 2.2, 2.2, 2.2))),
                FilterUtil.parseRating("2.2"));
        //With whitespaces
        assertEquals(new RatingInKeywordsRangePredicate(
                        new FilterRange<>(new Rating(2.9, 2.9, 2.9, 2.9))),
                FilterUtil.parseRating("    2.9     "));
        //Multiple values with whitespaces
        assertEquals(new RatingInKeywordsRangePredicate(
                        new FilterRange<>(new Rating(1.0, 1.0, 1.0, 1.0), new Rating(4.0, 4.0, 4.0, 4.0))),
                FilterUtil.parseRating("    1.001  -   4.001   "));
    }
    @Test
    public void parseInterviewDate_validInput_success() throws Exception {
        //Single value
        //No whitespaces
        assertEquals(new InterviewDateInKeywordsRangePredicate(
                        new FilterRange<>(
                                new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime("20180331")),
                                new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime("20180331")))),
                FilterUtil.parseInterviewDate("20180331"));
        //With whitespaces
        assertEquals(new InterviewDateInKeywordsRangePredicate(
                        new FilterRange<>(
                                new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime("20180331")),
                                new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime("20180331")))),
                FilterUtil.parseInterviewDate("  20180331     "));
        //Ranged values with whitespaces
        assertEquals(new InterviewDateInKeywordsRangePredicate(
                        new FilterRange<>(
                                new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime("20180331")),
                                new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime("20180401")))),
                FilterUtil.parseInterviewDate("20180331   -  20180401"));
    }

    @Test
    public void parseInterviewDate_invalidCommandFormat_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        FilterUtil.parseInterviewDate("  ");
    }

    @Test
    public void parseInterviewDate_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        FilterUtil.parseInterviewDate("20180100");
    }
}
