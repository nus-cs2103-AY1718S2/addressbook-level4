package seedu.address.model.person;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.model.person.Rating.DEFAULT_NULL_RATING;

import org.junit.Test;

import com.vdurmont.emoji.EmojiParser;

public class RatingTest {

    @Test
    public void constructorTest_noRatingSpecified_nullRating() {
        Rating r = new Rating();
        assertEquals((Integer) DEFAULT_NULL_RATING, (Integer) r.value);
    }

    @Test
    public void equalTest_notEqual() {
        Rating a = new Rating();
        Rating b = new Rating(5 + "");
        assertNotEquals(a, b);
    }

    @Test
    public void equalTest_equal() {
        Rating a = new Rating(5 + "");
        Rating b = new Rating(5 + "");
        assertEquals(a, b);
    }

    @Test
    public void toStringTest() {
        Rating r = new Rating("123");
        assertEquals(r.toString(), "123");
    }

    @Test
    public void getRatingDisplayTest() {
        Rating r = new Rating();
        assertEquals(r.INVALID_RATING_DISPLAY, r.getRatingDisplay());

        Rating a = new Rating(1 + "");
        assertEquals(a.getRatingDisplay(), EmojiParser.parseToUnicode(a.RATING_DISPLAY));
    }
}
