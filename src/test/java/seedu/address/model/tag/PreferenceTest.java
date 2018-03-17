package seedu.address.model.tag;

import org.junit.Test;

import seedu.address.testutil.Assert;
public class PreferenceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Preference(null));
    }

    @Test
    public void constructor_invalidPreferenceName_throwsIllegalArgumentException() {
        String invalidPreferenceName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Preference(invalidPreferenceName));
    }

    @Test
    public void isValidPreferenceName() {
        // null preference name
        Assert.assertThrows(NullPointerException.class, () -> Preference.isValidTagName(null));
    }
}
