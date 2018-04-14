package seedu.address.model.tag;
//@@author SuxianAlicia
import org.junit.Test;

import seedu.address.testutil.Assert;

public class PreferenceTest {

    @Test
    public void constructor_nullPreferenceTagName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Preference(null));
    }

    @Test
    public void constructor_invalidPreferenceTagName_throwsIllegalArgumentException() {
        String invalidPreferenceTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Preference(invalidPreferenceTagName));
    }

    @Test
    public void isValidPreferenceTagName() {
        // null preference name
        Assert.assertThrows(NullPointerException.class, () -> Preference.isValidTagName(null));
    }
}
