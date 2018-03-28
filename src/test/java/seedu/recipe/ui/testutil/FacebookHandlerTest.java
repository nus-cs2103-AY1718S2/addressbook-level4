package seedu.recipe.ui.testutil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.recipe.ui.util.FacebookHandler.ACCESS_TOKEN_IDENTIFIER;
import static seedu.recipe.ui.util.FacebookHandler.REDIRECT_DOMAIN;

import org.junit.Test;

import seedu.recipe.ui.util.FacebookHandler;

//@@author RyanAngJY
public class FacebookHandlerTest {
    public static final String ACCESS_TOKEN_STUB = "123";
    public static final String VALID_EMBEDDED_ACCESS_TOKEN = REDIRECT_DOMAIN
            + ACCESS_TOKEN_IDENTIFIER + ACCESS_TOKEN_STUB + "&";
    public static final String INVALID_EMBEDDED_ACCESS_TOKEN = REDIRECT_DOMAIN
            + ACCESS_TOKEN_STUB; // without token identifier

    @Test
    public void hasAccessToken() {
        FacebookHandler.setAccessToken(null);
        assertFalse(FacebookHandler.hasAccessToken());

        FacebookHandler.setAccessToken(ACCESS_TOKEN_STUB);
        assertTrue(FacebookHandler.hasAccessToken());
    }

    @Test
    public void extractAccessToken() {
        assertTrue(FacebookHandler.extractAccessToken(VALID_EMBEDDED_ACCESS_TOKEN)
                .equals(ACCESS_TOKEN_STUB));
    }

    @Test
    public void checkAndSetAccessToken() {
        assertTrue(FacebookHandler.checkAndSetAccessToken(VALID_EMBEDDED_ACCESS_TOKEN));
        assertFalse(FacebookHandler.checkAndSetAccessToken(INVALID_EMBEDDED_ACCESS_TOKEN));
    }
}
//@@author
