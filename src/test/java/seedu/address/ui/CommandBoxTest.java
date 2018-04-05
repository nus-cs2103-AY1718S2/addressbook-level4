package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Logic logic = new LogicManager(model);

        CommandBox commandBox = new CommandBox(logic);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_handleKeyPress() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    @Test
    public void handleKeyPress_startingWithUp() {
        // empty history
        assertInputHistory(KeyCode.UP, "");
        assertInputHistory(KeyCode.DOWN, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");

        // two commands (latest command is failure)
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.UP, thirdCommand);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
    }

    @Test
    public void handleKeyPress_startingWithDown() {
        // empty history
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);

        // two commands
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, thirdCommand);
    }

    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }

    //@@author aquarinte
    @Test
    public void commandBox_autocompleteCommandWord() {
        //add command
        testAutocompleteForUserInput("a", 1, "add");
        testAutocompleteForUserInput(" ", 1, "add");

        //clear command
        testAutocompleteForUserInput("cl", 1, "clear");

        //delete command
        testAutocompleteForUserInput("d", 1, "delete");

        //edit command
        testAutocompleteForUserInput("e", 1, "edit");
        testAutocompleteForUserInput("ed", 1, "edit");

        //exit command
        testAutocompleteForUserInput("e", 2, "exit");
        testAutocompleteForUserInput("ex", 1, "exit");

        //help command
        testAutocompleteForUserInput("h", 1, "help");
        testAutocompleteForUserInput("he", 1, "help");
        testAutocompleteForUserInput("hel", 1, "help");

        //history command
        testAutocompleteForUserInput("h", 2, "history");
        testAutocompleteForUserInput("hi", 1, "history");
        testAutocompleteForUserInput("hist", 1, "history");

        //list command
        testAutocompleteForUserInput("l", 1, "list");

        //theme command
        testAutocompleteForUserInput("t", 1, "theme");

        //undo
        testAutocompleteForUserInput("u", 1, "undo");

        //redo
        testAutocompleteForUserInput("r", 1, "redo");
    }

    @Test
    public void commandBox_autocompleteOption() {
        testAutocompleteForUserInput("delete -", 1, "delete -a");
        testAutocompleteForUserInput("add -", 2, "add -o");
        testAutocompleteForUserInput("find -", 3, "find -p");
    }

    @Test
    public void commandBox_autocompletePrefix() {
        // prefix n/
        testAutocompleteForUserInput("add -o n", 1, "add -o n/");

        // prefix nr/
        testAutocompleteForUserInput("add -o n", 2, "add -o nr/");
        testAutocompleteForUserInput("add -o nr", 1, "add -o nr/");

        //prefix b/
        testAutocompleteForUserInput("add -p b", 1, "add -p b/");

        //prefix bt/
        testAutocompleteForUserInput("add -p b", 2, "add -p bt/");
    }

    @Test
    public void commandBox_autocompleteNric() {
        // autocomplete suggestions for nric for add command that follows "-o nr/"
        testAutocompleteForUserInput("add -o nr/", 1, "add -o nr/F0184556R");
        testAutocompleteForUserInput("add -o nr/F018", 1, "add -o nr/F0184556R");
        testAutocompleteForUserInput("add -o nr/", 2, "add -o nr/F2345678U");
        testAutocompleteForUserInput("add -o nr/S", 1, "add -o nr/S0123456Q");

        // no nric autocomplete suggestion if add command does not have "-o nr/"
        testAutocompleteForUserInput("add -p nr/S", 1, "add -p nr/S");
        testAutocompleteForUserInput("add -a nr/F", 1, "add -a nr/F");

        // autocomplete suggestions for nric for "edit -p" command
        testAutocompleteForUserInput("edit -p nr/", 1, "edit -p nr/F0184556R");
        testAutocompleteForUserInput("edit -p nr/F018", 1, "edit -p nr/F0184556R");
        testAutocompleteForUserInput("edit -p nr/", 2, "edit -p nr/F2345678U");
        testAutocompleteForUserInput("edit -p nr/S", 1, "edit -p nr/S0123456Q");

        // no nric autocomplete suggestion if edit command does not start with "edit -p"
        testAutocompleteForUserInput("edit -o nr/", 1, "edit -o nr/");
        testAutocompleteForUserInput("edit -o nr/S", 1, "edit -o nr/S");

        // autocomplete suggestions for nric for "find -o" command
        testAutocompleteForUserInput("find -o nr/", 1, "find -o nr/F0184556R");
        testAutocompleteForUserInput("find -o nr/", 3, "find -o nr/G1111111B");
        testAutocompleteForUserInput("find -o nr/T", 3, "find -o nr/T0120956W");
        testAutocompleteForUserInput("find -o nr/T0", 2, "find -o nr/T0123456L");

        // no nric autocomplete suggestion if edit command does not start with "find -o"
        testAutocompleteForUserInput("find -p nr/T0", 2, "find -p nr/T0");
        testAutocompleteForUserInput("find -a nr/S", 2, "find -a nr/S");
    }

    @Test
    public void commandBox_autocompleteTag() {
        testAutocompleteForUserInput("add t/", 1, "add t/Depression");
        testAutocompleteForUserInput("add t/F", 1, "add t/friends");
        testAutocompleteForUserInput("add t/", 2, "add t/Test");
        testAutocompleteForUserInput("add t/ow", 2, "add t/owesMoney");
    }

    /**
     * Checks that {@code userInput} with the {@code numOfTabs} to select an option on autocomplete's context menu
     * will result in {@code actualCommand}.
     */
    private void testAutocompleteForUserInput(String userInput, int numOfTabs, String actualCommand) {
        commandBoxHandle.setText(userInput);

        while (numOfTabs > 0) {
            guiRobot.push(KeyCode.TAB);
            numOfTabs--;
        }
        guiRobot.push(KeyCode.ENTER);

        assertEquals(actualCommand, commandBoxHandle.getInput());
    }
}
