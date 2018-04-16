package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author lithiumlkid
public class RatingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rating(null));
    }

    @Test
    public void constructor_invalidRating_throwsIllegalArgumentException() {
        String invalidRating = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Rating(invalidRating));
    }

    @Test
    public void isValidRating() {
        // null rating number
        Assert.assertThrows(NullPointerException.class, () -> Rating.isValidRating(null));

        // invalid rating numbers
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating(" ")); // spaces only
        assertFalse(Rating.isValidRating("-1")); // negative
        assertFalse(Rating.isValidRating("rating")); // non-numeric
        assertFalse(Rating.isValidRating("1a")); // alphabets within digits
        assertFalse(Rating.isValidRating("1 1")); // spaces within digits

        // valid rating numbers
        assertTrue(Rating.isValidRating("1")); // within range
        assertTrue(Rating.isValidRating("5"));
    }

    @Test
    public void isEqualRating() {
        Rating x  = new Rating("1");
        Rating y = new Rating("1");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}
