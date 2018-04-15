package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_CINEMAS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalCinemas.BEDOK;
import static seedu.address.testutil.TypicalCinemas.CLEMENTI;
import static seedu.address.testutil.TypicalCinemas.DOVER;
import static seedu.address.testutil.TypicalCinemas.KEYWORD_MATCHING_SHAWS;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindCommandSystemTest extends MoviePlannerSystemTest {

    @Test
    public void find() {
        /* Case: find multiple cinemas in movie planner, command with leading spaces and trailing spaces
         * -> 2 cinemas found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SHAWS + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BEDOK, DOVER); // Bedok and Dover have Shaws
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where cinema list is displaying the cinemas we are finding
         * -> 2 cinemas found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SHAWS;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find cinema where cinema list is not displaying the cinema we are finding -> 1 cinema found */
        command = FindCommand.COMMAND_WORD + " Clementi";
        ModelHelper.setFilteredList(expectedModel, CLEMENTI);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple cinemas in movie planner, 2 keywords -> 2 cinemas found */
        command = FindCommand.COMMAND_WORD + " Bedok Dover";
        ModelHelper.setFilteredList(expectedModel, BEDOK, DOVER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple cinemas in movie planner, 2 keywords in reversed order -> 2 cinemas found */
        command = FindCommand.COMMAND_WORD + " Dover Bedok";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple cinemas in movie planner, 2 keywords with 1 repeat -> 2 cinemas found */
        command = FindCommand.COMMAND_WORD + " Dover Bedok Dover";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple cinemas in movie planner, 2 matching keywords and 1 non-matching keyword
         * -> 2 cinemas found
         */
        command = FindCommand.COMMAND_WORD + " Dover Bedok NonMatchingKeyWord";
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

        /* Case: find same cinemas in movie planner after deleting 1 of them -> 1 cinema found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getMoviePlanner().getCinemaList().contains(BEDOK));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SHAWS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DOVER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find cinema in movie planner, keyword is same as name but of different case -> 1 cinema found */
        command = FindCommand.COMMAND_WORD + " ShAws";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find cinema in movie planner, keyword is substring of name -> 0 cinemas found */
        command = FindCommand.COMMAND_WORD + " sha";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find cinema in movie planner, name is substring of keyword -> 0 cinemas found */
        command = FindCommand.COMMAND_WORD + " shawss";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find cinema not in movie planner -> 0 cinemas found */
        command = FindCommand.COMMAND_WORD + " Yishun";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of cinema in movie planner -> 0 cinemas found */
        command = FindCommand.COMMAND_WORD + " " + DOVER.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of cinema in movie planner -> 0 cinemas found */
        command = FindCommand.COMMAND_WORD + " " + DOVER.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of cinema in movie planner -> 0 cinemas found */
        command = FindCommand.COMMAND_WORD + " " + DOVER.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a cinema is selected -> selected card deselected */
        showAllCinemas();
        selectCinema(Index.fromOneBased(1));
        assertFalse(getCinemaListPanel().getHandleToSelectedCard().getName().equals(DOVER.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Dover";
        ModelHelper.setFilteredList(expectedModel, DOVER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find cinema in empty movie planner -> 0 cinemas found */
        deleteAllCinemas();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SHAWS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DOVER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Dover";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_CINEMAS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_CINEMAS_LISTED_OVERVIEW, expectedModel.getFilteredCinemaList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
