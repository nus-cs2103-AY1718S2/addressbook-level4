//@@author Kyholmes-test
package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

public class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void test_toString() {
        String date = "3/4/2017";
        String time = "2217";
        String dateTimeString = "3/4/2017 2217";

        DateTime toTest = new DateTime(dateTimeString);

        assertEquals(toTest.toString(), date + " " + time);
    }

    @Test
    public void test_isBefore_valid() throws ParseException {
        String dateTimeString = "3/4/2017 2217";

        assertTrue(DateTime.isBefore(dateTimeString));
    }

    @Test
    public void test_isBefore_invalid() throws ParseException {
        String dateTimeString = "3/4/2107 2217";

        assertFalse(DateTime.isBefore(dateTimeString));
    }

    @Test
    public void test_isAfterOrEqual_valid() throws ParseException {
        String dateTimeString = "3/4/2107 2217";

        assertTrue(DateTime.isAfterOrEqual(dateTimeString));
    }

    @Test
    public void test_isAfterOrEqual_invalid() throws ParseException {
        String dateTimeString = "3/4/2017 2217";

        assertFalse(DateTime.isAfterOrEqual(dateTimeString));
    }

    @Test
    public void test_isValidDateTime_valid() {
        String dateTimeString = "3/4/2017 2217";

        assertTrue(DateTime.isValidDateTime(dateTimeString));
    }

    @Test
    public void test_isValidDateTime_invalid() {
        String dateTimeString = "3 April 2017 2217";

        assertFalse(DateTime.isValidDateTime(dateTimeString));
    }
}
