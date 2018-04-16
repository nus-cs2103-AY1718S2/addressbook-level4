package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author lithiumlkid
public class AvatarTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Avatar(null));
    }

    @Test
    public void constructor_invalidAvatar_throwsIllegalArgumentException() {
        String invalidAvatar = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Avatar(invalidAvatar));
    }

    @Test
    public void isValidAvatar() {
        // null avatar number
        Assert.assertThrows(NullPointerException.class, () -> Avatar.isValidAvatar(null));

        // invalid avatar numbers
        assertFalse(Avatar.isValidAvatar("")); // empty string
        assertFalse(Avatar.isValidAvatar(" ")); // spaces only
        assertFalse(Avatar.isValidAvatar("avatar.gif")); // invalid filtype
        assertFalse(Avatar.isValidAvatar("avatar")); // no file type
        assertFalse(Avatar.isValidAvatar("a a")); // spaces within digits

        // valid avatar numbers
        assertTrue(Avatar.isValidAvatar("avatar.png")); // png file
        assertTrue(Avatar.isValidAvatar("/file/path/to/avatar.png")); //mac file path
        assertTrue(Avatar.isValidAvatar("C:\\file\\path\\avatar.png")); // windows file path
    }

    @Test
    public void isEqualAvatar() {
        Avatar x  = new Avatar("avatar.png");
        Avatar y = new Avatar("avatar.png");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }

}
