package seedu.address.commons.core;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.DelivDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class MailerTest {

    @Test
    public void sendEmail() {
        boolean test = Mailer.email(Arrays.asList(new Person(
                new Name("John"),
                new Phone("98765432"),
                new Email("pigeonscs2103@gmail.com"),
                new Address("NUS"),
                new DelivDate("2018-03-24"),
                Collections.emptySet())));
        assertTrue(test);
    }
}
