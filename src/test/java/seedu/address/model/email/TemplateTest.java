package seedu.address.model.email;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author ng95junwei

public class TemplateTest {
    private final String purpose = "purpose";
    private final String title = "title";
    private final String message = "message";
    private final Template template = new Template(purpose, title, message);

    @Test
    public void isEqual_sameTemplate_success() {
        assertTrue(new Template(purpose, title, message).equals(template));
    }

    @Test
    public void isEqual_compareNull_failure() {
        assertFalse(new Template(purpose, title, message).equals(null));
    }

    @Test
    public void getters_validAppointment_success() {
        assertTrue(template.getPurpose().equals(purpose));
        assertTrue(template.getTitle().equals(title));
        assertTrue(template.getMessage().equals(message));
    }
    @Test
    public void toString_validTemplate_success() {
        assertTrue(template.toString().equals("Purpose: " + purpose + ", Subject: " + title + ", Message: "
                + message));
    }

}

//@@author
