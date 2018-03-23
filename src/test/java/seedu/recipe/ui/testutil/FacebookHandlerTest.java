package seedu.recipe.ui.testutil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.recipe.ui.util.FacebookHandler.ACCESS_TOKEN_IDENTIFIER;
import static seedu.recipe.ui.util.FacebookHandler.REDIRECT_DOMAIN;

import org.junit.Test;

import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.Assert;
import seedu.recipe.ui.util.FacebookHandler;

//@@author RyanAngJY
public class FacebookHandlerTest {
    public static final String VALID_ACCESS_TOKEN = "123";
    public static final String VALID_EMBEDDED_ACCESS_TOKEN = REDIRECT_DOMAIN
            + ACCESS_TOKEN_IDENTIFIER + VALID_ACCESS_TOKEN + "&";
    public static final String INVALID_EMBEDDED_ACCESS_TOKEN = REDIRECT_DOMAIN
            + VALID_ACCESS_TOKEN; // without token identifier

    @Test
    public void hasAccessToken() {
        assertFalse(FacebookHandler.hasAccessToken());
        FacebookHandler.setAccessToken(VALID_EMBEDDED_ACCESS_TOKEN);
        assertTrue(FacebookHandler.hasAccessToken());
    }

    @Test
    public void checkAndSetAccessToken() {
        assertTrue(FacebookHandler.checkAndSetAccessToken(VALID_EMBEDDED_ACCESS_TOKEN));
        assertFalse(FacebookHandler.checkAndSetAccessToken(INVALID_EMBEDDED_ACCESS_TOKEN));
    }

    @Test
    public void postRecipeOnFacebook() {
        Assert.assertThrows(NullPointerException.class, () ->
                FacebookHandler.postRecipeOnFacebook(null));
//        FacebookHandler.postRecipeOnFacebook(new Recipe(new Name("asd"));
    }
}
//@@author
