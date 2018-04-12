# jonleeyz
###### \java\seedu\address\commons\events\ui\ExecuteCommandRequestEvent.java
``` java
/**
 * Indicates that a new result is available.
 */
public class ExecuteCommandRequestEvent extends BaseEvent {
    public final String commandWord;

    public ExecuteCommandRequestEvent(ImmediatelyExecutableCommand command) {
        commandWord = command.getCommandWord();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + commandWord;
    }
}
```
###### \java\seedu\address\commons\events\ui\PopulatePrefixesRequestEvent.java
``` java
/**
 * Indicates that a new result is available.
 */
public class PopulatePrefixesRequestEvent extends BaseEvent {

    public final String commandUsageMessage;
    public final String commandTemplate;
    public final int caretIndex;
    private final String commandWord;

    public PopulatePrefixesRequestEvent(PopulatableCommand command) {
        commandUsageMessage = command.getUsageMessage();
        commandTemplate = command.getTemplate();
        caretIndex = command.getCaretIndex();
        commandWord = command.getCommandWord();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + commandWord;
    }
}
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + " " + PREFIX_TYPE + "  " + PREFIX_NAME + "  "
                + PREFIX_PHONE + "  " + PREFIX_EMAIL + "  " + PREFIX_ADDRESS + "  "
                + PREFIX_TAG + " ";
    }

    @Override
    public int getCaretIndex() {
        return (COMMAND_WORD + " " + PREFIX_TYPE + " ").length();
    }

    @Override
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }
```
###### \java\seedu\address\logic\commands\ClearCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + " ";
    }

    @Override
    public int getCaretIndex() {
        return getTemplate().length();
    }

    @Override
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + " ";
    }

    @Override
    public int getCaretIndex() {
        return getTemplate().length();
    }

    @Override
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + " -";
    }

    @Override
    public int getCaretIndex() {
        return getTemplate().length();
    }

    @Override
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }
```
###### \java\seedu\address\logic\commands\HistoryCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
```
###### \java\seedu\address\logic\commands\ImmediatelyExecutableCommand.java
``` java
/**
 * This interface is utilised in the {@code ExecuteCommandRequestEvent} class, where it is used
 * to provide a handle to {@code Commands} that immediately execute on press of their respective
 * keyboard shortcuts.
 */
public interface ImmediatelyExecutableCommand {
    /** Returns the command word of the Command */
    String getCommandWord();
}
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
```
###### \java\seedu\address\logic\commands\LocateCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + "-";
    }

    @Override
    public int getCaretIndex() {
        return getTemplate().length();
    }

    @Override
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }
```
###### \java\seedu\address\logic\commands\PopulatableCommand.java
``` java
/**
 * This interface is utilised in the {@code ExecuteCommandRequestEvent} class, where it is used
 * to provide a handle to {@code Commands} that immediately execute on press of their respective
 * keyboard shortcuts.
 */
public interface PopulatableCommand {
    /** Returns the command word of the Command */
    String getCommandWord();

    /** Returns the complete template (command word + all prefixes) of the Command */
    String getTemplate();

    /** Returns the index where the cursor should be after population of the Command */
    int getCaretIndex();

    /** Returns the usage message of the Command */
    String getUsageMessage();
}
```
###### \java\seedu\address\logic\commands\RedoCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
```
###### \java\seedu\address\logic\commands\SelectCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + " ";
    }

    @Override
    public int getCaretIndex() {
        return getTemplate().length();
    }

    @Override
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }
```
###### \java\seedu\address\logic\commands\UndoCommand.java
``` java
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Handles the event where a valid keyboard shortcut is pressed
     * to populate the CommandBox with command prefixes,
     * {@code PopulatePrefixesRequestEvent}.
     */
    @Subscribe
    private void handlePopulatePrefixesRequestEvent(PopulatePrefixesRequestEvent event) {
        replaceText(event.commandTemplate, event.caretIndex);
    }

    /**
     * Handles the event where a valid keyboard shortcut is pressed
     * to execute a command immediately
     * {@code CommandRequestEvent}.
     */
    @Subscribe
    private void handleExecuteCommandRequestEvent(ExecuteCommandRequestEvent event) {
        replaceText(event.commandWord);
        handleCommandInputChanged();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem undoMenuItem;

    @FXML
    private MenuItem redoMenuItem;

    @FXML
    private MenuItem clearMenuItem;

    @FXML
    private MenuItem historyMenuItem;

    @FXML
    private MenuItem listMenuItem;

    @FXML
    private MenuItem findMenuItem;

    @FXML
    private MenuItem addMenuItem;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private MenuItem editMenuItem;

    @FXML
    private MenuItem locateMenuItem;

    @FXML
    private MenuItem selectMenuItem;
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private void setAccelerators() {
        setAccelerator(exitMenuItem, KeyCombination.valueOf("Alt + Q"));

        setAccelerator(undoMenuItem, KeyCombination.valueOf("Ctrl + Z"));
        setAccelerator(redoMenuItem, KeyCombination.valueOf("Ctrl + Y"));
        setAccelerator(clearMenuItem, KeyCombination.valueOf("Ctrl + Shift + C"));

        setAccelerator(historyMenuItem, KeyCombination.valueOf("Ctrl + H"));
        setAccelerator(listMenuItem, KeyCombination.valueOf("F2"));
        setAccelerator(findMenuItem, KeyCombination.valueOf("Ctrl + F"));

        setAccelerator(addMenuItem, KeyCombination.valueOf("Ctrl + Space"));
        setAccelerator(deleteMenuItem, KeyCombination.valueOf("Ctrl + D"));
        setAccelerator(editMenuItem, KeyCombination.valueOf("Ctrl + E"));
        setAccelerator(locateMenuItem, KeyCombination.valueOf("Ctrl + L"));
        setAccelerator(selectMenuItem, KeyCombination.valueOf("Ctrl + S"));
        setAccelerator(assignMenuItem, KeyCombination.valueOf("Ctrl + Shift + A"));

        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Executes the {@code undo} operation
     */
    @FXML
    private void handleUndo() {
        raise(new ExecuteCommandRequestEvent(new UndoCommand()));
    }

    /**
     * Executes the {@code redo} operation
     */
    @FXML
    private void handleRedo() {
        raise(new ExecuteCommandRequestEvent(new RedoCommand()));
    }

    /**
     * Executes the {@code clear} operation
     */
    @FXML
    private void handleClear() {
        raise(new ExecuteCommandRequestEvent(new ClearCommand()));
    }

    /**
     * Executes the {@code history} operation
     */
    @FXML
    private void handleHistory() {
        raise(new ExecuteCommandRequestEvent(new HistoryCommand()));
    }

    /**
     * Executes the {@code list} operation
     */
    @FXML
    private void handleList() {
        raise(new ExecuteCommandRequestEvent(new ListCommand()));
    }

    /**
     * Populates the {@code CommandBox} with the {@code FindCommand} prefixes.
     */
    @FXML
    private void handleFind() {
        raise(new PopulatePrefixesRequestEvent(new FindCommand()));
    }

    /**
     * Populates the {@code CommandBox} with the {@code AddCommand} prefixes.
     */
    @FXML
    private void handleAdd() {
        raise(new PopulatePrefixesRequestEvent(new AddCommand()));
    }

    /**
     * Populates the {@code CommandBox} with the {@code DeleteCommand} prefixes.
     */
    @FXML
    private void handleDelete() {
        raise(new PopulatePrefixesRequestEvent(new DeleteCommand()));
    }

    /**
     * Populates the {@code CommandBox} with the {@code EditCommand} prefixes.
     */
    @FXML
    private void handleEdit() {
        raise(new PopulatePrefixesRequestEvent(new EditCommand()));
    }

    /**
     * Populates the {@code CommandBox} with the {@code LocateCommand} prefixes.
     */
    @FXML
    private void handleLocate() {
        raise(new PopulatePrefixesRequestEvent(new LocateCommand()));
    }

    /**
     * Populates the {@code CommandBox} with the {@code SelectCommand} prefixes.
     */
    @FXML
    private void handleSelect() {
        raise(new PopulatePrefixesRequestEvent(new SelectCommand()));
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    /**
     * Handles the event where a valid keyboard shortcut is pressed
     * to populate the CommandBox with command prefixes,
     * {@code PopulatePrefixesRequestEvent}.
     */
    @Subscribe
    private void handlePopulatePrefixesRequestEvent(PopulatePrefixesRequestEvent event) {
        setStyleToIndicateCommandSuccess();
        Platform.runLater(() -> {
            displayed.setValue(event.commandUsageMessage);
        });
    }
```
###### \resources\view\MainWindow.fxml
``` fxml
          <Menu mnemonicParsing="false" text="Edit">
            <MenuItem fx:id="undoMenuItem" mnemonicParsing="false" onAction="#handleUndo" text="Undo" />
            <MenuItem fx:id="redoMenuItem" mnemonicParsing="false" onAction="#handleRedo" text="Redo" />
            <MenuItem fx:id="clearMenuItem" mnemonicParsing="false" onAction="#handleClear" text="Clear the Database" />
          </Menu>
          <Menu mnemonicParsing="false" text="View">
            <MenuItem fx:id="historyMenuItem" mnemonicParsing="false" onAction="#handleHistory" text="History" />
            <MenuItem fx:id="listMenuItem" mnemonicParsing="false" onAction="#handleList" text="List all" />
            <MenuItem fx:id="findMenuItem" mnemonicParsing="false" onAction="#handleFind" text="Find..." />
          </Menu>
          <Menu mnemonicParsing="false" text="Actions">
            <MenuItem fx:id="addMenuItem" mnemonicParsing="false" onAction="#handleAdd" text="Add a Person..." />
            <MenuItem fx:id="deleteMenuItem" mnemonicParsing="false" onAction="#handleDelete" text="Delete a Person..." />
            <MenuItem fx:id="editMenuItem" mnemonicParsing="false" onAction="#handleEdit" text="Edit a Person..." />
            <MenuItem fx:id="locateMenuItem" mnemonicParsing="false" onAction="#handleLocate" text="Locate a Person..." />
            <MenuItem fx:id="selectMenuItem" mnemonicParsing="false" onAction="#handleSelect" text="Select a Person..." />
            <MenuItem fx:id="assignMenuItem" mnemonicParsing="false" onAction="#handleAssign" text="Assign Customers to a Runner..." />
          </Menu>
```
