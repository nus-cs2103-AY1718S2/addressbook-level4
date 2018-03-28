package seedu.address.model.category;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TaskCategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskCategory(null));
    }

    @Test
    public void constructor_invalidTaskCategoryName_throwsIllegalArgumentException() {
        String invalidCategoryName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskCategory(invalidCategoryName));
    }

    @Test
    public void isValidTaskCategoryName() {
        // null task category name
        Assert.assertThrows(NullPointerException.class, () -> TaskCategory.isValidTaskCategoryName(null));
    }

}
