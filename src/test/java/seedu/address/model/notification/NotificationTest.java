//@@author IzHoBX
package seedu.address.model.notification;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Person;

public class NotificationTest {
    private static final String SAMPLE_STRING = "123";

    @Test
    public void notification_nullTitle_fail() {
        try {
            Notification n = new Notification(null, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_idNull_fail() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, null, SAMPLE_STRING, SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_endDateNull_fail() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, null, SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_ownerIdNull_fail() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, null);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_ownerIdUninitialised_fail() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING,
                    Person.UNINITIALISED_ID + "");
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_nonEssentialParameterNull_success() {
        try {
            Notification n = new Notification(SAMPLE_STRING, null, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(false);
            return;
        }
        assertTrue(true);
    }

    @Test
    public void notification_allParameterNonNull_success() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING,
                    SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(false);
            return;
        }
        assertTrue(true);
    }
}
