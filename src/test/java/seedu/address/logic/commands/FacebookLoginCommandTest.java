package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author tiny767
public class FacebookLoginCommandTest {
    @Test
    public void equals() {
        FacebookLoginCommand fbLoginCommand = new FacebookLoginCommand();
        // same object -> returns true
        assertTrue(fbLoginCommand.equals(fbLoginCommand));

        // same values -> returns true
        FacebookLoginCommand anotherFbLoginCommand = new FacebookLoginCommand();
        assertTrue(fbLoginCommand.equals(anotherFbLoginCommand));

        // different types -> returns false
        assertFalse(fbLoginCommand.equals(1));

        // null -> returns false
        assertFalse(fbLoginCommand.equals(null));

    }
}
