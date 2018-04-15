package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.RemarkCommand.COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.commons.events.logic.EditRemarkEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

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

    //@@author sherlynng
    @Test
    public void handleKeyPress_addCommandPressTab_autofill() {
        String expectedOutput = "add n/ p/ e/ a/ $/ sub/ lvl/ r/";

        // checks for add command word
        commandBoxHandle.setInput("add");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for add command word alias
        commandBoxHandle.setInput("a");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks if tab works correctly
        expectedOutput = "add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 $/50"
                         + " sub/Math lvl/Lower Sec r/Student";
        actualOutput = enterPersonDetails();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_addCommandPressDelete_removePreviousPrefix() {
        String expectedOutput = "add p/ e/ a/ $/ sub/ lvl/ r/";

        // checks for add command word
        commandBoxHandle.setInput("add");
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.DELETE);

        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_selectCommandPressTab_autofill() {
        String expectedOutput = "select 1";

        // checks for select command word
        commandBoxHandle.setInput("select");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for select command word alias
        commandBoxHandle.setInput("s");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_deleteCommandPressTab_autofill() {
        String expectedOutput = "delete 1";

        // checks for delete command word
        commandBoxHandle.setInput("delete");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for delete command word alias
        commandBoxHandle.setInput("d");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_unmatchCommandPressTab_autofill() {
        String expectedOutput = "unmatch 1";

        // checks for unmatch command word
        commandBoxHandle.setInput("unmatch");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for unmatch command word alias
        commandBoxHandle.setInput("um");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_unmatchCommandChangeIndex_autofill() {
        String expectedOutput = "unmatch 2";

        // checks for unmatch command word
        commandBoxHandle.setInput("unmatch");
        guiRobot.push(KeyCode.TAB);

        // change index in unmatch command
        guiRobot.push(KeyCode.DIGIT2);

        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_matchCommandPressTab_autofill() {
        String expectedOutput = "match 1 2";

        // checks for match command word
        commandBoxHandle.setInput("match");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for match command word alias
        commandBoxHandle.setInput("m");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_matchCommandChangeIndexes_autofill() {
        String expectedOutput = "match 4 5";

        // checks for match command word
        commandBoxHandle.setInput("match");
        guiRobot.push(KeyCode.TAB);

        // change indexes in match command
        guiRobot.push(KeyCode.DIGIT4);
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.DIGIT5);

        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_editCommandPressTab_autofill() {
        String expectedOutput = "edit 1 n/ p/ e/ a/ $/ sub/ lvl/ r/";

        // checks for edit command word
        commandBoxHandle.setInput("edit");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for edit command word alias
        commandBoxHandle.setInput("e");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_editCommandPressDelete_removePreviousPrefix() {
        String expectedOutput = "edit 1 p/ e/ a/ $/ sub/ lvl/ r/";

        // checks for edit command word
        commandBoxHandle.setInput("edit");
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.TAB);

        guiRobot.push(KeyCode.DELETE);

        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // delete 6 more times for testing repetitive pressing of delete button
        int i = 0;
        while (i < 6) {
            guiRobot.push(KeyCode.DELETE);
            i++;
        }
        commandBoxHandle.insertInput("Tutor");

        expectedOutput = "edit 1 r/Tutor";
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_remarkCommandPressTab_autofill() {
        String expectedOutput = "remark 1 r/";

        // checks for remark command word
        commandBoxHandle.setInput("remark");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for remark command word alias
        commandBoxHandle.setInput("rk");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_rateCommandPressTab_autofill() {
        String expectedOutput = "rate 1 r/";

        // checks for rate command word
        commandBoxHandle.setInput("rate");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for rate command word alias
        commandBoxHandle.setInput("rt");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Enters Person details using GUI robot
     * @return String entered by GUI robot
     */
    private String enterPersonDetails() {
        commandBoxHandle.insertInput("John Doe");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("98765432");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("johnd@example.com");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("311, Clementi Ave 2, #02-25");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("50");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("Math");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("Lower Sec");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("Student");

        return commandBoxHandle.getInput();
    }

    @Test
    public void editRemarkEventTest_success() {
        Person person = ALICE;

        EditRemarkEvent editRemarkEventStub = new EditRemarkEvent(COMMAND_WORD + " "
                + INDEX_FIRST_PERSON + " " + PREFIX_REMARK + person.getRemark());
        postNow(editRemarkEventStub);

        String expectedOutput = COMMAND_WORD + " " + INDEX_FIRST_PERSON + " " + PREFIX_REMARK + person.getRemark();
        String actualOutput = commandBoxHandle.getInput();

        guiRobot.pauseForHuman();
        assertEquals(expectedOutput, actualOutput);
    }
}
