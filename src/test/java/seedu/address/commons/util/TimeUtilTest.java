package seedu.address.commons.util;
//@@author SuxianAlicia

import static org.junit.Assert.assertFalse;

import java.time.format.DateTimeParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TimeUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidTime_nullSentence_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        TimeUtil.isValidTime(null);
    }

    // Test for isValidTime is rigorously tested in StartTimeTest and EndTimeTest.
    @Test
    public void isValidTime_invalidString_returnFalse() {
        // empty string
        assertFalse(TimeUtil.isValidTime("  "));

        // does not follow validation format
        assertFalse(TimeUtil.isValidTime(" * * *"));
        assertFalse(TimeUtil.isValidTime("0212"));
        assertFalse(TimeUtil.isValidTime("02-12"));

        // has incorrect number of digits
        assertFalse(TimeUtil.isValidTime("12:500"));
        assertFalse(TimeUtil.isValidTime("240:50"));
        assertFalse(TimeUtil.isValidTime("2:50"));
        assertFalse(TimeUtil.isValidTime("21:5"));
    }

    @Test
    public void convertStringToTime_nullSentence_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        TimeUtil.convertStringToTime(null);
    }

    @Test
    public void convertStringToTime_invalidString_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);

        // empty string
        TimeUtil.convertStringToTime(" ");

        // does not follow validation format
        TimeUtil.convertStringToTime(" * * *");
        TimeUtil.convertStringToTime("0212");
        TimeUtil.convertStringToTime("02-12");

        // has incorrect number of digits
        TimeUtil.convertStringToTime("12:500");
        TimeUtil.convertStringToTime("240:50");
        TimeUtil.convertStringToTime("2:50");
        TimeUtil.convertStringToTime("21:5");

    }
}
