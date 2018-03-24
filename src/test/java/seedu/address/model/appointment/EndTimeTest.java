package seedu.address.model.appointment;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author jlks96
public class EndTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartTime(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartTime(invalidTime));
    }
}
