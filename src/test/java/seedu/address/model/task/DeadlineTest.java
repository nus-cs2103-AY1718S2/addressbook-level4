package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class DeadlineTest {

    private LocalDate now = LocalDate.now();
    private LocalDate yesterday = now.minusDays(1);
    private LocalDate tomorrow = now.plusDays(1);
    private String dateYesterday = yesterday.toString();
    private String correctDateYesterday = dateYesterday.substring(8, 10) + "-" + dateYesterday.substring(5, 7)
        + "-" + dateYesterday.substring(0, 4);
    private String dateTomorrow = tomorrow.toString();
    private String correctDateTomorrow = dateTomorrow.substring(8, 10) + "-" + dateTomorrow.substring(5, 7)
        + "-" + dateTomorrow.substring(0, 4);

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void constructor_invalidDeadline_throwsDateTimeParseException() {
        String invalidDeadline = "";
        Assert.assertThrows(DateTimeParseException.class, () -> new Deadline(invalidDeadline));
    }

    @Test
    public void isValidDeadline() {
        // invalid dates
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("91")); // numbers
        assertFalse(Deadline.isValidDeadline("02/04/2017")); // / instead of -
        assertFalse(Deadline.isValidDeadline(correctDateYesterday)); // scheduled yesterday

        // valid dates
        assertTrue(Deadline.isValidDeadline(correctDateTomorrow));
    }
}

