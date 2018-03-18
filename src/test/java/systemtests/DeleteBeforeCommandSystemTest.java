package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_NOT_FOUND;
import static seedu.address.logic.commands.DeleteBeforeCommand.MESSAGE_DELETE_PERSONS_SUCCESS;
import static seedu.address.testutil.TypicalDates.DATE_FIRST_JAN;
import static seedu.address.testutil.TypicalDates.INVALID_DATE_DESC;
import static seedu.address.testutil.TypicalDates.VALID_DATE_DESC;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalTags.INVALID_TAG_DESC;
import static seedu.address.testutil.TypicalTags.TAG_SET_FRIEND;
import static seedu.address.testutil.TypicalTags.VALID_TAG_DESC_FRIEND;
import static seedu.address.testutil.TypicalTags.VALID_TAG_DESC_OWES_MONEY;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DeleteBeforeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

public class DeleteBeforeCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_BEFORE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteBeforeCommand.MESSAGE_USAGE);

    @Test
    public void deleteBefore() {
        /* --------------------------------- Performing delete before operation  ------------------------------------ */

        /* Case: delete persons in the list, command with leading spaces and trailing spaces -> deleted */
        Model modelBeforeDeleting = getModel();
        Model modelAfterDeleting = getModel();
        String command = "     " + DeleteBeforeCommand.COMMAND_WORD + "      " + VALID_DATE_DESC
                + "       " + VALID_TAG_DESC_FRIEND;
        List<Person> deleteTargets = Arrays.asList(ALICE);
        removePersons(modelAfterDeleting, deleteTargets);
        String expectedResultMessage = String.format(
                MESSAGE_DELETE_PERSONS_SUCCESS, deleteTargets.size(), TAG_SET_FRIEND, DATE_FIRST_JAN);
        assertCommandSuccess(command, modelAfterDeleting, expectedResultMessage);

        /* Case: undo deleting the persons in the list -> deleted persons restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeleting, expectedResultMessage);

        /* Case: redo deleting the persons in the list -> restored persons deleted again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelAfterDeleting, expectedResultMessage);


        /* ------------------------------ Performing invalid delete before operation -------------------------------- */

        /* Case: invalid date -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + INVALID_DATE_DESC + VALID_TAG_DESC_OWES_MONEY;
        assertCommandFailure(command, DateAdded.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + VALID_DATE_DESC + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: missing date -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + VALID_TAG_DESC_OWES_MONEY;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BEFORE_COMMAND_FORMAT);

        /* Case: missing tag -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + VALID_DATE_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BEFORE_COMMAND_FORMAT);

        /* Case: no such person exists -> rejected */
        command = DeleteBeforeCommand.COMMAND_WORD + VALID_DATE_DESC + VALID_TAG_DESC_OWES_MONEY;
        assertCommandFailure(command, MESSAGE_PERSONS_NOT_FOUND);
    }

    /**
     * Removes all {@code Person}s in {@code model}'s address book specified by the {@code targets} list.
     */
    private void removePersons(Model model, List<Person> targets) {
        try {
            model.deletePersons(targets);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
