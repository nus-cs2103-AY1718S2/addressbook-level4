package seedu.progresschecker.model.issue;

import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

import seedu.progresschecker.model.issues.Labels;
import seedu.progresschecker.testutil.Assert;

//@@author adityaa1998
public class LabelsTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Labels(null));
    }

    @Test
    public void constructor_invalidLabelName_throwsIllegalArgumentException() {
        String invalidLabelName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Labels(invalidLabelName));
    }

    @Test
    public void isValidLabelName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Labels.isValidLabel(null));

        // valid name
        assertTrue(Labels.isValidLabel("peter jack")); // alphabets only
        assertTrue(Labels.isValidLabel("12345")); // numbers only
        assertTrue(Labels.isValidLabel("peter the 2nd")); // alphanumeric characters
        assertTrue(Labels.isValidLabel("Capital Tan")); // with capital letters
        assertTrue(Labels.isValidLabel("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Labels.isValidLabel("peter*")); // contains non-alphanumeric characters
        assertTrue(Labels.isValidLabel("^")); // only non-alphanumeric characters
    }
}
