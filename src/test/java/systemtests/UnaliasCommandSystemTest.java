package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNALIAS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNALIAS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_MAP2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_NUMBER;
import static seedu.address.testutil.TypicalAliases.ADD;
import static seedu.address.testutil.TypicalAliases.MAP_2;
import static seedu.address.testutil.TypicalAliases.UPLOAD;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.AliasNotFoundException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.testutil.AliasUtil;
import seedu.address.testutil.TypicalAliases;

//@@author jingyinno
public class UnaliasCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void unalias() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform remove operations from the alias list ----------------------------- */

        /* Case: remove an alias from a non-empty address book, command with leading spaces and trailing spaces
        * -> removed
        * */
        String toDeleteAdd = VALID_ALIAS_ADD;
        generateAliases(model);
        String command = "   " + UnaliasCommand.COMMAND_WORD + "  " + VALID_ALIAS_ADD + "   ";
        Alias[] expectedAliasArray = getRemainingAliases(new Alias[] {ADD});
        Alias[][] expectedAliases = new Alias[][] {expectedAliasArray};
        assertCommandSuccess(command, toDeleteAdd, expectedAliases);

        /* Case: undo removing ADD from the list -> ADD added */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo removing ADD from the list -> ADD removed again */
        command = RedoCommand.COMMAND_WORD;
        model.removeAlias(VALID_ALIAS_ADD);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        expectedAliases = new Alias[][] {expectedAliasArray};
        assertCommandSuccess(command, model, expectedResultMessage, expectedAliases);

        /* Case: remove another alias from a non-empty address book, command with leading and trailing spaces
         * -> removed
         * */
        String toDeleteMap = VALID_ALIAS_MAP2;
        command = "   " + UnaliasCommand.COMMAND_WORD + "  " + VALID_ALIAS_MAP2 + "  ";
        expectedAliasArray = getRemainingAliases(new Alias[] {MAP_2, ADD});
        expectedAliases = new Alias[][] {expectedAliasArray};
        assertCommandSuccess(command, toDeleteMap, expectedAliases);

        /* Case: remove another alias from a non-empty address book, command with leading and trailing spaces
         * -> removed
         * */
        String toDeleteUpload = VALID_ALIAS_NUMBER;
        command = "   " + UnaliasCommand.COMMAND_WORD + "  " + VALID_ALIAS_NUMBER + "  ";
        expectedAliasArray = getRemainingAliases(new Alias[] {MAP_2, ADD, UPLOAD});
        expectedAliases = new Alias[][] {expectedAliasArray};
        assertCommandSuccess(command, toDeleteUpload, expectedAliases);

        /* --------------------------------- Perform invalid unalias operations ------------------------------------- */

        /* Case: remove nonexistent alias from a non-empty address book, command with leading and trailing spaces
         * -> rejected
         * */
        command = "   " + UnaliasCommand.COMMAND_WORD + "  " + INVALID_UNALIAS + "  ";
        assertCommandFailure(command, UnaliasCommand.MESSAGE_UNKNOWN_UNALIAS);

        /* Case: remove alias with symbols from a non-empty address book, command with leading and trailing spaces
         * -> rejected
         * */
        command = "   " + UnaliasCommand.COMMAND_WORD + "  " + INVALID_UNALIAS_DESC + "  ";
        assertCommandFailure(command, Alias.MESSAGE_ALIAS_CONSTRAINTS);
    }

    /**
     * Executes the {@code UnaliasCommand} that removes {@code toDelete} from the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code unaliasCommand} <br>
     * 4. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * the current model without {@code toDelete}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String toDelete, Alias[][] expectedTable) {
        Model expectedModel = getModel();
        try {
            expectedModel.removeAlias(toDelete);
        } catch (AliasNotFoundException e) {
            throw new AssertionError("toDelete does not exists in the model.");
        }
        String expectedResultMessage = UnaliasCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedTable);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Alias)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see UnaliasCommandSystemTest#assertCommandSuccess(String, String, Alias[][])
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

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Alias)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code AliasListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see UnaliasCommandSystemTest#assertCommandSuccess(String, String, Alias[][])
     */
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
     * To add new alias into {@code model} with typicalAliases
     * @param model the current model of the testing stage
     */
    private void generateAliases(Model model) {
        List<Alias> aliases = TypicalAliases.getTypicalAliases();
        for (Alias alias : aliases) {
            try {
                model.addAlias(alias);
            } catch (DuplicateAliasException dpe) {
                throw new IllegalArgumentException("toAdd already exists in the model.");
            }
            executeCommand(AliasUtil.getAliasCommand(alias));
        }
    }

    /**
     * To generate a new expected aliases list with typicalAliases excluding {@code aliases}
     * @param aliases the aliases to be excluded from typicalAlias list
     * @return the expected aliases excuding {@code aliases}
     */
    private Alias[] getRemainingAliases(Alias[] aliases) {
        List<Alias> expectedAliases = TypicalAliases.getTypicalAliases();
        for (Alias alias : aliases) {
            expectedAliases.remove(alias);
        }
        return expectedAliases.toArray(new Alias[expectedAliases.size()]);
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
