//@@author RyanAngJY
package seedu.recipe.model.recipe;

import org.junit.Test;

import seedu.recipe.testutil.Assert;

public class UrlTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Url(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidUrl = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Url(invalidUrl));
    }

}
//@@author
