// @@author anh2111
package seedu.address.model.report;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ProportionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Proportion(null, 0, 0));
    }
}
// @@author
