// @@author kush1509
package systemtests.job;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.job.JobMatchCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.model.Model;
import systemtests.AddressBookSystemTest;
import systemtests.ModelHelper;

public class JobMatchCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void match() {
        /* Case: match the first job in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        Model expectedModel = getModel();
        String command = "   " + JobMatchCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + "   ";
        ModelHelper.setFilteredPersonList(expectedModel, FIONA, GEORGE); //first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous match command where person list is displaying the persons we are matching
         * -> 2 persons found
         */
        command = "   " + JobMatchCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + "   ";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: match job where person list is not displaying the person we are matching -> 1 person found */
        command = "   " + JobMatchCommand.COMMAND_WORD + " " + INDEX_SECOND.getOneBased() + "   ";
        ModelHelper.setFilteredPersonList(expectedModel, CARL, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: match same job in address book after deleting 1 person -> 2 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(CARL));
        command = JobMatchCommand.COMMAND_WORD + " 2";
        expectedModel = getModel();
        ModelHelper.setFilteredPersonList(expectedModel, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);

        /* ----------------------------------- Perform invalid matchJob operations --------------------------------- */

        /* Case: invalid index (0) -> rejected */
        int invalidIndex = -1;
        assertCommandFailure(JobMatchCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobMatchCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(JobMatchCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobMatchCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredJobList().size() + 1;
        assertCommandFailure(JobMatchCommand.COMMAND_WORD + " "
                + invalidIndex, MESSAGE_INVALID_JOB_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(JobMatchCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobMatchCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(JobMatchCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobMatchCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("matCHjOb 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: match from empty address book -> rejected */
        deleteAllJobs();
        assertCommandFailure(JobMatchCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
