package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author trafalgarandre
public class ProfilePictureTest {

    @Test
    public void constructor_invalidProfilePicture_throwsIllegalArgumentException() {
        String invalidProfilePicture = "andre.jjp";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProfilePicture(invalidProfilePicture));
    }

    @Test
    public void isValidProfilePicture() {

        String temp;
        // invalid ProfilePicture
        assertFalse(ProfilePicture.isValidProfilePicture("91fae")); // random string
        assertFalse(ProfilePicture.isValidProfilePicture("phone.jpp")); // wrong ending
        temp = "abc.jpg";
        assertFalse(ProfilePicture.isValidProfilePicture(temp)
                && ProfilePicture.hasValidProfilePicture(temp)); // not exists

        // valid profile picture
        assertTrue(ProfilePicture.isValidProfilePicture("")); //no image => default image
        temp = "./src/test/data/images/alex.jpeg";
        assertTrue(ProfilePicture.isValidProfilePicture(temp)
                && ProfilePicture.hasValidProfilePicture(temp)); //absolute path
    }
}
