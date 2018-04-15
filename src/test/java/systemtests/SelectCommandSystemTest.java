package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Test;

import guitests.GuiRobot;
import javafx.scene.input.KeyCode;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PopulatePrefixesRequestEvent;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class SelectCommandSystemTest extends AddressBookSystemTest {
    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void select() {
        /* ------------------------ Perform select operations on the shown unfiltered list -------------------------- */

        /* Case: select the first card in the person list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_PERSON);

        /* Case: select the last card in the person list -> selected */
        Index personCount = Index.fromOneBased(getTypicalPersons().size());
        command = SelectCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertCommandSuccess(command, personCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the person list -> selected */
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* ------------------------ Perform select operations on the shown filtered list ---------------------------- */

        /* Case: filtered person list, select index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, select index within bounds of address book and person list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredPersonList().size());
        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* ----------------------------------- Perform invalid select operations ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty address book -> rejected */
        deleteAllPersons();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author jonleeyz
    @Test
    public void focusOnCommandBox_populateSelectCommandTemplate_usingAccelerator() {
        getCommandBox().click();
        populateSelectCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnResultDisplay_populateSelectCommandTemplate_usingAccelerator() {
        getResultDisplay().click();
        populateSelectCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnPersonListPanel_populateSelectCommandTemplate_usingAccelerator() {
        getPersonListPanel().click();
        populateSelectCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void focusOnBrowserPanel_populateSelectCommandTemplate_usingAccelerator() {
        getBrowserPanel().click();
        populateSelectCommandUsingAccelerator();
        assertPopulationSuccess();
    }

    @Test
    public void populateSelectCommandTemplate_usingMenuButton() {
        populateSelectCommandUsingMenu();
        assertPopulationSuccess();
    }
    //@@author

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected person.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    //@@author jonleeyz
    /**
     * Asserts that population of the {@code CommandBox} with the AddCommand
     * template was successful.
     */
    private void assertPopulationSuccess() {
        assertEquals(SelectCommand.COMMAND_TEMPLATE, getCommandBox().getInput());
        assertEquals(SelectCommand.MESSAGE_USAGE, getResultDisplay().getText());
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof PopulatePrefixesRequestEvent);
        guiRobot.pauseForHuman();

        executeCommand("invalid command");
        assertTrue(getCommandBox().clear());
        assertEquals(MESSAGE_UNKNOWN_COMMAND, getResultDisplay().getText());
        guiRobot.pauseForHuman();
    }

    /**
     * Populates the {@code CommandBox} with the SelectCommand template
     * using the associated accelerator in {@code MainWindow}.
     */
    private void populateSelectCommandUsingAccelerator() {
        populateUsingAccelerator(KeyCode.CONTROL, KeyCode.S);
    }

    /**
     * Populates the {@code CommandBox} with the SelectCommand template
     * using the menu bar in {@code MainWindow}.
     */
    private void populateSelectCommandUsingMenu() {
        populateUsingMenu("Actions", "Select a Person...");
    }
    //@@author

    //@@author jonleeyz-unused
    /* Redundant, kept for legacy purposes
    private void assertPopulationFailure() {
        SelectCommand selectCommand = new SelectCommand();
        assertNotEquals(selectCommand.getTemplate(), getCommandBox().getInput());
        assertNotEquals(selectCommand.getUsageMessage(), getResultDisplay().getText());
        guiRobot.pauseForHuman();

        executeCommand("invalid command");
        assertTrue(getCommandBox().clear());
        assertEquals(MESSAGE_UNKNOWN_COMMAND, getResultDisplay().getText());
        guiRobot.pauseForHuman();
    }
    */
    //@@author
}
