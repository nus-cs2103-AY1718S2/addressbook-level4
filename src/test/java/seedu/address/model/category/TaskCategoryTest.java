package seedu.address.model.category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author CYX28
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

        // invalid task category name
        assertFalse(TaskCategory.isValidTaskCategoryName("")); // empty string
        assertFalse(TaskCategory.isValidTaskCategoryName(" ")); // spaces only
        assertFalse(TaskCategory.isValidTaskCategoryName("^")); // only non-alphanumeric characters
        assertFalse(TaskCategory.isValidTaskCategoryName("meet&ing")); // contains non-alphanumeric characters
        assertFalse(TaskCategory.isValidTaskCategoryName("project meeting")); // contains spaces

        // valid task category name
        assertTrue(TaskCategory.isValidTaskCategoryName("firstcategory")); // alphabets only without whitespaces
        assertTrue(TaskCategory.isValidTaskCategoryName("12345")); // numbers only
        assertTrue(TaskCategory.isValidTaskCategoryName("firstcategory5")); // alphanumeric characters
    }

}
