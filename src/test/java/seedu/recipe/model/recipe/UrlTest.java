//@@author RyanAngJY
package seedu.recipe.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recipe.testutil.Assert;

public class UrlTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Url(null));
    }

    @Test
    public void constructor_invalidUrl_throwsIllegalArgumentException() {
        String invalidUrl = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Url(invalidUrl));
    }

    @Test
    public void isValidUrl() {
        // blank Url
        assertFalse(Url.isValidUrl("")); // empty string
        assertFalse(Url.isValidUrl(" ")); // spaces only

        // invalid Url
        assertFalse(Url.isValidUrl("www.google.com")); // "http://" of "https://" not at the beginning of Url

        // valid Url
        assertTrue(Url.isValidUrl("http://www.google.com")); // "http://" at the beginning of Url
        assertTrue(Url.isValidUrl("https://www.google.com")); // "https://" at the beginning of Url
        assertTrue(Url.isValidUrl(" http://www.google.com")); // leading space
        assertTrue(Url.isValidUrl("http://www.google.com ")); // trailing space
        assertTrue(Url.isValidUrl("https://www.google.com.sg/search"
                + "?ei=1oqfWryFJYvtvgS2kovIDw&q=long+url+trying+to+add+words&oq"
                + "=long+url+trying+to+add+words&gs_l=psy-ab.3...16827.19809.0.19937"
                + ".20.20.0.0.0.0.131.1429.15j3.18.0....0...1c.1.64.psy-ab..2.9.695...0j0"
                + "i20i263k1j0i22i30k1j33i160k1j33i21k1j33i22i29i30k1.0.ToeND2eqJXA")); // long url
    }
}
//@@author
