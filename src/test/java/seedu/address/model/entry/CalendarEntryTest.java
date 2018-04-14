package seedu.address.model.entry;
//@@author SuxianAlicia
import org.junit.Test;

import seedu.address.testutil.Assert;

public class CalendarEntryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new CalendarEntry(null, null, null, null, null));

    }
}
