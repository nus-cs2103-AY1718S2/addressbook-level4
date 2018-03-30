package seedu.address.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeParseException;

import org.junit.Test;
import seedu.address.testutil.Assert;

public class InterviewDateUtilTest {

    @Test
    public void parse_invalidDateTime_throwsDateTimeParseException() {
        String invalidDateTime1 = "20000000";
        Assert.assertThrows(DateTimeParseException.class,
                () -> InterviewDateUtil.formHigherInterviewDateTime(invalidDateTime1));
        String invalidDateTime2 = "20180229";
        Assert.assertThrows(DateTimeParseException.class,
                () -> InterviewDateUtil.formHigherInterviewDateTime(invalidDateTime2));
        String invalidDateTime3 = "20180631";
        Assert.assertThrows(DateTimeParseException.class,
                () -> InterviewDateUtil.formHigherInterviewDateTime(invalidDateTime3));
        String invalidDateTime4 = "20181313";
        Assert.assertThrows(DateTimeParseException.class,
                () -> InterviewDateUtil.formHigherInterviewDateTime(invalidDateTime4));
    }

    @Test
    public void parse_validDateTime_parseSuccess() {
        String validDateTime1 = "20180331";
        String validDateTime2 = "20200229";
        LocalDateTime expectedDateTime = LocalDateTime.of(2018, Month.MARCH,31,0,0,0);
        assertEquals(expectedDateTime, InterviewDateUtil.formLowerInterviewDateTime(validDateTime1));
        expectedDateTime = LocalDateTime.of(2020, Month.FEBRUARY,29,23,59,59);
        assertEquals(expectedDateTime, InterviewDateUtil.formHigherInterviewDateTime(validDateTime2));
    }

    @Test
    public void isValidDateTime() {
        // null date time
        Assert.assertThrows(NullPointerException.class, () ->
                InterviewDateUtil.isValidInterviewDate(null));

        // invalid date time
        assertFalse(InterviewDateUtil.isValidInterviewDate("")); // empty string
        assertFalse(InterviewDateUtil.isValidInterviewDate(" ")); // spaces only
        assertFalse(InterviewDateUtil.isValidInterviewDate("2018")); // no month and date
        assertFalse(InterviewDateUtil.isValidInterviewDate("year20180101")); // non-numeric
        assertFalse(InterviewDateUtil.isValidInterviewDate("-20180101")); // negative symbol before digits
        assertFalse(InterviewDateUtil.isValidInterviewDate("201 80101")); // spaces within digits
        assertFalse(InterviewDateUtil.isValidInterviewDate("2018.5.3")); // year and month
        // valid expectedGraduationYear
        assertTrue(InterviewDateUtil.isValidInterviewDate("20180228"));
        assertTrue(InterviewDateUtil.isValidInterviewDate("20240101"));
    }
}
