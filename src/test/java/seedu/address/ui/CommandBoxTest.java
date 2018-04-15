package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;
    private HashMap<String, String> keywordColorCoding;

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

        keywordColorCoding = commandBox.initializeKeywordColorCoding();
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

    @Test
    public void initializeKeywordColorCoding_isAddGreen_returnTrue() {
        String addCommandKeyword = "add";
        String correctKeywordColor = "white";
        assertEqualCommandKeywordTag(addCommandKeyword, correctKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isAddGreen_returnFalse() {
        String addCommandKeyword = "add";
        String wrongKeywordColor = "yellow";
        assertNotEqualCommandKeywordTag(addCommandKeyword, wrongKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isListYellow_returnTrue() {
        String listCommandKeyword = "list";
        String correctKeywordColor = "white";
        assertEqualCommandKeywordTag(listCommandKeyword, correctKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isListYellow_returnFalse() {
        String listCommandKeyword = "list";
        String wrongKeywordColor = "blue";
        assertNotEqualCommandKeywordTag(listCommandKeyword, wrongKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isDeleteRed_returnTrue() {
        String deleteCommandKeyword = "delete";
        String correctKeywordColor = "white";
        assertEqualCommandKeywordTag(deleteCommandKeyword, correctKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isDeleteRed_returnFalse() {
        String deleteCommandKeyword = "delete";
        String wrongKeywordColor = "brown";
        assertNotEqualCommandKeywordTag(deleteCommandKeyword, wrongKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isFindBlue_returnTrue() {
        String findCommandKeyword = "find";
        String correctKeywordColor = "white";
        assertEqualCommandKeywordTag(findCommandKeyword, correctKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isFindBlue_returnFalse() {
        String findCommandKeyword = "find";
        String wrongKeywordColor = "pink";
        assertNotEqualCommandKeywordTag(findCommandKeyword, wrongKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isClearRed_returnTrue() {
        String clearCommandKeyword = "clear";
        String correctKeywordColor = "white";
        assertEqualCommandKeywordTag(clearCommandKeyword, correctKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isClearRed_returnFalse() {
        String clearCommandKeyword = "clear";
        String wrongKeywordColor = "blue";
        assertNotEqualCommandKeywordTag(clearCommandKeyword, wrongKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isHelpBlue_returnTrue() {
        String helpCommandKeyword = "help";
        String correctKeywordColor = "white";
        assertEqualCommandKeywordTag(helpCommandKeyword, correctKeywordColor);
    }

    @Test
    public void initializeKeywordColorCoding_isHelpBlue_returnFalse() {
        String helpCommandKeyword = "help";
        String wrongKeywordColor = "brown";
        assertNotEqualCommandKeywordTag(helpCommandKeyword, wrongKeywordColor);
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *     - tag is cleared
     */
    private void assertEqualCommandKeywordTag(String commandKeyword, String correctKeywordColor) {
        commandBoxHandle.run(commandKeyword);
        assertEqualColor(correctKeywordColor, commandKeyword);
    }

    /**
     * Runs a command that fails, then verifies that <br>
     *     - tag is cleared
     */
    private void assertNotEqualCommandKeywordTag(String commandKeyword, String wrongKeywordColor) {
        commandBoxHandle.run(commandKeyword);
        assertDifferentColor(wrongKeywordColor, commandKeyword);
    }

    /**
     * Verifies that the expected command keyword color is the same as {@code correctKeywordColor}
     */
    private void assertEqualColor(String correctKeywordColor, String commandKeyword) {
        assertEquals(correctKeywordColor, keywordColorCoding.get(commandKeyword));
    }

    /**
     * Verifies that the expected command keyword color is the not the same as {@code correctKeywordColor}
     */
    private void assertDifferentColor(String wrongKeywordColor, String commandKeyword) {
        assertNotEquals(wrongKeywordColor, keywordColorCoding.get(commandKeyword));
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
}
