package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
}
