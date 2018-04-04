package seedu.organizer.model.user;

import org.junit.Test;

import seedu.organizer.testutil.Assert;

//@@author dominickenn
public class UserWithQuestionAnswerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UserWithQuestionAnswer(null, null));
        Assert.assertThrows(NullPointerException.class, () -> new UserWithQuestionAnswer(null, null, null, null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        String validPassword = "validPass";
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new UserWithQuestionAnswer(invalidUsername, validPassword));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String validUsername = "validUsername";
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new UserWithQuestionAnswer(validUsername, invalidPassword));
    }

    @Test
    public void constructor_invalidQuestion_throwsIllegalArgumentException() {
        String validUsername = "validUsername";
        String validPassword = "validPassword";
        String invalidQuestion = "";
        String validAnswer = "valid answer";
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new UserWithQuestionAnswer(validUsername, validPassword, invalidQuestion, validAnswer));
    }

    @Test
    public void constructor_invalidAnswer_throwsIllegalArgumentException() {
        String validUsername = "validUsername";
        String validPassword = "validPassword";
        String validQuestion = "valid question";
        String invalidAnswer = "";
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new UserWithQuestionAnswer(validUsername, validPassword, validQuestion, invalidAnswer));
    }

    @Test
    public void isValidUsername() {
        Assert.assertThrows(NullPointerException.class, () ->
                UserWithQuestionAnswer.isValidUsername(null));
    }

    @Test
    public void isValidPassword() {
        Assert.assertThrows(NullPointerException.class, () -> UserWithQuestionAnswer.isValidPassword(null));
    }

    @Test
    public void isValidQuestion() {
        Assert.assertThrows(NullPointerException.class, () -> UserWithQuestionAnswer.isValidQuestion(null));
    }

    @Test
    public void isValidAnswer() {
        Assert.assertThrows(NullPointerException.class, () -> UserWithQuestionAnswer.isValidAnswer(null));
    }

}

