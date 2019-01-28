package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.petpatient.exceptions.PetDependencyNotEmptyException;

public class DeleteCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT_OWNER =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_OWNER);
    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT_PET_PATIENT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_PET_PATIENT);
    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_OWNER =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_FORCE_OWNER);
    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_PET_PATIENT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_FORCE_PET_PATIENT);
    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT_APPOINTMENT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_APPOINTMENT);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first person in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "   -o   "
                + INDEX_FIRST_PERSON.getOneBased() + "       ";
        Person deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last person in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePerson(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle person in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, delete index within bounds of address book and person list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered person list, delete index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = DeleteCommand.COMMAND_WORD + " -o " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a person card is selected ------------------------ */

        /* Case: delete the selected person -> person list panel selects the person before the deleted person */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " -o " + selectedIndex.getOneBased();
        deletedPerson = removePerson(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);
    }

    //@@author wynonaK
    @Test
    public void deleteFormatTest() {
        /* ----------------------- Performing invalid delete operation for owner ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        String command = DeleteCommand.COMMAND_WORD + " -o 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_OWNER);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -o -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_OWNER);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " -o " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -o abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_OWNER);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -o 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_OWNER);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE -o 1", MESSAGE_UNKNOWN_COMMAND);

        /* ------------------ Performing invalid delete operation for appointment ----------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -a 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_APPOINTMENT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -a -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_APPOINTMENT);

        /* Case: invalid index (size + 1) -> rejected */
        outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getAppointmentList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " -a " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -a abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_APPOINTMENT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -a 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_APPOINTMENT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE -a 1", MESSAGE_UNKNOWN_COMMAND);

        /* ------------------ Performing invalid delete operation for pet patient ----------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -p 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_PET_PATIENT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -p -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_PET_PATIENT);

        /* Case: invalid index (size + 1) -> rejected */
        outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPetPatientList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " -p " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -p abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_PET_PATIENT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -p 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_PET_PATIENT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE -p 1", MESSAGE_UNKNOWN_COMMAND);

        /* ----------------------- Performing invalid delete operation for force owner ------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -fo 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_OWNER);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -fo -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_OWNER);

        /* Case: invalid index (size + 1) -> rejected */
        outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " -fo " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -fo abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_OWNER);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -fo 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_OWNER);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE -fo 1", MESSAGE_UNKNOWN_COMMAND);

        /* ----------------------- Performing invalid delete operation for force pet patient ------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -fp 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_PET_PATIENT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -fp -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_PET_PATIENT);

        /* Case: invalid index (size + 1) -> rejected */
        outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " -fp " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -fp abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_PET_PATIENT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -fp 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT_FORCE_PET_PATIENT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE -fp 1", MESSAGE_UNKNOWN_COMMAND);

        /* ------------------------- Performing invalid delete operation with wrong type ---------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -sha 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -fza -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " -fup " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -fsp abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -nafp 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
    }

    //@@author
    /**
     * Removes the {@code Person} at the specified {@code index} in {@code model}'s address book.
     * @return the removed person
     */
    private Person removePerson(Model model, Index index) {
        Person targetPerson = getPerson(model, index);
        try {
            model.deletePerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (PetDependencyNotEmptyException e) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        return targetPerson;
    }

    /**
     * Deletes the person at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Person deletedPerson = removePerson(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " -o " + toDelete.getOneBased(),
                expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

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
