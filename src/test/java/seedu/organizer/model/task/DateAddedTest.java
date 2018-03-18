package seedu.organizer.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import seedu.organizer.testutil.Assert;

//@@author dominickenn
public class DateAddedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateAdded(null));
    }

    @Test
    public void constructor_invalidDateAdded_throwsIllegalArgumentException() {
        String invalidDateAdded = "2018";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDateAdded));
    }

    @Test
    public void isValidDateAdded() {
        // null deadline
        Assert.assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // blank dateadded
        assertTrue(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only

        // missing parts
        assertFalse(Deadline.isValidDeadline("2018-02")); // missing date
        assertFalse(Deadline.isValidDeadline("12-02")); // missing year
        assertFalse(Deadline.isValidDeadline("2019")); // missing month and date
        assertFalse(Deadline.isValidDeadline("12")); // missing year and date

        // invalid parts
        assertFalse(Deadline.isValidDeadline("17-12-12")); // invalid year
        assertFalse(Deadline.isValidDeadline("2019-20-09")); // invalid month
        assertFalse(Deadline.isValidDeadline("2016-02-40")); // invalid date
        assertFalse(Deadline.isValidDeadline("2017-2-09")); // single numbered months should be declared '0x'
        assertFalse(Deadline.isValidDeadline("2017-02-9")); // single numbered dates should be declared '0x'
        assertFalse(Deadline.isValidDeadline("12-30-2017")); // wrong format of MM-DD-YYYY
        assertFalse(Deadline.isValidDeadline("30-12-2017")); // wrong format of DD-MM-YYYY
        assertFalse(Deadline.isValidDeadline(" 2017-08-09")); // leading space
        assertFalse(Deadline.isValidDeadline("2017-08-09 ")); // trailing space
        assertFalse(Deadline.isValidDeadline("2017/09/09")); // wrong symbol

        // valid dateadded
        assertTrue(Deadline.isValidDeadline("2018-03-11"));
        assertTrue(Deadline.isValidDeadline("2017-02-31"));  // dates that have already passed
        assertTrue(Deadline.isValidDeadline("3000-03-23"));   // dates in the far future
    }

    @Test
    public void hashCode_equals() {
        DateAdded testDateAdded = new DateAdded("2018-09-09");
        LocalDate testDateAddedValue = LocalDate.parse("2018-09-09");
        assertEquals(testDateAdded.hashCode(), testDateAddedValue.hashCode());
    }
}
