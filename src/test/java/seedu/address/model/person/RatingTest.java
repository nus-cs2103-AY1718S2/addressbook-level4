package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RatingTest {

    private final Rating rating = new Rating(3, 3,
            4, 3.5);
    @Test
    public void isValidScore() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Rating.isValidScore(null));

        // invalid scores
        assertFalse(Rating.isValidScore((double) 0));
        assertFalse(Rating.isValidScore(5.5));
        assertFalse(Rating.isValidScore((double) -3));

        // valid phone numbers
        assertTrue(Rating.isValidScore((double) 1));
        assertTrue(Rating.isValidScore(3.5));
        assertTrue(Rating.isValidScore((double) 5));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(rating.equals(rating));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Rating ratingCopy = new Rating(rating.getTechnicalSkillsScore(), rating.communicationSkillsScore,
                rating.getProblemSolvingSkillsScore(), rating.getExperienceScore());
        assertTrue(rating.equals(ratingCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(rating.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(rating.equals(null));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Rating differentRating = new Rating(1, 1,
                1, 1);
        assertFalse(rating.equals(differentRating));
    }
}
