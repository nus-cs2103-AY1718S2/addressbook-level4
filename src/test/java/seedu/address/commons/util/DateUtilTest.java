package seedu.address.commons.util;
//@@author SuxianAlicia

import static org.junit.Assert.assertFalse;

import java.time.format.DateTimeParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidDate_nullSentence_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        DateUtil.isValidDate(null);
    }

    // Test for isValidDate is rigorously tested in StartDateTest, EndDateTest and DeliveryDateTest.
    @Test
    public void isValidDate_invalidString_returnFalse() {
        // empty string
        assertFalse(DateUtil.isValidDate("  "));

        // does not follow validation format
        assertFalse(DateUtil.isValidDate(" * * *"));
        assertFalse(DateUtil.isValidDate("6 April 2020"));
        assertFalse(DateUtil.isValidDate("06-Apr-2020"));

        // has incorrect number of digits
        assertFalse(DateUtil.isValidDate("10-5-2020"));
        assertFalse(DateUtil.isValidDate("1-05-2020"));
        assertFalse(DateUtil.isValidDate("03-04-20"));
        assertFalse(DateUtil.isValidDate("30-02-2020"));
    }

    @Test
    public void convertStringToDate_nullSentence_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        DateUtil.convertStringToDate(null);
    }

    @Test
    public void convertStringToDate_invalidString_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);

        // empty string
        DateUtil.convertStringToDate(" ");

        // does not follow validation format
        DateUtil.convertStringToDate(" * * *");
        DateUtil.convertStringToDate("6 April 2020");
        DateUtil.convertStringToDate("06-Apr-2020");

        // has incorrect number of digits
        DateUtil.convertStringToDate("10-5-2020");
        DateUtil.convertStringToDate("1-05-2020");
        DateUtil.convertStringToDate("03-04-20");
        DateUtil.convertStringToDate("30-02-2020");
    }
}
