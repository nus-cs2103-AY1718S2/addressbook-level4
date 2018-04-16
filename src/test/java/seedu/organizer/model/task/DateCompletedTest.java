package seedu.organizer.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import seedu.organizer.testutil.Assert;

//@@author natania-d-reused
// Reused from team member guekling
public class DateCompletedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateCompleted(null));
    }

    @Test
    public void constructor_invalidDateCompleted_throwsIllegalArgumentException() {
        String invalidDateCompleted = "2018";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateCompleted(invalidDateCompleted));
    }

    @Test
    public void isValidDateCompleted() {
        // null dateCompleted
        Assert.assertThrows(NullPointerException.class, () -> DateCompleted.isValidDateCompleted(null));

        // blank dateCompleted
        assertFalse(DateCompleted.isValidDateCompleted("")); // empty string
        assertFalse(DateCompleted.isValidDateCompleted(" ")); // spaces only


        // missing parts
        assertFalse(DateCompleted.isValidDateCompleted("2018-02")); // missing date
        assertFalse(DateCompleted.isValidDateCompleted("12-02")); // missing year
        assertFalse(DateCompleted.isValidDateCompleted("2019")); // missing month and date
        assertFalse(DateCompleted.isValidDateCompleted("12")); // missing year and date

        // invalid parts
        assertFalse(DateCompleted.isValidDateCompleted("17-12-12")); // invalid year
        assertFalse(DateCompleted.isValidDateCompleted("2019-20-09")); // invalid month
        assertFalse(DateCompleted.isValidDateCompleted("2016-02-40")); // invalid date
        assertFalse(DateCompleted.isValidDateCompleted("2017-2-09")); // single numbered months should be declared '0x'
        assertFalse(DateCompleted.isValidDateCompleted("2017-02-9")); // single numbered dates should be declared '0x'
        assertFalse(DateCompleted.isValidDateCompleted("12-30-2017")); // wrong format of MM-DD-YYYY
        assertFalse(DateCompleted.isValidDateCompleted("30-12-2017")); // wrong format of DD-MM-YYYY
        assertFalse(DateCompleted.isValidDateCompleted(" 2017-08-09")); // leading space
        assertFalse(DateCompleted.isValidDateCompleted("2017-08-09 ")); // trailing space
        assertFalse(DateCompleted.isValidDateCompleted("2017/09/09")); // wrong symbol

        // valid dateCompleted
        assertTrue(DateCompleted.isValidDateCompleted("2018-03-11"));
        assertTrue(DateCompleted.isValidDateCompleted("2017-02-31"));  // dates that have already passed
        assertTrue(DateCompleted.isValidDateCompleted("3000-03-23"));   // dates in the far future
    }

    @Test
    public void hashCode_equals() {
        DateCompleted testDateCompleted = new DateCompleted("2018-09-09");
        LocalDate testDateCompletedValue = LocalDate.parse("2018-09-09");
        assertEquals(testDateCompleted.hashCode(), testDateCompletedValue.hashCode());
    }
}
