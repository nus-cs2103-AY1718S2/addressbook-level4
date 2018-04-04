//@@author nicholasangcx
package seedu.recipe.ui.testutil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.ui.util.CloudStorageUtil;

public class CloudStorageUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String ACCESS_TOKEN_STUB = "adjhsj";
    private static final String WRONG_AUTHORIZATION_CODE = "abcdefg";

    @Test
    public void hasAccessToken() {
        CloudStorageUtil.setAccessToken(null);
        assertFalse(CloudStorageUtil.hasAccessToken());

        CloudStorageUtil.setAccessToken(ACCESS_TOKEN_STUB);
        assertTrue(CloudStorageUtil.hasAccessToken());
    }

    @Test
    public void processInvalidAuthorizationCode() {
        thrown.expect(AssertionError.class);
        CloudStorageUtil.processAuthorizationCode(WRONG_AUTHORIZATION_CODE);
    }
}
//@@author
