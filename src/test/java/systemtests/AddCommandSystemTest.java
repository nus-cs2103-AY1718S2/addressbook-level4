package systemtests;

//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.address.testutil.TypicalActivities.ASSIGNMENT1;
//import static seedu.address.testutil.TypicalActivities.ASSIGNMENT3;
//import static seedu.address.testutil.TypicalActivities.DEMO1;
//import static seedu.address.testutil.TypicalActivities.KEYWORD_MATCHING_MEIER;
//import static seedu.address.testutil.TypicalActivities.QUIZ;

//import org.junit.Test;

//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.TaskCommand;
//import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
//import seedu.address.model.activity.DateTime;
//import seedu.address.model.activity.Name;
//import seedu.address.model.activity.Remark;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
//import seedu.address.model.tag.Tag;
import seedu.address.testutil.ActivityUtil;
//import seedu.address.testutil.TaskBuilder;

public class AddCommandSystemTest extends RemarkBookSystemTest {

    //TODO: TEST
    /**
     * Test
     */
//    public void add() throws Exception {
//        Model model = getModel();
//
//        /* ------------------------ Perform add operations on the shown unfiltered list
//        ----------------------------- */
//
//        /* Case: add a activity without tags to a non-empty address book,
//         * command with leading spaces and trailing spaces
//         * -> added
//         */
//        Activity toAdd = AMY;
//        String command = "   " + TaskCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
//                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: undo adding Amy to the list -> Amy deleted */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: redo adding Amy to the list -> Amy added again */
//        command = RedoCommand.COMMAND_WORD;
//        model.addActivity(toAdd);
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: add a activity with all fields same as another activity in the address book except name -> added */
//        toAdd = new TaskBuilder().withName(VALID_NAME_BOB).withDateTime(VALID_PHONE_AMY)
//                .withRemark(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
//                + TAG_DESC_FRIEND;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a activity with all fields same as another activity in the address book except phone -> added */
//        toAdd = new TaskBuilder().withName(VALID_NAME_AMY).withDateTime(VALID_PHONE_BOB)
//                .withRemark(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY
//                + ADDRESS_DESC_AMY
//                + TAG_DESC_FRIEND;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a activity with all fields same as another activity in the address book except email -> added */
//        toAdd = new TaskBuilder().withName(VALID_NAME_AMY).withDateTime(VALID_PHONE_AMY)
//                .withRemark(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB
//                + ADDRESS_DESC_AMY
//                + TAG_DESC_FRIEND;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a activity with all fields same as another activity in the address book except address
//        -> added */
//        toAdd = new TaskBuilder().withName(VALID_NAME_AMY).withDateTime(VALID_PHONE_AMY)
//                .withRemark(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
//                + TAG_DESC_FRIEND;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add to empty address book -> added */
//        deleteAllPersons();
//        assertCommandSuccess(ASSIGNMENT1);
//
//        /* Case: add a activity with tags, command with parameters in random order -> added */
//        toAdd = BOB;
//        command = TaskCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB
//                + NAME_DESC_BOB
//                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
//        assertCommandSuccess(command, toAdd);
//
//        /* Case: add a activity, missing tags -> added */
//        assertCommandSuccess(ASSIGNMENT3);
//
//        /* -------------------------- Perform add operation on the shown filtered list
//        ------------------------------ */
//
//        /* Case: filters the activity list before adding -> added */
//        showPersonsWithName(KEYWORD_MATCHING_MEIER);
//        assertCommandSuccess(DEMO1);
//
//        /* ------------------------ Perform add operation while a activity card is selected
//        ------------------------ */
//
//        /* Case: selects first card in the activity list, add a activity -> added, card selection remains unchanged */
//        selectPerson(Index.fromOneBased(1));
//        assertCommandSuccess(QUIZ);
//
//        /* ----------------------------------- Perform invalid add operations
//        --------------------------------------- */
//
//        /* Case: add a duplicate activity -> rejected */
//        //command = ActivityUtil.getTaskCommand((ASSIGNMENT3);
//        //assertCommandFailure(command, TaskCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: add a duplicate activity except with different tags -> rejected */
//        // "friends" is an existing tag used in the default model, see TypicalActivities#ASSIGNMENT1
//        // This test will fail if a new tag that is not in the model is used, see the bug documented in
//        // DeskBoard#addActivity(Activity)
//        //command = ActivityUtil.getTaskCommand(ASSIGNMENT3) + " " + PREFIX_TAG.getPrefix() + "friends";
//        //assertCommandFailure(command, TaskCommand.MESSAGE_DUPLICATE_PERSON);
//
//        /* Case: missing name -> rejected */
//        command = TaskCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCommand.MESSAGE_USAGE));
//
//        /* Case: missing phone -> rejected */
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCommand.MESSAGE_USAGE));
//
//        /* Case: missing email -> rejected */
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCommand.MESSAGE_USAGE));
//
//        /* Case: missing address -> rejected */
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;
//        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCommand.MESSAGE_USAGE));
//
//        /* Case: invalid keyword -> rejected */
//        command = "adds " + ActivityUtil.getActivityDetails(toAdd);
//        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
//
//        /* Case: invalid name -> rejected */
//        command = TaskCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY
//                + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);
//
//        /* Case: invalid phone -> rejected */
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY
//                + ADDRESS_DESC_AMY;
//        assertCommandFailure(command, DateTime.MESSAGE_DATETIME_CONSTRAINTS);
//
//        /* Case: invalid address -> rejected */
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
//                + INVALID_ADDRESS_DESC;
//        assertCommandFailure(command, Remark.MESSAGE_REMARK_CONSTRAINTS);
//
//        /* Case: invalid tag -> rejected */
//        command = TaskCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
//                + INVALID_TAG_DESC;
//        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
//    }

    /**
     * Executes the {@code TaskCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code TaskCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code ActivityListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RemarkBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RemarkBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Activity toAdd) {
        assertCommandSuccess(ActivityUtil.getTaskCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Activity)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Activity)
     */
    private void assertCommandSuccess(String command, Activity toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addActivity(toAdd);
        } catch (DuplicateActivityException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(TaskCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Activity)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code ActivityListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Activity)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code ActivityListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RemarkBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RemarkBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
