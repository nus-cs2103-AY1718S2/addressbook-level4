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
###### \java\seedu\address\logic\commands\DeleteEventCommandTest.java
``` java
//import static org.junit.Assert.assertEquals;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Before;
//import org.junit.Test;

//import seedu.address.logic.CommandHistory;
//import seedu.address.logic.UndoRedoStack;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class DeleteEventCommandTest {
    private final Person testPerson = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends")
            .withCalendarId("ck6s71ditb731dfepeporbnfb0@group.calendar.google.com")
            .build();

    private Model model;

    @Before
    public void setUp() {
        AddressBook ab = new AddressBook();
        try {
            ab.addPerson(testPerson);
        } catch (DuplicatePersonException e) {
            e.printStackTrace();
        }
        model = new ModelManager(ab, new UserPrefs());
    }

    /*@Test
    public void execute_addEvent_success() throws Exception {
        TestAddEventCommand addEventCommand = new TestAddEventCommand(INDEX_FIRST_PERSON, "Test Event",
                "NUS", "2018-05-01T12:00:00", "2018-05-01T12:30:00",
                "Test add event command");
        addEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        addEventCommand.execute();
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_PERSON, "Test Event");
        deleteEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = DeleteEventCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = deleteEventCommand.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }*/

}
```
###### \java\seedu\address\logic\commands\LockCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code LockCommand}.
 */
public class LockCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        LockCommand firstLockCommand = new LockCommand();
        LockCommand secondLockCommand = new LockCommand();

        // same object -> returns true
        assertTrue(firstLockCommand.equals(firstLockCommand));

        // different types -> returns false
        assertFalse(firstLockCommand.equals(1));

        // null -> returns false
        assertFalse(firstLockCommand.equals(null));

    }

    @Test
    public void lockSuccess() {

        LockCommand testLockCommand = new LockCommand();
        testLockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = LockCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = testLockCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }



}
```
###### \java\seedu\address\logic\commands\MyCalendarCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code MyCalendarCommand}.
 */
public class MyCalendarCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        MyCalendarCommand myCalendarCommand = new MyCalendarCommand();

        // same object -> returns true
        assertTrue(myCalendarCommand.equals(myCalendarCommand));

        // different types -> returns false
        assertFalse(myCalendarCommand.equals(1));

        // null -> returns false
        assertFalse(myCalendarCommand.equals(null));

    }

    @Test
    public void viewSuccess() {
        MyCalendarCommand testCommand = new MyCalendarCommand();
        testCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = MyCalendarCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = testCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }
}
```
###### \java\seedu\address\logic\commands\SetPasswordCommandTest.java
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
 * Contains integration tests (interaction with the Model) for {@code SetPasswordCommand}.
 */
public class SetPasswordCommandTest {

    @Test
    public void equals() {

        SetPasswordCommand firstCommand = new SetPasswordCommand();

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));
    }

    @Test
    public void setPasswordFail() {
        //incorrect old password entered.
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LogicManager logicManager = new LogicManager(model);
        LogicManager.setPassword("psw");
        model.setPassword("psw");
        SetPasswordCommand command = new SetPasswordCommand();
        command.setTestMode();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = SetPasswordCommand.MESSAGE_INCORRECT_OLDPASSWORD;
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);

    }

    @Test
    public void setPasswordSuccess() {

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LogicManager logicManager = new LogicManager(model);
        LogicManager.setPassword("admin");
        model.setPassword("admin");
        SetPasswordCommand command = new SetPasswordCommand();
        command.setTestMode();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = SetPasswordCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);

    }
}
```
###### \java\seedu\address\logic\commands\TestAddEventCommandTest.java
``` java
//import static org.junit.Assert.assertEquals;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Before;
//import org.junit.Test;
//import seedu.address.logic.CommandHistory;
//import seedu.address.logic.UndoRedoStack;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code TestAddEventCommand}.
 */
public class TestAddEventCommandTest {
    private final Person testPerson = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends")
            .withCalendarId("ck6s71ditb731dfepeporbnfb0@group.calendar.google.com")
            .build();

    private Model model;

    @Before
    public void setUp() {
        AddressBook ab = new AddressBook();
        try {
            ab.addPerson(testPerson);
        } catch (DuplicatePersonException e) {
            e.printStackTrace();
        }
        model = new ModelManager(ab, new UserPrefs());
    }

    /*@Test
    public void execute_addEvent_success() throws Exception {
        TestAddEventCommand command = new TestAddEventCommand(INDEX_FIRST_PERSON, "Test Event",
                "NUS", "2018-05-01T12:00:00", "2018-05-01T12:30:00",
                "Test add event command");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = TestAddEventCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }*/
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
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addEvent() throws Exception {
        assertTrue(parser.parseCommand(TestAddEventCommand.COMMAND_WORD
                + " 1 title/Project loca/NUS, Singapore stime/2017-03-19T08:00:00 "
                + "etime/2017-03-19T10:00:00 descrip/discuss")
                instanceof TestAddEventCommand);
    }

    @Test
    public void parseCommand_lock() throws Exception {
        assertTrue(parser.parseCommand(LockCommand.COMMAND_WORD) instanceof LockCommand);
    }

    @Test
    public void parseCommand_unlock() throws Exception {
        assertTrue(parser.parseCommand(UnlockCommand.COMMAND_WORD) instanceof UnlockCommand);
    }

    @Test
    public void parseCommand_setPasswrod() throws Exception {
        assertTrue(parser.parseCommand(SetPasswordCommand.COMMAND_WORD) instanceof SetPasswordCommand);
    }

    @Test
    public void parseCommand_todoList() throws Exception {
        assertTrue(parser.parseCommand(TodoListCommand.COMMAND_WORD) instanceof TodoListCommand);
    }

    @Test
    public void parseCommand_myCalendar() throws Exception {
        assertTrue(parser.parseCommand(MyCalendarCommand.COMMAND_WORD) instanceof MyCalendarCommand);
    }

    @Test
    public void parseCommand_addPhoto() throws Exception {
        AddPhotoCommand command = (AddPhotoCommand) parser.parseCommand(
                AddPhotoCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new AddPhotoCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_authen() throws Exception {
        assertTrue(parser.parseCommand(AuthenCommand.COMMAND_WORD) instanceof AuthenCommand);
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
###### \java\seedu\address\logic\parser\LockCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LockCommand;

/**
 * Test scope: similar to {@code UnlockCommandParserTest}.
 * @see UnlockCommandParserTest
 */
public class LockCommandParserTest {

    private LockCommandParser parser = new LockCommandParser();

    @Test
    public void parse_invalidArgs() {
        assertParseFailure(parser, " 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLockCommand() {
        assertParseSuccess(parser, "   ", new LockCommand());
    }
}
```
###### \java\seedu\address\logic\parser\SetPasswordCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SetPasswordCommand;


/**
 * Test scope: similar to {@code SetPasswordCommandParserTest}.
 * @see SetPasswordCommandParserTest
 */
public class SetPasswordCommandParserTest {

    private SetPasswordCommandParser parser = new SetPasswordCommandParser();

    @Test
    public void parse_invalidArgs() {
        // no agrs provided command
        assertParseFailure(parser, " 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetPasswordCommand.MESSAGE_USAGE));

        //only old password provided
        assertParseFailure(parser, " qqq  aaa", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetPasswordCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSetPasswordCommand() {
        assertParseSuccess(parser, " ",
                new SetPasswordCommand());
    }
}
```
###### \java\seedu\address\logic\parser\TestAddEventCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ENDTIME;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_STARTTIME;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_ENDTIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_STARTTIME;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ENDTIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_STARTTIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.TestAddEventCommand;

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
###### \java\seedu\address\logic\parser\UnlockCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UnlockCommand;

/**
 * Test scope: similar to {@code UnlockCommandParserTest}.
 * @see UnlockCommandParserTest
 */
public class UnlockCommandParserTest {

    private UnlockCommandParser parser = new UnlockCommandParser();

    @Test
    public void parse_invalidArgs() {
        assertParseFailure(parser, " 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLockCommand() {
        assertParseSuccess(parser, "   ", new UnlockCommand());
    }
}
```
###### \java\seedu\address\model\listevent\ListEventTest.java
``` java
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.api.client.util.DateTime;

public class ListEventTest {

    @Test
    public void construct_success() {
        DateTime startTime = new DateTime("2018-04-30T18:00:00+08:00");
        ListEvent listEvent = new ListEvent("Test Event", "NUS", startTime);
        assertEquals("Test Event", listEvent.getTitle());
        assertEquals("NUS", listEvent.getLocation());
        assertEquals(startTime, listEvent.getStartTime());
        assertEquals("EVENT: Test Event  ||   LOCATION: NUS  ||   START AT: " + startTime.toString()
                .substring(0, startTime.toString().lastIndexOf("+")).replaceAll("T", " "),
                listEvent.toString());
    }
}
```
###### \java\seedu\address\model\person\CalendarIdTest.java
``` java
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CalendarIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CalendarId(null));
    }

    @Test
    public void construct_success() {
        CalendarId calendarId = new CalendarId("cid");
        assertEquals(calendarId.getValue(), "cid");
    }
}
```
###### \java\seedu\address\model\photo\PhotoTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhotoTest {

    @Test
    public void construct_success() {
        Photo photo = new Photo("test.jpg");
        assertEquals(photo.getName(), "test.jpg");
        assertEquals(photo.toString(), Photo.DEFAULT_PHOTO_FOLDER + "test.jpg");
    }

    @Test
    public void invalidPhotoName_throwsIllegalArgumentException() {
        assertFalse(Photo.isValidPhotoName(""));
        assertFalse(Photo.isValidPhotoName("test"));
        assertFalse(Photo.isValidPhotoName("test.txt"));
        assertFalse(Photo.isValidPhotoName("test.pdf"));
    }

    @Test
    public void equals() {
        Photo firstPhoto = new Photo("test.jpg");
        Photo secondPhoto = new Photo("test2.jpg");

        // same object -> returns true
        assertTrue(firstPhoto.equals(firstPhoto));

        // different types -> returns false
        assertFalse(firstPhoto.equals(1));

        // null -> returns false
        assertFalse(firstPhoto.equals(null));

        // same value -> returns true
        Photo thirdPhoto = new Photo("test.jpg");
        assertTrue(firstPhoto.equals(thirdPhoto));

        // different value -> returns false
        assertFalse(firstPhoto.equals(secondPhoto));

    }

}
```
###### \java\seedu\address\model\UniquePhotoListTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.photo.Photo;
import seedu.address.model.photo.UniquePhotoList;
import seedu.address.testutil.Assert;

public class UniquePhotoListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Set<Photo> photos;

    private Photo photo1 = new Photo("test1.jpg");
    private Photo photo2 = new Photo("test2.jpg");

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePhotoList uniquePhotoList = new UniquePhotoList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePhotoList.asObservableList().remove(0);
    }

    @Before
    public void setUp() {
        photos = new HashSet<Photo>();
        photos.add(photo1);
        photos.add(photo2);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UniquePhotoList(null));
    }

    @Test
    public void construct_success() {
        UniquePhotoList uniquePhotoList = new UniquePhotoList(photos);
        assertEquals(photos, uniquePhotoList.toSet());
    }

    @Test
    public void setAndAddPhoto_success() throws UniquePhotoList.DuplicatePhotoException {
        UniquePhotoList uniquePhotoList = new UniquePhotoList();
        uniquePhotoList.setPhotos(photos);
        assertEquals(photos, uniquePhotoList.toSet());
        Photo photo3 = new Photo("test3.jpg");
        uniquePhotoList.add(photo3);
        Set<Photo> newPhotos = new HashSet<Photo>();
        newPhotos.add(photo3);
        newPhotos.add(photo2);
        newPhotos.add(photo1);
        UniquePhotoList uniquePhotoList1 = new UniquePhotoList(newPhotos);
        assertTrue(uniquePhotoList.equalsOrderInsensitive(uniquePhotoList1));
    }

    @Test
    public void addDuplicate_throwsDuplicatePhotoException() {
        UniquePhotoList uniquePhotoList = new UniquePhotoList();
        uniquePhotoList.setPhotos(photos);
        Assert.assertThrows(UniquePhotoList.DuplicatePhotoException.class, () -> uniquePhotoList.add(photo1));
    }

    @Test
    public void equals() {
        UniquePhotoList uniquePhotoList = new UniquePhotoList();
        uniquePhotoList.setPhotos(photos);

        Set<Photo> newPhotos = new HashSet<Photo>();
        newPhotos.add(photo2);
        newPhotos.add(photo1);
        UniquePhotoList uniquePhotoList1 = new UniquePhotoList(newPhotos);

        // same object -> returns true
        assertTrue(uniquePhotoList.equals(uniquePhotoList));

        // different types -> returns false
        assertFalse(uniquePhotoList.equals(1));

        // null -> returns false
        assertFalse(uniquePhotoList.equals(null));

        // same value -> returns true
        assertTrue(uniquePhotoList.equals(uniquePhotoList1));

        assertEquals(uniquePhotoList.asObservableList().hashCode(), uniquePhotoList.hashCode());
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
