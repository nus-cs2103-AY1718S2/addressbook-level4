//@@author IzHoBX
package seedu.address.storage;

import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

public class NotificationTimeParserUtilTest {
    private static final String NO_INTEGER_INVALID = "assvvbdgdtdyhdhdhbdfhdfhdhfhdghfhghfhjfjfjjjfjfhdgdgsdfsfsfsgghd";
    private static final String NULL_STRING_INVALID = "";
    private static final String SHORTER_THAN_SECOND_END_INDEX_INVALID = "20180419T1220";
    private static final String ACCEPTABLE_FORMAT_VALID = "2018-04-19T12:20:00";
    private static final String ACCEPTABLE_FORMAT_WITHOUT_SEPARATION_INVALID = "20180419122000";
    private static final String DEFAULT_FORMAT_VALID =
            "{\"dateTime\":\"2018-12-20T13:35:00.000+08:00timeZone:\"Asia/Singapore\"}";
    private static final String DEFAULT_FORMAT_WITHOUT_QUOTE_VALID =
            "{dateTime:2018-12-20T13:35:00.000+08:00timeZone:Asia/Singapore}";

    @Test
    public void parseTimeTest_invalidInput_fail() {
        assertError(NO_INTEGER_INVALID);
        assertError(NULL_STRING_INVALID);
        assertError(SHORTER_THAN_SECOND_END_INDEX_INVALID);
        assertError(ACCEPTABLE_FORMAT_WITHOUT_SEPARATION_INVALID);
    }

    @Test
    public void parseTimeTest_validInput_success() {
        assertSuccess(ACCEPTABLE_FORMAT_VALID);
        assertSuccess(DEFAULT_FORMAT_VALID);
        assertSuccess(DEFAULT_FORMAT_WITHOUT_QUOTE_VALID);
    }

    /**
     * Passes if there is error thrown, vice versa.
     */
    private void assertError(String input) {
        try {
            NotificationTimeParserUtil.parseTime(input);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assert(false);
    }

    /**
     * Passes if there is no error thrown, vice versa.
     */
    private void assertSuccess(String input) {
        try {
            NotificationTimeParserUtil.parseTime(input);
        } catch (AssertionError e) {
            assertTrue(false);
            return;
        }
        assertTrue(true);
    }
}
