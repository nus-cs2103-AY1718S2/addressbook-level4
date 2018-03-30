//@@author nicholasangcx
package seedu.recipe.ui.testutil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.ui.util.CloudStorageUtil.ACCESS_TOKEN_IDENTIFIER;

import org.junit.Test;

import seedu.recipe.ui.util.CloudStorageUtil;

public class CloudStorageUtilTest {

    private static final String ACCESS_TOKEN_STUB = "adjhsj";
    private static final String VALID_EMBEDDED_ACCESS_TOKEN = CloudStorageUtil.getRedirectDomain()
            + ACCESS_TOKEN_IDENTIFIER + ACCESS_TOKEN_STUB + "&token_type";
    private static final String INVALID_EMBEDDED_ACCESS_TOKEN = CloudStorageUtil.getRedirectDomain();

    @Test
    public void hasAccessToken() {
        CloudStorageUtil.setAccessToken(null);
        assertFalse(CloudStorageUtil.hasAccessToken());

        CloudStorageUtil.setAccessToken(ACCESS_TOKEN_STUB);
        assertTrue(CloudStorageUtil.hasAccessToken());
    }

    @Test
    public void extractAccessToken() {
        assertTrue(CloudStorageUtil.extractAccessToken(VALID_EMBEDDED_ACCESS_TOKEN)
                .equals(ACCESS_TOKEN_STUB));
    }

    @Test
    public void checkAndSetAccessToken() {
        assertTrue(CloudStorageUtil.checkAndSetAccessToken(VALID_EMBEDDED_ACCESS_TOKEN));
        assertFalse(CloudStorageUtil.checkAndSetAccessToken(INVALID_EMBEDDED_ACCESS_TOKEN));
    }
}
