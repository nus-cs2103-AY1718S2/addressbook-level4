# jonleeyz
###### \java\seedu\address\commons\events\ui\ExecuteCommandRequestEvent.java
``` java
/**
 * Indicates that a new request to execute a Command is available.
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
###### \java\seedu\address\commons\events\ui\HomeRequestEvent.java
``` java

/**
 * Indicates a request to execute the home command
 */
public class HomeRequestEvent extends BaseEvent {
    public static final String MESSAGE_HOME =
            "Home view displayed. "
            + "\n\n"
            + "Utilise one of the keyboard shortcuts below to get started!"
            + "\n"
            + "Alternatively, press \"F12\" or type \"help\" to view the User Guide!";

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\PopulatePrefixesRequestEvent.java
``` java
/**
 * Indicates that a new request to populate the CommandBox is available.
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
        return COMMAND_TEMPLATE;
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
        return COMMAND_TEMPLATE;
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
        return COMMAND_TEMPLATE;
    }

    @Override
    public int getCaretIndex() {
        return (COMMAND_WORD + " ").length();
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
        return COMMAND_TEMPLATE;
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
        return COMMAND_TEMPLATE;
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
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Customer(new Name("Xiao Ming"), new Phone("88888888"), new Email("xiao@ming.com"),
                    new Address("The Fullerton"),
                    getTagSet("richxiaoming", "mingdynasty", "HighSES"), new MoneyBorrowed(314159265),
                    createDate(2017, 5, 7), createDate(2018, 5, 7),
                    new StandardInterest(9.71), new LateInterest(), new Runner()),
            new Customer(new Name("Korean Defender"), new Phone("99994321"),
                    new Email("kalbitanglover@tourism.korea.com"), new Address("The Hwang's"),
                    getTagSet("defenderOfTheFree", "defenderOfKalbiTang", "yummeh", "UTownHeritage"),
                    new MoneyBorrowed(413255),
                    createDate(2010, 10, 3), createDate(2019, 1, 1),
                    new StandardInterest(5.4), new LateInterest(), new Runner()),
            new Customer(new Name("Bob the Builder"), new Phone("92334532"), new Email("bob@bobthebuilder.com"),
                    new Address("IKEA Alexandra"),
                    getTagSet("FatherOfHDB", "InBobWeTrust"), new MoneyBorrowed(0.24),
                    createDate(1965, 8, 9), createDate(2015, 8, 9),
                    new StandardInterest(0.0005), new LateInterest(), new Runner()),
            new Runner(new Name("Ah Seng"), new Phone("90011009"), new Email("quick_and_easy_money@hotmail.com"),
                    new Address("Marina Bay Sands"),
                    getTagSet("EmployeeOfTheMonth", "InvestorFirstGrade", "HighSES"), new ArrayList<>()),
            new Runner(new Name("Mas Selamat Kastari"), new Phone("999"), new Email("kastari@johorbahru.my"),
                    new Address("Internal Security Department"),
                    getTagSet("BeatTheSystem", "BeatByTheSystem"), new ArrayList<>()),
            new Customer(new Name("Aunty Kim"), new Phone("99994321"), new Email("hotkorean1905@hotmail.com"),
                    new Address("I'm Kim Korean BBQ"),
                    getTagSet("RichAunty", "KBBQBossLady", "Aunty"),
                    new MoneyBorrowed(413255),
                    createDate(2010, 10, 3), createDate(2019, 1, 1),
                    new StandardInterest(5.4), new LateInterest(), new Runner()),
            new Runner(new Name("Leon Tay"), new Phone("93498349"), new Email("laoda@leontay349.com"),
                    new Address("Bao Mei Boneless Chicken Rice"),
                    getTagSet("LaoDa", "349", "Joker"), new ArrayList<>()),
            new Runner(new Name("Ping An"), new Phone("93698369"), new Email("pingan@houseofahlong.com"),
                    new Address("Ang Mo Kio Police Divison HQ"),
                    getTagSet("UndercoverRunner", "TripleAgent", "Joker"), new ArrayList<>()),
            new Customer(new Name("Da Ming"), new Phone("83699369"), new Email("da@ming.com"),
                    new Address("Fountain of Wealth"),
                    getTagSet("RicherDaMing", "BigMing", "MingSuperior", "mingdynasty"), new MoneyBorrowed(98789060),
                    createDate(2017, 3, 1), createDate(2020, 12, 5),
                    new StandardInterest(3.14), new LateInterest(), new Runner()),
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Removes the current {@code field} or {@code prefix}.
     */
    private void clearCurrentFieldOrPrefix() {
        int currentCaretPosition = commandTextField.getCaretPosition();
        int lastPrefixPosition = getPreviousPrefixPosition(currentCaretPosition);

        // clearing the current field or prefix
        String stringLiteralUpToPrefix = commandTextField.getText().substring(0, lastPrefixPosition);
        String stringLiteralAfterCaret = commandTextField.getText().substring(currentCaretPosition);
        String newCommandBoxText = stringLiteralUpToPrefix + stringLiteralAfterCaret;
        commandTextField.setText(newCommandBoxText);
        commandTextField.positionCaret(lastPrefixPosition);
    }

    /**
     * Positions the caret after the last {@code prefix}.
     */
    private void moveToPreviousPrefix() {
        int currentCaretPosition = commandTextField.getCaretPosition();
        int newCaretPosition = getPreviousPrefixPosition(currentCaretPosition);
        commandTextField.positionCaret(newCaretPosition);
    }

    /**
     * Positions the caret after the next {@code prefix}.
     */
    private void moveToNextPrefix() {
        int currentCaretPosition = commandTextField.getCaretPosition();
        int newCaretPosition = getNextPrefixPosition(currentCaretPosition);
        commandTextField.positionCaret(newCaretPosition);
    }

    private int getPreviousPrefixPosition(int currentCaretPosition) {
        // find last prefix position
        int previousPrefixPosition = commandTextField.getText().lastIndexOf(":", currentCaretPosition);

        // if last prefix is too close to caret, find the second last prefix position
        if (currentCaretPosition - previousPrefixPosition < 3) {
            previousPrefixPosition = commandTextField.getText().lastIndexOf(":", previousPrefixPosition - 1);
        }

        // set new caret position to be in front of chosen prefix. If prefix not found, then set at index 0.
        int newCaretPosition = previousPrefixPosition != -1 ? previousPrefixPosition + 1 : 0;

        // check for space in front of last prefix. If present, move forward one more index.
        if (commandTextField.getText().substring(newCaretPosition, newCaretPosition + 1).equals(" ")) {
            newCaretPosition += 1;
        }

        return newCaretPosition;
    }

    private int getNextPrefixPosition(int currentCaretPosition) {
        // find next prefix position
        int nextPrefixPosition = commandTextField.getText().indexOf(":", currentCaretPosition);
        int newCaretPosition;

        // set new caret position to be in front of chosen prefix. If prefix not found, then set at last index.
        if (nextPrefixPosition != -1) {
            newCaretPosition = nextPrefixPosition + 1;

            // check for space in front of last prefix. If present, move forward one more index.
            if (commandTextField.getText().substring(newCaretPosition, newCaretPosition + 1).equals(" ")) {
                newCaretPosition += 1;
            }
        } else {
            newCaretPosition = commandTextField.getText().length();
        }

        return newCaretPosition;
    }

    /**
     * Handles the event where a valid keyboard shortcut is pressed
     * to populate the CommandBox with command prefixes,
     * {@code PopulatePrefixesRequestEvent}.
     */
    @Subscribe
    private void handlePopulatePrefixesRequestEvent(PopulatePrefixesRequestEvent event) {
        commandTextField.requestFocus();
        replaceText(event.commandTemplate, event.caretIndex);
    }

    /**
     * Handles the event where a valid keyboard shortcut is pressed
     * to execute a command immediately
     * {@code ExecuteCommandRequestEvent}.
     */
    @Subscribe
    private void handleExecuteCommandRequestEvent(ExecuteCommandRequestEvent event) {
        replaceText(event.commandWord);
        handleCommandInputChanged();
        commandTextField.requestFocus();
    }

    /**
     * Handles the event where the Esc key is pressed or "home" is input to the CommandBox.
     * {@code HomeRequestEvent}.
     */
    @Subscribe
    private void handleHomeRequestEvent(HomeRequestEvent event) {
        replaceText("");
        commandTextField.requestFocus();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java

    @FXML
    private MenuItem homeMenuItem;

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
        setAccelerator(homeMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(exitMenuItem, KeyCombination.valueOf("Alt + Q"));

        setAccelerator(undoMenuItem, KeyCombination.valueOf("Ctrl + Z"));
        setAccelerator(redoMenuItem, KeyCombination.valueOf("Ctrl + Y"));
        setAccelerator(clearMenuItem, KeyCombination.valueOf("Ctrl + Shift + C"));

        setAccelerator(historyMenuItem, KeyCombination.valueOf("F3"));
        setAccelerator(listMenuItem, KeyCombination.valueOf("F2"));
        setAccelerator(findMenuItem, KeyCombination.valueOf("Ctrl + F"));

        setAccelerator(addMenuItem, KeyCombination.valueOf("Ctrl + I"));
        setAccelerator(deleteMenuItem, KeyCombination.valueOf("Ctrl + D"));
        setAccelerator(editMenuItem, KeyCombination.valueOf("Ctrl + E"));
        setAccelerator(locateMenuItem, KeyCombination.valueOf("Ctrl + L"));
        setAccelerator(selectMenuItem, KeyCombination.valueOf("Ctrl + S"));
        setAccelerator(assignMenuItem, KeyCombination.valueOf("Ctrl + Shift + A"));

        setAccelerator(helpMenuItem, KeyCombination.valueOf("F12"));
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Executes the {@code home} operation
     */
    @FXML
    private void handleHome() {
        raise(new HomeRequestEvent());
    }

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
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    /**
     * Handles the event where the Esc key is pressed or "home" is input to the CommandBox.
     * {@code HomeRequestEvent}.
     */
    @Subscribe
    private void handleHomeRequestEvent(HomeRequestEvent event) {
        //@TODO to be implemented
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

    /**
     * Handles the event where the Esc key is pressed or "home" is input to the CommandBox.
     * {@code HomeRequestEvent}.
     */
    @Subscribe
    private void handleHomeRequestEvent(HomeRequestEvent event) {
        setStyleToIndicateCommandSuccess();
        Platform.runLater(() -> {
            displayed.setValue(event.MESSAGE_HOME);
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
