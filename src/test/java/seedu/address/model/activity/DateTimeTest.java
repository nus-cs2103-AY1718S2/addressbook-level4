package seedu.address.model.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Kyomian
public class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void constructor_invalidDateTime_throwsIllegalArgumentException() {
        String invalidDateTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidDateTime));
    }

    @Test
    public void isValidDateTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));
    }

    @Test
    public void isValidDateTime_validDateTime() {
        assertTrue(DateTime.isValidDateTime("01/08/1995 12:00"));
        assertTrue(DateTime.isValidDateTime("03/03/2019 12:00"));
        assertTrue(DateTime.isValidDateTime("3/3/2019 00:00"));
        assertTrue(DateTime.isValidDateTime("3/3/2019 23:59"));

    }

    @Test
    public void isValidDateTime_invalidDateTime() {
        assertFalse(DateTime.isValidDateTime(""));
        assertFalse(DateTime.isValidDateTime(" "));
        assertFalse(DateTime.isValidDateTime("2019/03/03 12:00")); // YYMMDD
        assertFalse(DateTime.isValidDateTime("12:00 03/03/2019")); // time before date
        assertFalse(DateTime.isValidDateTime("03-03-2019 12:00")); // dash, instead of slash
        assertFalse(DateTime.isValidDateTime("32/12/2018 12:00")); // wrong day
        assertFalse(DateTime.isValidDateTime("1/13/2018 10:00")); // wrong month
        assertFalse(DateTime.isValidDateTime("1/12/0000 12:00")); // wrong year
        assertFalse(DateTime.isValidDateTime("1/8/1995 25:00")); // wrong time
    }
}
