package seedu.address.model.person;

import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Set;

import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

//@@author kaisertanqr
public class PersonTest {

    private static final Name VALID_NAME = BENSON.getName();
    private static final Phone VALID_PHONE = BENSON.getPhone();
    private static final Email VALID_EMAIL = BENSON.getEmail();
    private static final Address VALID_ADDRESS = BENSON.getAddress();
    private static final SessionLogs VALID_SESSION_LOG = BENSON.getSessionLogs();
    private static final Set<Tag> VALID_TAGS = BENSON.getTags();

    @Test
    public void constructor_null_throwsNullPointerException() {
        // ================== normal constructor ======================

        // null name
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS));

        // null phone
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS));

        // null email
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS));

        // null address
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS));

        // null tags
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null));

        // ================== overloaded constructor ======================

        // null name
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_SESSION_LOG));

        // null phone
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_SESSION_LOG));

        // null email
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS, VALID_SESSION_LOG));

        // null address
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS, VALID_SESSION_LOG));

        // null tags
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_SESSION_LOG));

        // null session log
        Assert.assertThrows(NullPointerException.class, () -> new
                Person(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, null));

    }
}
