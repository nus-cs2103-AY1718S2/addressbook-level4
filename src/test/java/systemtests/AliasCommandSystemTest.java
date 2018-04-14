package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalAliases.HISTORY;
import static seedu.address.testutil.TypicalAliases.MAP_1;
import static seedu.address.testutil.TypicalAliases.MAP_2;

import org.junit.Test;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.testutil.AliasUtil;

public class AliasCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void alias() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the alias list ----------------------------- */

        /* Case: add an alias to a non-empty address book, command with leading spaces and trailing spaces -> added */
        Alias toAdd_map1 = MAP_1;
        String command = "   " + AliasCommand.COMMAND_WORD + "  " + ALIAS_DESC_MAP1;
        Alias[][] expectedAliases = new Alias[][] {{toAdd_map1}};
        assertCommandSuccess(command, toAdd_map1, expectedAliases);

        /* Case: undo adding MAP_1 to the list -> MAP_1 deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        expectedAliases = new Alias[][] {};
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding MAP_1 to the list -> MAP_1 added again */
        command = RedoCommand.COMMAND_WORD;
        model.addAlias(toAdd_map1);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        expectedAliases = new Alias[][] {{toAdd_map1}};
        assertCommandSuccess(command, model, expectedResultMessage, expectedAliases);

        /* Case: add an alias of a different command -> added */
        Alias toAdd_history = HISTORY;
        command = "   " + AliasCommand.COMMAND_WORD + "  " + ALIAS_DESC_HISTORY;
        expectedAliases = new Alias[][] {{toAdd_map1, toAdd_history}};
        assertCommandSuccess(command, toAdd_history, expectedAliases);

        /* Case: add another alias of MAP command -> added */
        Alias toAdd_map2 = MAP_2;
        command = "   " + AliasCommand.COMMAND_WORD + "  " + ALIAS_DESC_MAP2;
        expectedAliases = new Alias[][] {{toAdd_map2, toAdd_history}, {toAdd_map1}};
        assertCommandSuccess(command, toAdd_map2, expectedAliases);

        /* Case: add to empty address book -> added */
        deleteAllPersonsAndAliases();
        command = "   " + AliasCommand.COMMAND_WORD + "  " + ALIAS_DESC_HISTORY;
        expectedAliases = new Alias[][] {{toAdd_history}};
        assertCommandSuccess(toAdd_history,expectedAliases);


        /* --------------------------------- Perform invalid alias operations ------------------------------------- */

        /* Case: add a duplicate alias -> rejected */
        //Alias toAdd_history2 = HISTORY;
        command = "   " + AliasCommand.COMMAND_WORD + "  " + ALIAS_DESC_HISTORY;
        assertCommandFailure(command, AliasCommand.MESSAGE_DUPLICATE_ALIAS);

    }

    /**
     * Executes the {@code AliasCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AliasCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Alias toAdd, Alias[][] expectedTable) {
        assertCommandSuccess(AliasUtil.getAliasCommand(toAdd), toAdd, expectedTable);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Alias)}. Executes {@code command}
     * instead.
     * @see AliasCommandSystemTest#assertCommandSuccess(Alias, Alias[][])
     */
    private void assertCommandSuccess(String command, Alias toAdd, Alias[][] expectedTable) {
        Model expectedModel = getModel();
        try {
            expectedModel.addAlias(toAdd);
        } catch (DuplicateAliasException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AliasCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedTable);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Alias)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AliasCommandSystemTest#assertCommandSuccess(String, Alias, Alias[][])
     */
    private void assertCommandSuccess(String command, Model expectedModel,
                                      String expectedResultMessage, Alias[][] expectedTable) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
        assertAliasTable(expectedTable);
    }

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    private void assertAliasTable(Alias[][] expectedTable) {
        executeCommand(ListCommand.COMMAND_WORD);
        assertTableDisplaysExpected("", ListCommand.MESSAGE_SUCCESS, expectedTable);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code AliasListPanel} remain unchanged.<br>
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
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
