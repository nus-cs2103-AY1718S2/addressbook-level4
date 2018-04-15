# tiny767
###### \java\seedu\address\logic\commands\FacebookLoginCommandTest.java
``` java
public class FacebookLoginCommandTest {
    @Test
    public void equals() {
        FacebookLoginCommand fbLoginCommand = new FacebookLoginCommand();
        // same object -> returns true
        assertTrue(fbLoginCommand.equals(fbLoginCommand));

        // same values -> returns true
        FacebookLoginCommand anotherFbLoginCommand = new FacebookLoginCommand();
        assertTrue(fbLoginCommand.equals(anotherFbLoginCommand));

        // different types -> returns false
        assertFalse(fbLoginCommand.equals(1));

        // null -> returns false
        assertFalse(fbLoginCommand.equals(null));

    }
}
```
###### \java\seedu\address\logic\commands\FacebookPostCommandTest.java
``` java
public class FacebookPostCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullMessage_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new FacebookPostCommand(null);
    }

    @Test
    public void equals() {
        FacebookPostCommand fbPostCommand = new FacebookPostCommand("some message");
        // same object -> returns true
        assertTrue(fbPostCommand.equals(fbPostCommand));

        // same values -> returns true
        FacebookPostCommand sameFbPostCommand = new FacebookPostCommand("some message");
        assertTrue(fbPostCommand.equals(sameFbPostCommand));

        // different types -> returns false
        assertFalse(fbPostCommand.equals(1));

        // null -> returns false
        assertFalse(fbPostCommand.equals(null));

        FacebookPostCommand differentPostCommand = new FacebookPostCommand("some other message");
        // different content -> return false
        assertFalse(fbPostCommand.equals(differentPostCommand));

    }

}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
/**
 * Contains tests for {@code RemarkCommand}
 */
public class RemarkCommandTest {

    private static String SOME_REMARK = "some remark";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_addRemark_success() throws Exception {

        Person firstPerson = model.getFilteredPersonList().get(0);
        Person remarkedPerson = new PersonBuilder(firstPerson).withRemark(SOME_REMARK).build();

        RemarkCommand remarkCommand = makeCommand(Index.fromOneBased(1), SOME_REMARK);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, remarkedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(firstPerson, remarkedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_delRemark_success() throws Exception {

        Person firstPerson = model.getFilteredPersonList().get(0);
        Person remarkedPerson = new PersonBuilder(firstPerson).withRemark("").build();

        RemarkCommand remarkCommand = makeCommand(Index.fromOneBased(1), "");

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, remarkedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(firstPerson, remarkedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void equals() throws Exception {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));

    }

    /**
     * Returns a {@code RemarkCommand}
     */
    private RemarkCommand makeCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }

}
```
###### \java\seedu\address\logic\commands\ThemeCommandTest.java
``` java
public class ThemeCommandTest {
    @Test
    public void equals() {
        final ThemeCommand darkThemeCommand = new ThemeCommand(DARK_THEME);
        final ThemeCommand lightThemeCommand = new ThemeCommand(LIGHT_THEME);

        // same object -> true
        assertTrue(darkThemeCommand.equals(darkThemeCommand));

        // same value -> true
        ThemeCommand anotherLightThemeCommand = new ThemeCommand(LIGHT_THEME);
        assertTrue(lightThemeCommand.equals(anotherLightThemeCommand));

        // same value -> true
        ThemeCommand anotherDarkThemeCommand = new ThemeCommand(DARK_THEME);
        assertTrue(darkThemeCommand.equals(anotherDarkThemeCommand));

        // different value -> false
        assertFalse(darkThemeCommand.equals(lightThemeCommand));

        // different type -> false
        assertFalse(darkThemeCommand.equals(1));

        // null -> false
        assertFalse(darkThemeCommand.equals(null));

    }
}
```
###### \java\seedu\address\logic\parser\FacebookPostCommandParserTest.java
``` java
public class FacebookPostCommandParserTest {
    private FacebookPostCommandParser parser = new FacebookPostCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, FacebookPostCommand.EXAMPLE_POST,
                new FacebookPostCommand(FacebookPostCommand.EXAMPLE_POST));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacebookPostCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();
    private final String emptyRemark = "";
    private final String nonEmptyRemark = "some remark";

    @Test
    public void parse_validArgs_success() throws Exception {

        //has index, new remark is non-empty
        assertParseSuccess(parser, "1 r/"  + nonEmptyRemark,
            new RemarkCommand(Index.fromOneBased(1), new Remark(nonEmptyRemark)));

        //has index, new remark is empty i.e. delete remark
        assertParseSuccess(parser, "1 r/" + emptyRemark,
            new RemarkCommand(Index.fromOneBased(1), new Remark(emptyRemark)));
    }

    @Test
    public void parse_missingIndex_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        //no index
        assertParseFailure(parser, nonEmptyRemark , expectedMessage);

    }
}
```
###### \java\seedu\address\logic\parser\ThemeCommandParserTest.java
``` java
public class ThemeCommandParserTest {
    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "dark", new ThemeCommand("dark"));

        //valid theme name (since it's case insensitive
        assertParseSuccess(parser, "LIGHT", new ThemeCommand("LIGHT"));
        assertParseSuccess(parser, "mOrning", new ThemeCommand("mOrning"));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "afternoon",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "dark abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Like fishing");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(remark.equals(1));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different remark -> returns false
        Remark differentRemark = new Remark("Like walking");
        assertFalse(remark.equals(differentRemark));
    }
}
```
