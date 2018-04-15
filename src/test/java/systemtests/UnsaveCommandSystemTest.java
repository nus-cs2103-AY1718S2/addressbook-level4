//@@author wyinkok
package systemtests;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX;
import static seedu.address.logic.commands.UnsaveCommand.MESSAGE_UNSAVED_INTERNSHIP_SUCCESS;
import static seedu.address.testutil.TestUtil.getInternship;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getSecondLastIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalInternshipsWithSavedTag.KEYWORD_MATCHING_SAFETY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnsaveCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.JobbiBot;
import seedu.address.model.Model;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.testutil.TypicalInternshipsWithSavedTag;
import seedu.address.testutil.UnsavedInternshipBuilder;

public class UnsaveCommandSystemTest extends JobbiBotSystemTest {

    private static final String MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnsaveCommand.MESSAGE_USAGE);

    /**
     * Returns the data with saved tags to be loaded into the file in {@link #getDataFileLocation()}.
     */
    @Override
    protected JobbiBot getInitialData() {
        return TypicalInternshipsWithSavedTag.getTypicalInternshipBookWithSavedTag();
    }

    @Test
    public void unsave() throws CommandException {


        /* ----------------- Performing save operation while an unfiltered list is being shown -------------------- */

        /* Case: remove the saved first internship in the list,
            command with leading spaces and trailing spaces -> saved */
        Model model = getModel();
        Index firstindex = INDEX_FIRST_INTERNSHIP;
        String command = "     " + UnsaveCommand.COMMAND_WORD + "      " + firstindex.getOneBased() + "       ";
        Internship editedInternship = removeSavedTagToInternship(model, firstindex);
        assertCommandSuccess(command, firstindex, editedInternship);


        /* Case: save the last internship in the list -> saved */
        Model modelBeforeSavingLast = getModel();
        Index lastInternshipIndex = getLastIndex(modelBeforeSavingLast);
        assertCommandSuccess(lastInternshipIndex);

        /* Case: undo saving the last internship in the list -> last internship restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeSavingLast, expectedResultMessage);

        /* Case: redo saving the last internship in the list -> last internship saved again */
        command = RedoCommand.COMMAND_WORD;
        removeSavedTagToInternship(modelBeforeSavingLast, lastInternshipIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeSavingLast, expectedResultMessage);

        /* Case: unsave the middle internship in the list -> unsaved */
        Index middleInternshipIndex = getMidIndex(getModel());
        assertCommandSuccess(middleInternshipIndex);

        /* ------------------ Performing save operation while a filtered list is being shown ---------------------- */

        /* Case: filtered internship list, unsave index within bounds of internship book and internship list
         * -> unsave */
        showInternshipsWithName(KEYWORD_MATCHING_SAFETY);
        Index index = INDEX_FIRST_INTERNSHIP;
        assertTrue(index.getZeroBased() < getModel().getFilteredInternshipList().size());
        command = UnsaveCommand.COMMAND_WORD + " " + index.getOneBased();
        Internship personWithoutSavedTag = new UnsavedInternshipBuilder()
                                                .removeTag(getModel().getFilteredInternshipList()
                                                        .get(index.getZeroBased()));
        assertCommandSuccess(command, index, personWithoutSavedTag);


        /* Case: filtered internship list,
         * unsave index within bounds of internship book but out of bounds of internship list -> rejected
         */
        showInternshipsWithName(KEYWORD_MATCHING_SAFETY);
        int invalidIndex = getModel().getJobbiBot().getInternshipList().size();
        command = UnsaveCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        /* --------------------- Performing unsave operation while a internship card is selected ------------------- */

        /* Case: unsave the selected internship
                      -> internship list panel selects the internship before the unsaved internship */
        showAllInternships();
        Model expectedModel = getModel();
        Index selectedIndex = getSecondLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased());
        selectInternship(selectedIndex);
        command = UnsaveCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        Internship nextEditedInternship = removeSavedTagToInternship(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_UNSAVED_INTERNSHIP_SUCCESS, nextEditedInternship);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid unsave operation ---------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = UnsaveCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = UnsaveCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT);

        /* Case: invalid nonalphanumerical index (!) -> rejected */
        command = UnsaveCommand.COMMAND_WORD + " !";
        assertCommandFailure(command, MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getJobbiBot().getInternshipList().size() + 1);
        command = UnsaveCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(UnsaveCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(UnsaveCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT);
    }

    /**
     * Removes the "saved" tag from the {@code Internship} at the specified {@code index}
     * in {@code model}'s internship book.
     * @return the internship without a "saved" tag
     */
    private Internship removeSavedTagToInternship(Model model, Index index) throws CommandException {
        Internship targetInternship = getInternship(model, index);
        Internship unsavedInternship = new UnsavedInternshipBuilder().removeTag(targetInternship);
        try {
            model.updateInternship(targetInternship, unsavedInternship);
        } catch (InternshipNotFoundException pnfe) {
            throw new AssertionError("targetInternship is retrieved from model.");
        } catch (DuplicateInternshipException e) {
            throw new AssertionError("unsavedInternship is a duplicate in expectedModel.");
        }
        return unsavedInternship;
    }

    /**
     * Removes the saved internship from Saved Collection at {@code toUnsave} by creating
     * a default {@code UnsaveCommand} using {@code toUnsave} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see UnsaveCommandSystemTest#assertCommandSuccess(String, Index, Internship)
     */
    private void assertCommandSuccess(Index toUnsave) throws CommandException {
        Model expectedModel = getModel();
        Internship unsavedInternship = removeSavedTagToInternship(expectedModel, toUnsave);
        String expectedResultMessage = String.format(MESSAGE_UNSAVED_INTERNSHIP_SUCCESS, unsavedInternship);

        assertCommandSuccess(
                UnsaveCommand.COMMAND_WORD + " " + toUnsave.getOneBased(), expectedModel,
                expectedResultMessage);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see UnsaveCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toUnsave, Internship editedInternship) {
        assertCommandSuccess(command, toUnsave, editedInternship, null);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that chat bot panel displays the success message of executing {@code UnsaveCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the internship at index {@code toUnsave}
     * being updated to values specified {@code unsavedInternship}.<br>
     * @param toUnsave the index of the current model's filtered list.
     * @see UnsaveCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toUnsave, Internship unsavedInternship,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateInternship(
                    expectedModel.getFilteredInternshipList().get(toUnsave.getZeroBased()), unsavedInternship);
        } catch (DuplicateInternshipException | InternshipNotFoundException e) {
            throw new IllegalArgumentException(
                    "unsavedInternship is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(UnsaveCommand.MESSAGE_UNSAVED_INTERNSHIP_SUCCESS, unsavedInternship),
                    expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the chat bot panel displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see JobbiBotSystemTest#assertSelectedCardChanged(Index)
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
     * 2. Asserts that chat bot panel displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
