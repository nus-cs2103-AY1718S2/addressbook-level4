# chanyikwai
###### \java\seedu\address\email\EmailComposeTest.java
``` java
public class EmailComposeTest {

    private EmailCompose emailCompose;

    @Before
    public void setUp() {
        emailCompose = new EmailCompose();
    }

    @Test
    public void composeEmail() {
        MessageDraft validDraft = new MessageDraft();
        final String validMessage = "This is a message body.";
        final String validSubject = "This is a subject.";
        final String validRecipients = "Someone@gmail.com";
        final String validRelativeFilePath = "docs/images/Architecture.png";
        validDraft.setMessage(validMessage);
        validDraft.setSubject(validSubject);
        validDraft.setRecipients(validRecipients);
        validDraft.setRelativeFilePath(validRelativeFilePath);
        emailCompose.composeEmail(validDraft);
        assertEquals(validMessage, emailCompose.getMessage().getMessage());
        assertEquals(validSubject, emailCompose.getMessage().getSubject());
        assertEquals(validRecipients, emailCompose.getMessage().getRecipient());
        assertEquals(validRelativeFilePath, emailCompose.getMessage().getRelativeFilePath());
    }

    @Test
    public void clearEmailDraft() {
        MessageDraft validDraft = new MessageDraft();
        final String validMessage = "This is a message body.";
        final String validSubject = "This is a subject.";
        final String validRecipients = "Someone@gmail.com";
        final String validRelativeFilePath = "docs/images/Architecture.png";
        validDraft.setMessage(validMessage);
        validDraft.setSubject(validSubject);
        validDraft.setRecipients(validRecipients);
        validDraft.setRelativeFilePath(validRelativeFilePath);
        emailCompose.composeEmail(validDraft);
        emailCompose.resetData();
        assertEquals("", emailCompose.getMessage().getMessage());
        assertEquals("", emailCompose.getMessage().getSubject());
        assertEquals("", emailCompose.getMessage().getRecipient());
        assertEquals("", emailCompose.getMessage().getRelativeFilePath());
    }
}
```
###### \java\seedu\address\email\EmailFunctionTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EmailFunctionTest {

    private static final String EMAIL_VALID_FUNCTION = "send";
    private static final String EMAIL_INVALID_FUNCTION = "do nothing";

    private EmailFunction emailFunction;

    @Before
    public void setUp() {
        emailFunction = new EmailFunction();
    }

    @Test
    public void valid() {
        emailFunction.setEmailFunction(EMAIL_VALID_FUNCTION);
        assertTrue(emailFunction.isValid());
    }

    @Test
    public void notValid() {
        emailFunction.setEmailFunction(EMAIL_INVALID_FUNCTION);
        assertFalse(emailFunction.isValid());
    }
}
```
###### \java\seedu\address\email\EmailLoginTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.email.exceptions.EmailLoginInvalidException;

/**
 * Handles how user logs into email
 */
public class EmailLoginTest {

    private static final String EMAIL_VALID_LOGIN_ACCOUNT = "valid@gmail.com:123";
    private static final String EMAIL_INVALID_LOGIN_ACCOUNT = "invalid@yahoo.com:123";

    private EmailLogin emailLogin;

    @Before
    public void setUp() {
        emailLogin = new EmailLogin();
    }

    @Test
    public void validAccountLogin() throws EmailLoginInvalidException {
        String[] validEmailLogin = EMAIL_VALID_LOGIN_ACCOUNT.split(":");
        emailLogin.loginEmail(validEmailLogin);

        assertTrue(emailLogin.isUserLoggedIn());
    }

    @Test
    public void invalidAccountLogin() {
        try {
            String[] invalidEmailLogin = EMAIL_INVALID_LOGIN_ACCOUNT.split(":");
            emailLogin.loginEmail(invalidEmailLogin);
        } catch (EmailLoginInvalidException e) {
            assertFalse(emailLogin.isUserLoggedIn());
        }
    }

    @Test
    public void retrieveLoginEmail() throws EmailLoginInvalidException {
        String[] validEmailLogin = EMAIL_VALID_LOGIN_ACCOUNT.split(":");
        emailLogin.loginEmail(validEmailLogin);

        assertEquals(emailLogin.getEmailLogin(), validEmailLogin[0]);
    }

    @Test
    public void retrievePassword() throws EmailLoginInvalidException {
        String[] validEmailLogin = EMAIL_VALID_LOGIN_ACCOUNT.split(":");
        emailLogin.loginEmail(validEmailLogin);

        assertEquals(emailLogin.getPassword(), validEmailLogin[1]);
    }

    @Test
    public void logoutUserEmail() throws EmailLoginInvalidException {
        String[] validEmailLogin = EMAIL_VALID_LOGIN_ACCOUNT.split(":");
        emailLogin.loginEmail(validEmailLogin);

        emailLogin.resetData();
        assertFalse(emailLogin.isUserLoggedIn());
    }
}
```
###### \java\seedu\address\email\EmailManagerTest.java
``` java
public class EmailManagerTest {
    @Test
    public void equals() {
        EmailManager emailManager = new EmailManager();
        EmailManager emailManagerCopy = new EmailManager();

        // same values -> returns true
        assertTrue(emailManager.equals(emailManager));

        // null -> returns false
        assertFalse(emailManager.equals(null));

        // different types -> returns false
        assertFalse(emailManager.equals(5));

        // different user -> returns false
        try {
            String loginAccount = "example@gmail.com:123";
            String[] loginEmail = loginAccount.split(":");
            emailManagerCopy.loginEmailAccount(loginEmail);
            assertFalse(emailManager.equals(emailManagerCopy));
        } catch (EmailLoginInvalidException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void compose() {
        EmailManager emailManager = new EmailManager();
        MessageDraft messageDraft = new MessageDraft();
        String validMessage = "Message";
        String validSubject = "Subject";
        String validRecipients = "another@gmail.com";
        messageDraft.setMessage(validMessage);
        messageDraft.setSubject(validSubject);
        messageDraft.setRecipients(validRecipients);
        emailManager.composeEmail(messageDraft);

        assertTrue(emailManager.getEmailDraft().getMessage().equals(validMessage));
        assertTrue(emailManager.getEmailDraft().getSubject().equals(validSubject));
        assertTrue(emailManager.getEmailDraft().getRecipient().equals(validRecipients));
    }
}
```
###### \java\seedu\address\email\EmailSendTest.java
``` java
public class EmailSendTest {

    private EmailSend emailSend;

    @Before
    public void setUp() {
        emailSend = new EmailSend();
    }

    @Test
    public void sendEmail() throws AuthenticationFailedException {
        EmailCompose emailCompose = new EmailCompose();
        EmailLogin emailLogin = new EmailLogin();

        try {
            emailSend.sendEmail(emailCompose, emailLogin);
        } catch (EmailLoginInvalidException e) {
            assertFalse(emailLogin.isUserLoggedIn());
        } catch (EmailMessageEmptyException e) {
            assertTrue(emailCompose.getMessage().getMessage().isEmpty());
            assertTrue(emailCompose.getMessage().getSubject().isEmpty());

            MessageDraft messageDraft = new MessageDraft();
            messageDraft.setMessage("Message");
            emailCompose.composeEmail(messageDraft);
            assertFalse(emailCompose.getMessage().getMessage().isEmpty());
            assertTrue(emailCompose.getMessage().getSubject().isEmpty());
            emailCompose.resetData();

            messageDraft = new MessageDraft();
            messageDraft.setSubject("Subject");
            emailCompose.composeEmail(messageDraft);
            assertTrue(emailCompose.getMessage().getMessage().isEmpty());
            assertFalse(emailCompose.getMessage().getSubject().isEmpty());
            emailCompose.resetData();
        } catch (EmailRecipientsEmptyException e) {
            MessageDraft messageDraft = new MessageDraft();
            messageDraft.setMessage("Message");
            messageDraft.setSubject("Subject");
            emailCompose.composeEmail(messageDraft);
            assertFalse(emailCompose.getMessage().getMessage().isEmpty());
            assertFalse(emailCompose.getMessage().getSubject().isEmpty());
            assertTrue(emailCompose.getMessage().getRecipient().isEmpty());
            emailCompose.resetData();
        }
    }
}
```
###### \java\systemtests\EmailCommandSystemTest.java
``` java
public class EmailCommandSystemTest extends MoviePlannerSystemTest {

    @Test
    public void draftMessageWithoutSend() {
        Model model = getModel();
        GuiRobot guiRobot = new GuiRobot();
        guiRobot.push(KeyCode.SHIFT, KeyCode.TAB);
        guiRobot.pauseForHuman();

        /* ------------------------------------- Perform email operations --------------------------------------- */

        EmailManager toEdit = EMAIL_DRAFT_1;

        /* Case: edit the email draft with all fields (without sending) -> drafted */
        String command = "   " + EmailCommand.COMMAND_WORD + "  "
                + EMAIL_DESC_MESSAGE + "  " + EMAIL_DESC_SUBJECT + " "
                + EMAIL_DESC_RECIPIENT + "   " + EMAIL_DESC_LOGIN_ACCOUNT + " ";
        assertCommandSuccess(command, toEdit);
        guiRobot.pauseForHuman();

        /* Case: edit the email draft with all fields same as previous email draft except recipient -> drafted */
        EmailManager tempEmailManager = null;
        try {
            tempEmailManager = new EmailBuilder().withMessage(VALID_EMAIL_MESSAGE)
                    .withSubject(VALID_EMAIL_SUBJECT)
                    .withRecipient("anotherPerson@gmail.com")
                    .withLoginAccount(VALID_EMAIL_LOGIN_ACCOUNT)
                    .build();
        } catch (EmailLoginInvalidException e) {
            e.printStackTrace();
        }
        toEdit = tempEmailManager;
        command = EmailCommand.COMMAND_WORD + EMAIL_DESC_MESSAGE + EMAIL_DESC_SUBJECT
                + EMAIL_DESC_ANOTHER_RECIPIENT + EMAIL_DESC_LOGIN_ACCOUNT;
        assertCommandSuccess(command, toEdit);
        guiRobot.pauseForHuman();

        /* Case: edit the email draft with all fields same as previous email draft except message
         * -> drafted
         */
        toEdit = EMAIL_DRAFT_1;
        try {
            tempEmailManager = new EmailBuilder().withMessage("Hi Mr. Matthews, This is a new email.")
                    .withSubject(VALID_EMAIL_SUBJECT)
                    .withRecipient(VALID_EMAIL_RECIPIENT)
                    .withLoginAccount(VALID_EMAIL_LOGIN_ACCOUNT)
                    .build();
        } catch (EmailLoginInvalidException e) {
            e.printStackTrace();
        }
        toEdit = tempEmailManager;
        command = EmailCommand.COMMAND_WORD + EMAIL_DESC_ANOTHER_MESSAGE
                + EMAIL_DESC_SUBJECT
                + EMAIL_DESC_RECIPIENT + EMAIL_DESC_LOGIN_ACCOUNT;
        assertCommandSuccess(command, toEdit);
        guiRobot.pauseForHuman();

        /* Case: edit the email draft with all fields same as previous email draft except subject
         * -> drafted
         */
        toEdit = EMAIL_DRAFT_1;
        try {
            tempEmailManager = new EmailBuilder().withMessage(VALID_EMAIL_MESSAGE)
                    .withSubject("Scheduled Meeting")
                    .withRecipient(VALID_EMAIL_RECIPIENT)
                    .withLoginAccount(VALID_EMAIL_LOGIN_ACCOUNT)
                    .build();
        } catch (EmailLoginInvalidException e) {
            e.printStackTrace();
        }
        toEdit = tempEmailManager;
        command = EmailCommand.COMMAND_WORD + EMAIL_DESC_MESSAGE + EMAIL_DESC_ANOTHER_SUBJECT
                + EMAIL_DESC_RECIPIENT + EMAIL_DESC_LOGIN_ACCOUNT;
        assertCommandSuccess(command, toEdit);
        guiRobot.pauseForHuman();

        /* Case: clear email draft -> cleared */
        command = EmailCommand.COMMAND_WORD + " " + "func/clear";
        executeCommand(command);
        guiRobot.pauseForHuman();
    }

    /**
     * Executes the {@code EmailCommand} that adds {@code toEdit} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code EmailCommand} with the details of
     * {@code toEdit}.<br>
     * 4. {@code Model}, {@code MessageDraft} and {@code EmailMessagePanel} equal to the corresponding components in
     * the current model added with {@code toEdit}.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(EmailManager toAdd) {
        assertCommandSuccess(EmailUtil.getEmailCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(EmailManager)}. Executes {@code command}
     * instead.
     * @see EmailCommandSystemTest#assertCommandSuccess(EmailManager)
     */
    private void assertCommandSuccess(String command, EmailManager toEdit) {
        Model expectedModel = getModel();

        MessageDraft tempMessageDraft = new MessageDraft();
        tempMessageDraft.setMessage(toEdit.getEmailDraft().getMessage());
        tempMessageDraft.setSubject(toEdit.getEmailDraft().getSubject());
        tempMessageDraft.setRecipients(toEdit.getEmailDraft().getRecipient());

        String loginAccount = EMAIL_DESC_LOGIN_ACCOUNT.substring(5, 39);
        String[] tempLoginAccount = loginAccount.split(":");
        try {
            expectedModel.loginEmailAccount(tempLoginAccount);
        } catch (EmailLoginInvalidException e) {
            e.printStackTrace();
        }
        expectedModel.draftEmail(tempMessageDraft);
        expectedModel.getEmailManager();

        String expectedResultMessage = String.format(EmailCommand.MESSAGE_SUCCESS, expectedModel.getEmailStatus());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, EmailManager)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code MessageDraft} and {@code EmailMessagePanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see EmailCommandSystemTest#assertCommandSuccess(String, EmailManager)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        //assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code MessageDraft} and {@code CinemaListPanel} remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\KeyShortcutSystemTest.java
``` java
public class KeyShortcutSystemTest extends MoviePlannerSystemTest {

    protected final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void undo() {
        /* ------------------------ Perform undo operation on the shown unfiltered list ----------------------------- */

        /* Case: add to empty movie planner -> added */
        deleteAllCinemas();

        /* Case: add a cinema without tags to an empty movie planner -> added */
        assertAddCommandSuccess(SENGKANG);
        guiRobot.pauseForHuman();

        /* Case: undo adding Amy to the list -> Amy deleted */
        guiRobot.push(KeyCode.CONTROL, KeyCode.Z);
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        guiRobot.pauseForHuman();
        assertShortcutSuccess(expectedResultMessage);
    }

    @Test
    public void redo() {
        /* ------------------------ Perform redo operation on the shown unfiltered list ----------------------------- */

        /* Case: add to empty movie planner -> added */
        deleteAllCinemas();

        /* Case: add a cinema without tags to an empty movie planner -> added */
        assertAddCommandSuccess(SENGKANG);
        guiRobot.pauseForHuman();

        /* Case: undo adding Amy to the list -> Amy deleted */
        guiRobot.push(KeyCode.CONTROL, KeyCode.Z);
        guiRobot.pauseForHuman();

        /* Case: redo adding Amy to the list -> Amy added again */
        guiRobot.push(KeyCode.CONTROL, KeyCode.Y);
        String expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        guiRobot.pauseForHuman();
        assertShortcutSuccess(expectedResultMessage);
    }

    @Test
    public void clear() {
        /* ------------------------ Perform redo operation on the shown unfiltered list ----------------------------- */

        /* Case: add to empty movie planner -> added */
        deleteAllCinemas();

        /* Case: add a cinema without tags to an empty movie planner -> added */
        assertAddCommandSuccess(SENGKANG);
        guiRobot.pauseForHuman();

        /* Case: clear list through shortcut key -> list of contacts deleted */
        guiRobot.push(KeyCode.ALT, KeyCode.SHIFT, KeyCode.C);
        String expectedResultMessage = ClearCommand.MESSAGE_SUCCESS;
        guiRobot.pauseForHuman();
        assertShortcutSuccess(expectedResultMessage);
    }

    @Test
    public void list() {
        /* ------------------------ Perform redo operation on the shown unfiltered list ----------------------------- */

        /* Case: add to empty movie planner -> added */
        deleteAllCinemas();

        /* Case: add a cinema without tags to an empty movie planner -> added */
        assertAddCommandSuccess(SENGKANG);
        guiRobot.pauseForHuman();

        /* Case: clear list through shortcut key -> list of contacts deleted */
        guiRobot.push(KeyCode.CONTROL, KeyCode.L);
        String expectedResultMessage = ListCommand.MESSAGE_SUCCESS;
        guiRobot.pauseForHuman();
        assertShortcutSuccess(expectedResultMessage);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code CinemaListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertAddCommandSuccess(Cinema toAdd) {
        assertAddCommandSuccess(CinemaUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Cinema)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Cinema)
     */
    private void assertAddCommandSuccess(String command, Cinema toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addCinema(toAdd);
        } catch (DuplicateCinemaException dce) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertAddCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Cinema)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code CinemaListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Cinema)
     */
    private void assertAddCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes the {@code UndoCommand/RedoCommand} that undoes {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code UndoCommand/RedoCommand}. <br>
     * 4. {@code Model}, {@code Storage} and {@code CinemaListPanel} equal to the corresponding components in
     * the current model after executing {@code UndoCommand/RedoCommand}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertShortcutSuccess(String expectedResultMessage) {
        Model expectedModel = getModel();
        assertShortcutSuccess(expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertShortcutSuccess(String, Cinema)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code CinemaListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     */
    private void assertShortcutSuccess(Model expectedModel, String expectedResultMessage) {
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
}
```
