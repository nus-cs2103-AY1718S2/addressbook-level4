package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListInterviewCommand;
import seedu.address.logic.commands.ListJobsCommand;
import seedu.address.logic.commands.MatchJobCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CommandBoxTest extends GuiUnitTest {

    private static final int FIRST_INDEX = 0;
    private static final int THIRD_INDEX = 2;

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMPLETE_COMMAND_FIRST_COMPLETION = ListCommand.COMMAND_WORD + " ";
    private static final String COMPLETE_COMMAND_SECOND_COMPLETION = ListInterviewCommand.COMMAND_WORD + " ";
    private static final String COMPLETE_COMMAND_THIRD_COMPLETION = ListJobsCommand.COMMAND_WORD + " ";
    private static final String COMMAND_WITH_ONE_COMPLETION = MatchJobCommand.COMMAND_WORD
            .substring(FIRST_INDEX, THIRD_INDEX);
    private static final String COMPLETE_COMMAND_WITH_ONE_COMPLETION = MatchJobCommand.COMMAND_WORD + " ";
    private static final String COMMAND_WITH_MULTIPLE_COMPLETIONS = ListCommand.COMMAND_WORD
            .substring(FIRST_INDEX, THIRD_INDEX);
    private static final String COMMAND_THAT_FAILS = "invalid command";
    private static final String COMMAND_WITH_SWAPPED_CHARACTERS = "lsit";
    private static final String COMMAND_WITH_MISSING_CHARACTER = "ist";
    private static final String COMMAND_WITH_EXTRA_CHARACTER = "llist";
    private static final String COMMAND_WITH_MULTIPLE_MISTAKES = "lllist";
    private static final String EXPECTED_COMMAND_CORRECTION = ListCommand.COMMAND_WORD + " ";
    private static final String DELETE_COMAND_WITH_TYPO = "dlete";
    private static final String INCORRECT_COMMAND_WITH_ARGUMENTS = DELETE_COMAND_WITH_TYPO + " " + THIRD_INDEX;
    private static final String CORRECT_COMMAND_WITH_ARGUMENTS = DeleteCommand.COMMAND_WORD + " " + THIRD_INDEX + " ";

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

    //@@author ChengSashankh
    @Test
    public void handleKeyPress_startingWithTab() {
        // no completion exists
        assertInputHistory(KeyCode.TAB, "");

        // one completion exists
        commandBoxHandle.run(COMMAND_WITH_ONE_COMPLETION);
        assertInputHistory(KeyCode.TAB, COMPLETE_COMMAND_WITH_ONE_COMPLETION);

        // no change on multiple tab press
        assertInputHistory(KeyCode.TAB, COMPLETE_COMMAND_WITH_ONE_COMPLETION);

        // three possible completions exist
        commandBoxHandle.run(COMMAND_WITH_MULTIPLE_COMPLETIONS);
        assertInputHistory(KeyCode.TAB, COMPLETE_COMMAND_FIRST_COMPLETION);
        assertInputHistory(KeyCode.TAB, COMPLETE_COMMAND_SECOND_COMPLETION);
        assertInputHistory(KeyCode.TAB, COMPLETE_COMMAND_THIRD_COMPLETION);

        // on further tab press it should cycle through
        assertInputHistory(KeyCode.TAB, COMPLETE_COMMAND_FIRST_COMPLETION);

        // incorrect command phrase is attempted to be completed
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.TAB, COMMAND_THAT_FAILS);
    }
    //@@author

    @Test
    public void handleKeyPress_afterSpace() {
        // no correction exists
        assertInputHistory(KeyCode.SPACE, " ");

        // missing character correction exists
        commandBoxHandle.run(COMMAND_WITH_MISSING_CHARACTER);
        assertInputHistory(KeyCode.SPACE, EXPECTED_COMMAND_CORRECTION);

        // additional character correction exists
        commandBoxHandle.run(COMMAND_WITH_EXTRA_CHARACTER);
        assertInputHistory(KeyCode.SPACE, EXPECTED_COMMAND_CORRECTION);

        // swapped character correction exists
        commandBoxHandle.run(COMMAND_WITH_SWAPPED_CHARACTERS);
        assertInputHistory(KeyCode.SPACE, EXPECTED_COMMAND_CORRECTION);

        // command with arguments copy pasted with typo in command word
        commandBoxHandle.run(INCORRECT_COMMAND_WITH_ARGUMENTS);
        assertInputHistory(KeyCode.SPACE, CORRECT_COMMAND_WITH_ARGUMENTS);

        // command that cannot be corrected
        commandBoxHandle.run(COMMAND_WITH_MULTIPLE_MISTAKES);
        assertInputHistory(KeyCode.TAB, COMMAND_WITH_MULTIPLE_MISTAKES);
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
