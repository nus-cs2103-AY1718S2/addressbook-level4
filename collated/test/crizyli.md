# crizyli
###### \java\seedu\address\logic\commands\AddPhotoCommandTest.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddPhotoCommand}.
 */
public class AddPhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void addPhotoSuccess() throws Exception {
        Person personToAddPhoto = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON);
        Person newPerson = new PersonBuilder(personToAddPhoto).build();
        newPerson.setPhotoName("DefaultPerson.png");

        String expectedMessage = AddPhotoCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), newPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code AddPhotoCommand} with the parameter {@code index}.
     */
    private AddPhotoCommand prepareCommand(Index index) {
        AddPhotoCommand addPhotoCommand = new AddPhotoCommand(index);
        addPhotoCommand.setTestMode();
        addPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPhotoCommand;
    }
}
```
###### \java\seedu\address\logic\commands\TodoListCommandTest.java
``` java

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;
//import seedu.address.logic.CommandHistory;
//import seedu.address.logic.UndoRedoStack;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code TodoListCommand}.
 */
public class TodoListCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        TodoListCommand todoListCommand = new TodoListCommand();

        // same object -> returns true
        assertTrue(todoListCommand.equals(todoListCommand));

        // different types -> returns false
        assertFalse(todoListCommand.equals(1));

        // null -> returns false
        assertFalse(todoListCommand.equals(null));
    }

    /*@Test
    public void showSuccess() {
        TodoListCommand command = new TodoListCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = TodoListCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }*/
}
```
###### \java\seedu\address\logic\commands\UnlockCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code UnlockCommand}.
 */
public class UnlockCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        UnlockCommand firstUnlockCommand = new UnlockCommand();

        // same object -> returns true
        assertTrue(firstUnlockCommand.equals(firstUnlockCommand));

        // different types -> returns false
        assertFalse(firstUnlockCommand.equals(1));

        // null -> returns false
        assertFalse(firstUnlockCommand.equals(null));
    }

    @Test
    public void unlockSuccess() {
        model.setPassword("admin");
        LogicManager logicManager = new LogicManager(model);
        LockCommand testLockCommand = new LockCommand();
        testLockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        testLockCommand.execute();
        UnlockCommand testUnlockCommand = new UnlockCommand();
        testUnlockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        testUnlockCommand.setTestMode();
        String expectedMessage = UnlockCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = testUnlockCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void unlockFail() {
        model.setPassword("qwer");
        LogicManager logicManager = new LogicManager(model);
        LockCommand testLockCommand = new LockCommand();
        testLockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        testLockCommand.execute();
        UnlockCommand testUnlockCommand = new UnlockCommand();
        testUnlockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        testUnlockCommand.setTestMode();
        String expectedMessage = UnlockCommand.MESSAGE_INCORRECT_PASSWORD;
        CommandResult commandResult = testUnlockCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

}
```
###### \java\seedu\address\logic\parser\AddPhotoCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.AddPhotoCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class AddPhotoCommandParserTest {

    private AddPhotoCommandParser parser = new AddPhotoCommandParser();

    @Test
    public void parse_validArgs_returnsAddPhotoCommand() {
        assertParseSuccess(parser, "1", new AddPhotoCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\DeleteEventCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteEventCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class DeleteEventCommandParserTest {

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_invalidArgs() {
        // no agrs provided command
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));

        //only one arg provided
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));

        //illegal value for index
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsDeleteEventCommand() {
        assertParseSuccess(parser, " 1 event",
                new DeleteEventCommand(INDEX_FIRST_PERSON, "event"));
    }
}
```
###### \java\seedu\address\logic\parser\TestAddEventCommandParserTest.java
``` java
public class TestAddEventCommandParserTest {

    private TestAddEventCommandParser parser = new TestAddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        final String expectedTitle = "Test Event";
        final String expectedLocation = "NUS";
        final String expectedStarttime = "2018-05-15T10:00:00";
        final String expectedEndtime = "2018-05-15T12:00:00";
        final String expectedDescription = "A test event.";

        assertParseSuccess(parser, "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, new TestAddEventCommand(INDEX_FIRST_PERSON, expectedTitle,
                expectedLocation, expectedStarttime, expectedEndtime, expectedDescription));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, new TestAddEventCommand(INDEX_FIRST_PERSON, expectedTitle,
                expectedLocation, expectedStarttime, expectedEndtime, expectedDescription));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE);

        //missing title prefix
        assertParseFailure(parser, "1" + VALID_EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, expectedMessage);

        //missing location prefix
        assertParseFailure(parser, "1" + EVENT_TITLE + VALID_EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, expectedMessage);

        //missing starttime prefix
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + VALID_EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, expectedMessage);

        //missing endtime prefix
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + VALID_EVENT_ENDTIME + EVENT_DESCRIPTION, expectedMessage);

        //missing description prefix
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + VALID_EVENT_DESCRIPTION, expectedMessage);

        //all prefix missing
        assertParseFailure(parser, "1" + VALID_EVENT_TITLE + VALID_EVENT_LOCATION + VALID_EVENT_STARTTIME
                + VALID_EVENT_ENDTIME + VALID_EVENT_DESCRIPTION, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid start time
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + INVALID_EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, "Invalid date/time format: " + "2018-04-09T08:00");

        // invalid end time
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + INVALID_EVENT_ENDTIME + EVENT_DESCRIPTION, "Invalid date/time format: " + "2018-04-09T10");

        //non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code calendarId} of the {@code Person} that we are building.
     */
    public PersonBuilder withCalendarId(String calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    /**
     * Sets the {@code photoName} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhotoName(String photoName) {
        this.photoName = photoName;
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java

    /**
     * Sets the {@code Rating} of the {@code Person} that we are building.
     */
    public PersonBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

```
