# lithiumlkid
###### \java\seedu\address\logic\AutocompleteTest.java
``` java
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.CommandTrie;

public class AutocompleteTest {

    private CommandTrie commandTrie;

    @Before
    public void setup() {
        commandTrie = new CommandTrie();
    }

    @Test
    public void autocomplete_unique_prefix() {
        assert commandTrie.attemptAutoComplete("l").equals("list");
        assert commandTrie.attemptAutoComplete("he").equals("help");
    }

    @Test
    public void autocomplete_multiple_prefix() {
        assert commandTrie.attemptAutoComplete("e").equals("e");
        assert commandTrie.attemptAutoComplete("H").equals("H");

    }

    @Test
    public void autocomplete_mix_case() {
        assert commandTrie.attemptAutoComplete("setT").equals("setTagColour");
    }
}
```
###### \java\seedu\address\model\person\AvatarTest.java
``` java
public class AvatarTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Avatar(null));
    }

    @Test
    public void constructor_invalidAvatar_throwsIllegalArgumentException() {
        String invalidAvatar = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Avatar(invalidAvatar));
    }

    @Test
    public void isValidAvatar() {
        // null avatar number
        Assert.assertThrows(NullPointerException.class, () -> Avatar.isValidAvatar(null));

        // invalid avatar numbers
        assertFalse(Avatar.isValidAvatar("")); // empty string
        assertFalse(Avatar.isValidAvatar(" ")); // spaces only
        assertFalse(Avatar.isValidAvatar("avatar.gif")); // invalid filtype
        assertFalse(Avatar.isValidAvatar("avatar")); // no file type
        assertFalse(Avatar.isValidAvatar("a a")); // spaces within digits

        // valid avatar numbers
        assertTrue(Avatar.isValidAvatar("avatar.png")); // png file
        assertTrue(Avatar.isValidAvatar("/file/path/to/avatar.png")); //mac file path
        assertTrue(Avatar.isValidAvatar("C:\\file\\path\\avatar.png")); // windows file path
    }

    @Test
    public void isEqualAvatar() {
        Avatar x  = new Avatar("avatar.png");
        Avatar y = new Avatar("avatar.png");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }

}
```
###### \java\seedu\address\model\person\JerseyNumberTest.java
``` java
public class JerseyNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JerseyNumber(null));
    }

    @Test
    public void constructor_invalidJerseyNumber_throwsIllegalArgumentException() {
        String invalidJerseyNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new JerseyNumber(invalidJerseyNumber));
    }

    @Test
    public void isValidJerseyNumber() {
        // null jerseyNumber number
        Assert.assertThrows(NullPointerException.class, () -> JerseyNumber.isValidJerseyNumber(null));

        // invalid jerseyNumber numbers
        assertFalse(JerseyNumber.isValidJerseyNumber("")); // empty string
        assertFalse(JerseyNumber.isValidJerseyNumber(" ")); // spaces only
        assertFalse(JerseyNumber.isValidJerseyNumber("-1")); // less than 0
        assertFalse(JerseyNumber.isValidJerseyNumber("100")); // larger than 99
        assertFalse(JerseyNumber.isValidJerseyNumber("1a")); // alphabets with digits
        assertFalse(JerseyNumber.isValidJerseyNumber("1 1")); // spaces within digits

        // valid jerseyNumber numbers
        assertTrue(JerseyNumber.isValidJerseyNumber("0")); // within 0 - 99
        assertTrue(JerseyNumber.isValidJerseyNumber("99"));
    }

    @Test
    public void isEqualJerseyNumber() {
        JerseyNumber x  = new JerseyNumber("0");
        JerseyNumber y = new JerseyNumber("0");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}

```
###### \java\seedu\address\model\person\PositionTest.java
``` java
public class PositionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Position(null));
    }

    @Test
    public void constructor_invalidPosition_throwsIllegalArgumentException() {
        String invalidPosition = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Position(invalidPosition));
    }

    @Test
    public void isValidPosition() {
        // null position number
        Assert.assertThrows(NullPointerException.class, () -> Position.isValidPosition(null));

        // invalid position numbers
        assertFalse(Position.isValidPosition("")); // empty string
        assertFalse(Position.isValidPosition(" ")); // spaces only
        assertFalse(Position.isValidPosition("0")); // less than 1
        assertFalse(Position.isValidPosition("-1")); // negative
        assertFalse(Position.isValidPosition("position")); // non-numeric
        assertFalse(Position.isValidPosition("1a")); // alphabets within digits
        assertFalse(Position.isValidPosition("1 1")); // spaces within digits

        // valid position numbers
        assertTrue(Position.isValidPosition("1")); // within range
        assertTrue(Position.isValidPosition("4"));
    }

    @Test
    public void isEqualPosition() {
        Position x  = new Position("1");
        Position y = new Position("1");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}
```
###### \java\seedu\address\model\person\RatingTest.java
``` java
public class RatingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rating(null));
    }

    @Test
    public void constructor_invalidRating_throwsIllegalArgumentException() {
        String invalidRating = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Rating(invalidRating));
    }

    @Test
    public void isValidRating() {
        // null rating number
        Assert.assertThrows(NullPointerException.class, () -> Rating.isValidRating(null));

        // invalid rating numbers
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating(" ")); // spaces only
        assertFalse(Rating.isValidRating("-1")); // negative
        assertFalse(Rating.isValidRating("rating")); // non-numeric
        assertFalse(Rating.isValidRating("1a")); // alphabets within digits
        assertFalse(Rating.isValidRating("1 1")); // spaces within digits

        // valid rating numbers
        assertTrue(Rating.isValidRating("1")); // within range
        assertTrue(Rating.isValidRating("5"));
    }

    @Test
    public void isEqualRating() {
        Rating x  = new Rating("1");
        Rating y = new Rating("1");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
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
        assertBehaviorForSuccessfulCommand();
        guiRobot.push(KeyCode.V);
        guiRobot.push(KeyCode.TAB);
        assertEquals(ViewCommand.COMMAND_WORD, commandBoxHandle.getInput());
        guiRobot.push(KeyCode.TAB);
        assertEquals(ViewCommand.COMMAND_WORD + " " + ViewCommand.MESSAGE_PARAMETERS, commandBoxHandle.getInput());
        guiRobot.push(KeyCode.TAB);
        assertEquals(ViewCommand.COMMAND_WORD, commandBoxHandle.getInput());
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        guiRobot.push(KeyCode.TAB);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        commandBoxHandle.run("e");
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.ENTER);
        assertEquals(ExitCommand.COMMAND_WORD, commandBoxHandle.getInput());


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
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid rating -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                 + INVALID_RATING_DESC + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, Rating.MESSAGE_RATING_CONSTRAINTS);

        /* Case: invalid position -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                 + RATING_DESC_0 + INVALID_POSITION_DESC + JERSEY_NUMBER_DESC_2;
        assertCommandFailure(command, Position.MESSAGE_POSITION_CONSTRAINTS);

        /* Case: invalid jersey number -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                 + RATING_DESC_0 + POSITION_DESC_STRIKER + INVALID_JERSEY_NUMBER_DESC;
        assertCommandFailure(command, JerseyNumber.MESSAGE_JERSEY_NUMBER_CONSTRAINTS);

        /* Case: invalid jersey avatar -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2 + INVALID_AVATAR_NO_FILE;
        assertCommandFailure(command, AddCommand.MESSAGE_FILE_NOT_FOUND);

        /* Case: invalid jersey avatar -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2 + INVALID_AVATAR_TYPE;
        assertCommandFailure(command, Avatar.MESSAGE_AVATAR_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        // TODO: place holder for success message, change to proper assert method
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
