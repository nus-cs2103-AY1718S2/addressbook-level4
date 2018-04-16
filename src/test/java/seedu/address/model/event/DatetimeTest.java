package seedu.address.model.event;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

//@@author x3tsunayh

public class DatetimeTest {

    @Test
    public void isValidDate() {
        // invalid datetimes
        assertFalse(Datetime.isValidDatetime("")); // empty string
        assertFalse(Datetime.isValidDatetime(" ")); // whitespace only
        assertFalse(Datetime.isValidDatetime("20180401")); // numbers only
        assertFalse(Datetime.isValidDatetime("1st April 2018")); // characters only
        assertFalse(Datetime.isValidDatetime("test123")); // numbers and characters
        assertFalse(Datetime.isValidDatetime("2018-09-1 1845")); // invalid date format
        assertFalse(Datetime.isValidDatetime("2018-02-29 1915")); // invalid day
        assertFalse(Datetime.isValidDatetime("2018-13-02 2030")); // invalid month
        assertFalse(Datetime.isValidDatetime("18-09-02 2030")); // invalid year
        assertFalse(Datetime.isValidDatetime("2018-09-02 2410")); // invalid hour
        assertFalse(Datetime.isValidDatetime("2018-09-02 2060")); // invalid minute

        // valid datetimes
        assertTrue(Datetime.isValidDatetime("2018-02-01 0800"));
        assertTrue(Datetime.isValidDatetime("2018-05-29 1030"));
        assertTrue(Datetime.isValidDatetime("2018-10-30 1345"));
        assertTrue(Datetime.isValidDatetime("2018-12-31 2015"));
    }
}
