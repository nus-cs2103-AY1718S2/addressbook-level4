package seedu.organizer.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.organizer.testutil.Assert;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void isValidDescription() {
        // null organizer
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // valid descriptions
        assertTrue(Description.isValidDescription("")); // empty string - Description is not a compulsory field
        assertTrue(Description.isValidDescription(" ")); // spaces only - Description is not a compulsory field
        assertTrue(Description.isValidDescription("Practice MA1101R past year questions"));
        assertTrue(Description.isValidDescription("!")); // one character
        assertTrue(Description.isValidDescription("Add new sort feature / Update README.md / Refactor Address to "
            + "Email")); // long description
    }

    //@@guekling
    @Test
    public void isHashCodeEquals() {
        Description testDescription = new Description("CS2103T Testing");
        String testDescriptionValue = "CS2103T Testing";
        assertEquals(testDescription.hashCode(), testDescriptionValue.hashCode());
    }
}
