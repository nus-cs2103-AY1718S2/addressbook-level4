//@@author IzHoBX
package seedu.address.model.notification;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

public class NotificationTimeTest {

    @Test
    public void isTodayTest_pastDate_fail() {
        assertFalse((new NotificationTime(2014, 2, 1, 10, 12, 13)).isToday());
    }

    @Test
    public void isTodayTest_todayDate_success() {
        assertTrue((new NotificationTime(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                12, 12, 12)).isToday());
    }

    @Test
    public void isTodayTest_futureDate_fail() {
        assertFalse((new NotificationTime(2024, 2, 1, 10, 12, 13)).isToday());
    }

}
