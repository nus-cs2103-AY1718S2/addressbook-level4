# dannyngmx94
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }
}
```
###### \java\seedu\address\logic\commands\FilterCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    //private Model missingAttributesModel = new ModelManager(getMissingAttributesAddressBook(), new UserPrefs());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equalsTest() {
        KeywordPredicate firstPredicate = new KeywordPredicate("first");
        KeywordPredicate secondPredicate = new KeywordPredicate("second");

        FilterCommand firstCommand = new FilterCommand(firstPredicate);
        FilterCommand secondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));
        // same values -> returns true
        FilterCommand firstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(firstCommand.equals(firstCommandCopy));
        // different types -> returns false
        assertFalse(firstCommand.equals("A String"));
        // null -> returns false
        assertFalse(firstCommand.equals(null));
        // different input list -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_noMissingFields_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        //special prepare command/assert command success using the typical persons model
        FilterCommand command = new FilterCommand(new KeywordPredicate("biology"));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    @Test
    public void execute_oneField_twoPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FilterCommand command = prepareCommand("chinese");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(GEORGE, HENRY));
        command = prepareCommand("chemistry");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL));
    }

    /**
     * Parses {@code userInput} into a {@code FilterCommand}.
     */
    private FilterCommand prepareCommand(String userInput) {
        FilterCommand command = new FilterCommand(new KeywordPredicate(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FilterCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult result = command.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\FilterCommandParserTest.java
``` java
public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new KeywordPredicate("Alice"));
        assertParseSuccess(parser, "Alice", expectedFilterCommand);

        // multiple whitespaces before and after keyword
        assertParseSuccess(parser, " \n Alice \n ", expectedFilterCommand);
    }

}
```
