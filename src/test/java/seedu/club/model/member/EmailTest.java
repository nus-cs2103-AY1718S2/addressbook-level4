package seedu.club.model.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;

import org.junit.Test;

import seedu.club.testutil.Assert;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Email.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Email.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Email.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Email.isValidEmail("a@bc"));  // minimal
        assertTrue(Email.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(Email.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Email.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(Email.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com"));   // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com"));    // long local part
    }

    @Test
    public void test_hashCode() {
        Email email = new Email(VALID_EMAIL_AMY);
        String expectedEmail = VALID_EMAIL_AMY;
        assertEquals(expectedEmail.hashCode(), email.hashCode());
    }

    @Test
    public void test_equals() {
        Email testEmailOne = new Email(VALID_EMAIL_AMY);
        Email testEmailTwo = new Email(VALID_EMAIL_BOB);

        assertTrue(testEmailOne.equals(testEmailOne));
        assertFalse(testEmailOne.equals(testEmailTwo));

        assertFalse(testEmailOne.equals(null));
    }
}
