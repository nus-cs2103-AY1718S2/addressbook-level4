package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.ExpectedGraduationYearInKeywordsRangePredicate;
import seedu.address.model.person.Rating;
import seedu.address.model.person.RatingInKeywordsRangePredicate;

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
}
