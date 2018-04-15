//@@author hoangduong1607
package systemtests;

import static seedu.recipe.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.logic.commands.GroupCommand;
import seedu.recipe.logic.parser.CliSyntax;
import seedu.recipe.model.Model;

public class GroupCommandSystemTest extends RecipeBookSystemTest {

    private static final String GROUP_NAME = "My best";

    private static final String WHITESPACE = " ";
    private static final String FIRST_INDEX = "1";
    private static final String SECOND_INDEX = "2";

    @Test
    public void group() throws Exception {
        final Model defaultModel = getModel();

        /* Case: group with valid name and indices -> successful */
        String command = GroupCommand.COMMAND_WORD + WHITESPACE + CliSyntax.PREFIX_GROUP_NAME + GROUP_NAME + WHITESPACE
                + CliSyntax.PREFIX_INDEX + FIRST_INDEX + WHITESPACE + CliSyntax.PREFIX_INDEX + SECOND_INDEX;
        String expectedResultMessage = String.format(GroupCommand.MESSAGE_SUCCESS, GROUP_NAME);
        assertCommandSuccess(command, expectedResultMessage, defaultModel);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("gRoUp", MESSAGE_UNKNOWN_COMMAND);

        /*Case: invalid index -> rejected */
        String invalidIndex = String.valueOf(defaultModel.getRecipeBook().getRecipeList().size() + 1);
        command = GroupCommand.COMMAND_WORD + WHITESPACE + CliSyntax.PREFIX_GROUP_NAME + GROUP_NAME + WHITESPACE
                + CliSyntax.PREFIX_INDEX + invalidIndex;
        expectedResultMessage = Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
        assertCommandFailure(command, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RecipeBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see RecipeBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
