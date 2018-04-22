package seedu.address.model.person;
//@@author crizyli
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CalendarIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CalendarId(null));
    }

    @Test
    public void construct_success() {
        CalendarId calendarId = new CalendarId("cid");
        assertEquals(calendarId.getValue(), "cid");
    }
}
