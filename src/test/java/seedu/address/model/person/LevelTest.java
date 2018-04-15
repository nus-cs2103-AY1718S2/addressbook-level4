package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

//@@author aussiroth
public class LevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Level(null));
    }

    @Test
    public void isValidLevel() {
        // null level
        Assert.assertThrows(NullPointerException.class, () -> Level.isValidLevel(null));

        // invalid levels
        assertFalse(Level.isValidLevel("JC")); // not accepted levels
        assertFalse(Level.isValidLevel("middle Sec"));

        // valid levels
        assertTrue(Level.isValidLevel("upper Sec"));
        assertTrue(Level.isValidLevel("lower Pri"));
        assertTrue(Level.isValidLevel("Upper pri")); //check case insensitive
        assertTrue(Level.isValidLevel("Lower sec"));
    }

    @Test
    public void checkLevelEquality() {
        //test level against non-level type
        assertFalse(new Level("upper Sec").equals(null));
        assertFalse(new Level("upper Sec").equals(new Tag("upper Sec")));
        //test correctly returns equal if level string is the same
        assertTrue(new Level("upper Sec").equals(new Level("upper Sec")));
    }

    @Test
    public void checkLevelHashCode() {
        Level level = new Level("upper sec");
        assertTrue(level.hashCode() == level.value.hashCode());
        level = new Level("lower sec");
        assertTrue(level.hashCode() == level.value.hashCode());
        level = new Level("lower pri");
        assertTrue(level.hashCode() == level.value.hashCode());
    }
}
