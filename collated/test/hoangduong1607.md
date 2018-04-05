# hoangduong1607
###### /java/seedu/recipe/ui/CommandBoxTest.java
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
###### /java/guitests/guihandles/CommandBoxHandle.java
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
