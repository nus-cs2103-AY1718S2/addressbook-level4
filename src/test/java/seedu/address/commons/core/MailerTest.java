package seedu.address.commons.core;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class MailerTest {

    @Test
    public void sendEmail() {
        boolean test = Mailer.email(Arrays.asList("pigeonscs2103@gmail.com"));
        assertTrue(test);
    }
}
