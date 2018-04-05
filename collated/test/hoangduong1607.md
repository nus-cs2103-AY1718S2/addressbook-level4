# hoangduong1607
###### \java\guitests\guihandles\CommandBoxHandle.java
``` java
    /**
     * Inserts the given string to text at current caret position
     */
    public void insertText(String text) {
        int caretPosition = getRootNode().getCaretPosition();
        guiRobot.interact(() -> getRootNode().insertText(caretPosition, text));
        guiRobot.pauseForHuman();
    }

```
###### \java\seedu\recipe\logic\parser\ViewGroupCommandParserTest.java
``` java
package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recipe.logic.commands.ViewGroupCommand;
import seedu.recipe.model.recipe.GroupName;
import seedu.recipe.model.recipe.GroupPredicate;

public class ViewGroupCommandParserTest {

    private ViewGroupCommandParser parser = new ViewGroupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewGroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyArgs_success() {
        String groupNameString = "My best";
        GroupName groupName = new GroupName(groupNameString);
        ViewGroupCommand expectedViewGroupCommand = new ViewGroupCommand(new GroupPredicate(groupName), groupName);
        assertParseSuccess(parser, groupNameString, expectedViewGroupCommand);
    }

}
```
###### \java\seedu\recipe\ui\CommandBoxTest.java
``` java
    @Test
    public void commandBox_handleShowingSuggestions() {
        commandBoxHandle.insertText(ADD_COMMAND);
        commandBoxHandle.insertText(WHITESPACE);
        commandBoxHandle.insertText(String.valueOf(PREFIX_NAME.charAt(0)));
        guiRobot.push(KeyboardShortcutsMapping.SHOW_SUGGESTIONS_COMMAND);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.ENTER);
        assertInput(ADD_COMMAND_WITH_PREFIX_NAME);

        commandBoxHandle.insertText(WHITESPACE);
        guiRobot.push(KeyboardShortcutsMapping.SHOW_SUGGESTIONS_COMMAND);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.ENTER);
        assertInput(SECOND_SUGGESTION);
    }

    @Test
    public void commandBox_handleAutoCompletion() {
        guiRobot.push(KeyboardShortcutsMapping.SHOW_SUGGESTIONS_COMMAND);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.ENTER);
        assertInput(AUTO_COMPLETION_FOR_ADD_COMMAND);

        guiRobot.push(KeyboardShortcutsMapping.NEXT_FIELD);
        commandBoxHandle.insertText(RECIPE_NAME);
        assertInput(AUTO_COMPLETION_FOR_ADD_COMMAND_WITH_RECIPE_NAME);
    }

```
###### \java\systemtests\GroupCommandSystemTest.java
``` java
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
```
