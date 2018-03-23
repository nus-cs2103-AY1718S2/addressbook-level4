package seedu.recipe.ui.testutil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.recipe.ui.util.FacebookHandler.ACCESS_TOKEN_IDENTIFIER;
import static seedu.recipe.ui.util.FacebookHandler.REDIRECT_DOMAIN;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.junit.Test;

import com.restfb.exception.FacebookNetworkException;
import com.restfb.exception.FacebookOAuthException;

import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.Assert;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.ui.util.FacebookHandler;

//@@author RyanAngJY
public class FacebookHandlerTest {
    public static final String ACCESS_TOKEN_STUB = "123";
    public static final String VALID_EMBEDDED_ACCESS_TOKEN = REDIRECT_DOMAIN
            + ACCESS_TOKEN_IDENTIFIER + ACCESS_TOKEN_STUB + "&";
    public static final String INVALID_EMBEDDED_ACCESS_TOKEN = REDIRECT_DOMAIN
            + ACCESS_TOKEN_STUB; // without token identifier
    public static final String VALID_URL = "http://www.google.com";

    private static final Recipe recipeStub = new RecipeBuilder().build();

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

    @Test
    public void postRecipeOnFacebook() {
        Assert.assertThrows(NullPointerException.class, () ->
                FacebookHandler.postRecipeOnFacebook(null));

        FacebookHandler.setAccessToken(ACCESS_TOKEN_STUB);
        if (hasInternetConnection()) {
            Assert.assertThrows(FacebookOAuthException.class, () ->
                    FacebookHandler.postRecipeOnFacebook(recipeStub));
        } else {
            Assert.assertThrows(FacebookNetworkException.class, () ->
                    FacebookHandler.postRecipeOnFacebook(recipeStub));
        }
    }

    /**
     * Returns true if Computer has Internet connection.
     */
    //https://sqa.stackexchange.com/questions/6036/how-to-check-my-pc-is-having-internet-connection-using-java
    private boolean hasInternetConnection() {
        try {
            URL url = new URL(VALID_URL);
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.getContent();
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
//@@author
