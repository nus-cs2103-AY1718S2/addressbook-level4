package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author tiny767
public class FacebookPostCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullMessage_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new FacebookPostCommand(null);
    }

    @Test
    public void equals() {
        FacebookPostCommand fbPostCommand = new FacebookPostCommand("some message");
        // same object -> returns true
        assertTrue(fbPostCommand.equals(fbPostCommand));

        // same values -> returns true
        FacebookPostCommand sameFbPostCommand = new FacebookPostCommand("some message");
        assertTrue(fbPostCommand.equals(sameFbPostCommand));

        // different types -> returns false
        assertFalse(fbPostCommand.equals(1));

        // null -> returns false
        assertFalse(fbPostCommand.equals(null));

        FacebookPostCommand differentPostCommand = new FacebookPostCommand("some other message");
        // different content -> return false
        assertFalse(fbPostCommand.equals(differentPostCommand));

    }

}
