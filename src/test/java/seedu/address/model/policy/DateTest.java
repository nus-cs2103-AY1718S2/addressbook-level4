//@@author ValerianRey

package seedu.address.model.policy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        //all parameters are null
        Assert.assertThrows(NullPointerException.class, () -> new Date(null, null, null));

        //one parameter is null
        Assert.assertThrows(NullPointerException.class, () -> new Date(null, Month.JANUARY, 2018));
        Assert.assertThrows(NullPointerException.class, () -> new Date(1, null, 2018));
        Assert.assertThrows(NullPointerException.class, () -> new Date(1, Month.JANUARY, null));
    }

    @Test public void constructor_invalidDate_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(0, Month.JANUARY, 0));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null, null, null));

        // invalid date
        assertFalse(Date.isValidDate(0, Month.JANUARY, 2018));      //day is 0
        assertFalse(Date.isValidDate(1, Month.FEBRUARY, 1949));     //year is less than 1950
        assertFalse(Date.isValidDate(10, Month.MARCH, 2151));       //year is more than 2150
        assertFalse(Date.isValidDate(29, Month.FEBRUARY, 2018));    //day is February 29th of a non-leap year
        assertFalse(Date.isValidDate(32, Month.MAY, 2018));         //day is more than 31
        assertFalse(Date.isValidDate(31, Month.APRIL, 2010));       //day is 31 on a month that is 30-days long
        assertFalse(Date.isValidDate(31, Month.JUNE, 2010));        //day is 31 on a month that is 30-days long
        assertFalse(Date.isValidDate(31, Month.SEPTEMBER, 2010));   //day is 31 on a month that is 30-days long
        assertFalse(Date.isValidDate(31, Month.NOVEMBER, 2010));    //day is 31 on a month that is 30-days long
        assertFalse(Date.isValidDate(30, Month.FEBRUARY, 2016));    //day is 30 on a month that is 29-days long
        assertFalse(Date.isValidDate(-10, Month.JUNE, 2000));       //day is negative

        // valid date
        assertTrue(Date.isValidDate(1, Month.JANUARY, 2018));
        assertTrue(Date.isValidDate(31, Month.JANUARY, 2018));
        assertTrue(Date.isValidDate(28, Month.FEBRUARY, 2018));
        assertTrue(Date.isValidDate(29, Month.FEBRUARY, 2016));    //February 29th of a leap year
        assertTrue(Date.isValidDate(31, Month.MARCH, 2000));
        assertTrue(Date.isValidDate(30, Month.APRIL, 1950));       //Limit year
        assertTrue(Date.isValidDate(31, Month.MAY, 2100));
        assertTrue(Date.isValidDate(30, Month.JUNE, 2150));        //Limit year
        assertTrue(Date.isValidDate(31, Month.JULY, 2000));
        assertTrue(Date.isValidDate(31, Month.AUGUST, 2016));
        assertTrue(Date.isValidDate(30, Month.SEPTEMBER, 2000));
        assertTrue(Date.isValidDate(31, Month.OCTOBER, 2000));
        assertTrue(Date.isValidDate(30, Month.NOVEMBER, 2000));
        assertTrue(Date.isValidDate(31, Month.DECEMBER, 2000));
    }

    @Test
    public void compareTo() {
        Date d1 = new Date(1, Month.JANUARY, 2000);
        Date d2 = new Date(2, Month.JANUARY, 2000);
        Date d3 = new Date(1, Month.FEBRUARY, 2000);
        Date d4 = new Date(7, Month.FEBRUARY, 2000);
        Date d5 = new Date(31, Month.DECEMBER, 2000);
        Date d6 = new Date(1, Month.JANUARY, 2001);
        assertTrue(d1.compareTo(d2) < 0);
        assertTrue(d2.compareTo(d3) < 0);
        assertTrue(d4.compareTo(d3) > 0);
        assertTrue(d4.compareTo(d4) == 0);
        assertTrue(d4.compareTo(d5) < 0);
        assertTrue(d6.compareTo(d5) > 0);
    }
}
