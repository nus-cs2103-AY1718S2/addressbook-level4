package seedu.address.model.event;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CalendarEventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new CalendarEvent(null, null, null, null, null));

    }
}
