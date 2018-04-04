package seedu.organizer.storage;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.testutil.Assert;

//@@author dominickenn
public class XmlAdaptedUserTest {

    public static final String USERNAME = "Jennifer";
    public static final String PASSWORD = "Jennifer123";
    public static final String QUESTION = "Question";
    public static final String ANSWER = "Answer";

    public static final String OTHER_USERNAME = "bobby";

    @Test
    public void equal_defaultConstructor() {
        XmlAdaptedUser user = new XmlAdaptedUser(USERNAME, PASSWORD);
        XmlAdaptedUser otherUser = new XmlAdaptedUser(USERNAME, PASSWORD);
        assertEquals(user, otherUser);
        assertEquals(user, user);

        XmlAdaptedUser diffUser = new XmlAdaptedUser(OTHER_USERNAME, PASSWORD);
        assertNotEquals(user, diffUser);
    }

    @Test
    public void equal_userConstructor() {
        XmlAdaptedUser user = new XmlAdaptedUser(USERNAME, PASSWORD);
        XmlAdaptedUser otherUser = new XmlAdaptedUser(USERNAME, PASSWORD);
        assertEquals(user, otherUser);
        assertEquals(user, user);
    }

    @Test
    public void equal_userWithQuestionAnswerConstructor() {
        XmlAdaptedUser user = new XmlAdaptedUser(USERNAME, PASSWORD, QUESTION, ANSWER);
        XmlAdaptedUser otherUser = new XmlAdaptedUser(USERNAME, PASSWORD, QUESTION, ANSWER);
        assertEquals(user, otherUser);
        assertEquals(user, user);
    }

    @Test
    public void toModel_invalidUsername() {
        Assert.assertThrows(
                IllegalValueException.class, () -> new XmlAdaptedUser("", "validpassword").toUserModelType()
        );
    }

    @Test
    public void toModel_invalidPassword() {
        Assert.assertThrows(
                IllegalValueException.class, () -> new XmlAdaptedUser("validusername", "").toUserModelType()
        );
    }

    @Test
    public void toModel_invalidQuestion() {
        Assert.assertThrows(
                IllegalValueException.class, () -> new XmlAdaptedUser(
                        "validusername",
                        "validpassword",
                        "",
                        "valid answer").toUserQuestionAnswerModelType()
        );
    }

    @Test
    public void toModel_invalidAnswer() {
        Assert.assertThrows(
                IllegalValueException.class, () -> new XmlAdaptedUser(
                        "validusername",
                        "validpassword",
                        "validquestion",
                        "").toUserQuestionAnswerModelType()
        );
    }
}
