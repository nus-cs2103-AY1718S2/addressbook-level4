package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.testutil.EventsCollectorRule;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";
    private static final String EXAMPLE_NAME = "Aaron";
    private static final String EXAMPLE_PHONE = "96781452";
    private static final String EXAMPLE_COMMAND_TEMPLATE = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + "  "
            + PREFIX_NAME + " " + EXAMPLE_NAME + " " + PREFIX_PHONE + EXAMPLE_PHONE + " ";

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
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

    //@@author jonleeyz
    @Test
    public void handleKeyPress_shiftTab_whenNoPrefixesPresent() {
        commandBoxHandle.setInput(COMMAND_THAT_FAILS);
        int expectedCaretPosition = COMMAND_THAT_FAILS.length();
        commandBoxHandle.setCaretPosition(expectedCaretPosition);

        assertShiftTabPressBehaviour(expectedCaretPosition, COMMAND_THAT_FAILS);
    }

    @Test
    public void handleKeyPress_shiftTab_whenPrefixesPresent() {
        // initialisation
        commandBoxHandle.setInput(EXAMPLE_COMMAND_TEMPLATE);
        int expectedCaretPosition = EXAMPLE_COMMAND_TEMPLATE.length();
        commandBoxHandle.setCaretPosition(expectedCaretPosition);

        // test skipping past third prefix's argument and one trailing space
        expectedCaretPosition =
                assertShiftTabPressBehaviour(expectedCaretPosition, EXAMPLE_PHONE + " ");

        // test skipping past second prefix's argument and third prefix, with no trailing spaces
        expectedCaretPosition =
                assertShiftTabPressBehaviour(expectedCaretPosition, EXAMPLE_NAME + " " + PREFIX_PHONE);

        // test skipping past second prefix, with one trailing space following second prefix's argument
        expectedCaretPosition =
                assertShiftTabPressBehaviour(expectedCaretPosition, " " + PREFIX_NAME + " ");

        // test skipping past command word and first prefix, to before the entire CommandBox input
        assertShiftTabPressBehaviour(expectedCaretPosition, AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + " ");
    }

    @Test
    public void handleKeyPress_tab_whenNoPrefixesPresent() {
        commandBoxHandle.setInput(COMMAND_THAT_FAILS);
        int expectedCaretPosition = 0;
        commandBoxHandle.setCaretPosition(expectedCaretPosition);

        assertTabPressBehaviour(expectedCaretPosition, COMMAND_THAT_FAILS);
    }

    @Test
    public void handleKeyPress_tab_whenPrefixesPresent() {
        // initialisation
        commandBoxHandle.setInput(EXAMPLE_COMMAND_TEMPLATE);
        int expectedCaretPosition = 0;
        commandBoxHandle.setCaretPosition(expectedCaretPosition);

        // test skipping past command word and first prefix
        expectedCaretPosition =
                assertTabPressBehaviour(expectedCaretPosition, AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + " ");

        // test skipping past second prefix, with one trailing space following second prefix
        expectedCaretPosition =
                assertTabPressBehaviour(expectedCaretPosition, " " + PREFIX_NAME + " ");

        // test skipping past second prefix's argument and third prefix, without no trailing spaces
        expectedCaretPosition =
                assertTabPressBehaviour(expectedCaretPosition, EXAMPLE_NAME + " " + PREFIX_PHONE);

        // test skipping past third prefix's argument and one trailing space, to after the entire CommandBox input
        assertTabPressBehaviour(expectedCaretPosition, EXAMPLE_PHONE + " ");
    }

    @Test
    public void handleKeyPress_shiftBackspace_whenNoPrefixesPresent() {
        commandBoxHandle.setInput(COMMAND_THAT_FAILS);
        int initialCaretPosition = COMMAND_THAT_FAILS.length();
        commandBoxHandle.setCaretPosition(initialCaretPosition);

        assertShiftBackspacePressBehaviour(COMMAND_THAT_FAILS);
    }

    @Test
    public void handleKeyPress_shiftBackspace_whenPrefixesPresent() {
        // initialisation
        commandBoxHandle.setInput(EXAMPLE_COMMAND_TEMPLATE);
        int initialCaretPosition = EXAMPLE_COMMAND_TEMPLATE.length();
        commandBoxHandle.setCaretPosition(initialCaretPosition);

        // test deleting third prefix's argument and one trailing space
        assertShiftBackspacePressBehaviour(EXAMPLE_PHONE + " ");

        // test deleting second prefix's argument and third prefix, with no trailing spaces
        assertShiftBackspacePressBehaviour(EXAMPLE_NAME + " " + PREFIX_PHONE);

        // test deleting second prefix, with one trailing space following second prefix's argument
        assertShiftBackspacePressBehaviour(" " + PREFIX_NAME + " ");

        // test deleting command word and first prefix
       assertShiftBackspacePressBehaviour(AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + " ");
    }

    private int assertShiftTabPressBehaviour(int lastCaretPosition, String stringLiteralSkipped) {
        guiRobot.push(KeyCode.SHIFT, KeyCode.TAB);
        int expectedCaretPosition = lastCaretPosition - stringLiteralSkipped.length();
        assertEquals(expectedCaretPosition, commandBoxHandle.getCaretPosition());
        return expectedCaretPosition;
    }

    private int assertTabPressBehaviour(int lastCaretPosition, String stringLiteralSkipped) {
        guiRobot.push(KeyCode.TAB);
        int expectedCaretPosition = lastCaretPosition + stringLiteralSkipped.length();
        assertEquals(expectedCaretPosition, commandBoxHandle.getCaretPosition());
        return expectedCaretPosition;
    }

    private void assertShiftBackspacePressBehaviour(String stringLiteralDeleted) {
        String inputBeforePush = commandBoxHandle.getInput();
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        String inputAfterPush =
                inputBeforePush.substring(0, inputBeforePush.length() - stringLiteralDeleted.length());
        assertEquals(inputAfterPush, commandBoxHandle.getInput());
    }
    //@@author

    //@@author jonleeyz-reused
    /**
     * Runs a command that fails, then verifies that <br>
     *      - {@code NewResultAvailableEvent} is posted
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertFalse(((NewResultAvailableEvent) eventsCollectorRule.eventsCollector.getMostRecent()).isSuccessful());
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
        eventsCollectorRule.eventsCollector.reset();

        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - {@code NewResultAvailableEvent} is posted
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertTrue(((NewResultAvailableEvent) eventsCollectorRule.eventsCollector.getMostRecent()).isSuccessful());
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
        eventsCollectorRule.eventsCollector.reset();

        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }
    //@@author

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }
}
