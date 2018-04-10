# Kyholmes-test
###### \src\main\java\seedu\address\ui\PatientAppointmentPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AppointmentChangedEvent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;

/**
 * Panel containing the list of patient's appointments.
 */
public class PatientAppointmentPanel extends UiPart<Region> {
    private static final String FXML = "PatientAppointmentPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PatientAppointmentPanel.class);

    @FXML
    private ListView<AppointmentCard> pastAppointmentCardListView;

    @FXML
    private ListView<AppointmentCard> upcomingAppointmentCardListView;

    public PatientAppointmentPanel(ObservableList<Appointment> pastAppointmentList,
                                   ObservableList<Appointment> upcomingAppointmentList) {
        super(FXML);
        setConnections(pastAppointmentList, upcomingAppointmentList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Appointment> pastAppointmentList,
                                ObservableList<Appointment> upcomingAppointmentList) {

        int pastAppointmentListSize = pastAppointmentList.size();
        ObservableList<AppointmentCard> pastMappedList = EasyBind.map(
                pastAppointmentList, (appointment -> new AppointmentCard(appointment,
                        pastAppointmentList.indexOf(appointment) + 1)));
        pastAppointmentCardListView.setItems(pastMappedList);
        pastAppointmentCardListView.setCellFactory(listView -> new AppointmentListViewCell());
        ObservableList<AppointmentCard> upcomingMappedList = EasyBind.map(
                upcomingAppointmentList, (appointment -> new AppointmentCard(appointment,
                        pastAppointmentListSize + upcomingAppointmentList.indexOf(appointment) + 1)));
        upcomingAppointmentCardListView.setItems(upcomingMappedList);
        upcomingAppointmentCardListView.setCellFactory(listView-> new AppointmentListViewCell());
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AppointmentChangedEvent ace) throws ParseException {
        try {
            setConnections(ace.data.getPastAppointmentList(), ace.data.getUpcomingAppointmentList());
        } catch (Exception e) {
            throw new ParseException("List is not in observable list");
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<AppointmentCard> {

        @Override
        protected void updateItem(AppointmentCard appointmentCard, boolean empty) {
            super.updateItem(appointmentCard, empty);

            if (empty || appointmentCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(appointmentCard.getRoot());
            }
        }
    }
}
```
###### \src\test\java\seedu\address\logic\commands\AddAppointmentCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AddAppointmentCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullObject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null, null, null);
    }
}
```
###### \src\test\java\seedu\address\logic\commands\AddPatientQueueCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPatients;

public class AddPatientQueueCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullObject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPatientQueueCommand(null);
    }

    @Test
    public void equals() throws IllegalValueException {
        Index firstIndex = ParserUtil.parseIndex("1");
        Index secondIndex = ParserUtil.parseIndex("2");

        AddPatientQueueCommand addQueueFirstCommand = new AddPatientQueueCommand(firstIndex);
        AddPatientQueueCommand addQueueSecondCommand = new AddPatientQueueCommand(secondIndex);

        // same object -> returns true
        assertTrue(addQueueFirstCommand.equals(addQueueFirstCommand));

        // same values -> returns true
        AddPatientQueueCommand addQueueFirstCommandCopy = new AddPatientQueueCommand(firstIndex);
        assertTrue(addQueueFirstCommand.equals(addQueueFirstCommandCopy));

        // different types -> returns false
        assertFalse(addQueueFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addQueueFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(addQueueFirstCommand.equals(addQueueSecondCommand));
    }

    @Test
    public void execute_patientExist_addSuccessful() throws Exception {
        AddPatientQueueCommand command = prepareCommand("6");
        CommandResult commandResult = command.execute();
        assertEquals(String.format(AddPatientQueueCommand.MESSAGE_SUCCESS, TypicalPatients.FIONA.getName()),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() throws Exception {
        prepareForDuplicatePatient();
        AddPatientQueueCommand duplicateCommand = prepareCommand("6");
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddPatientQueueCommand.MESSAGE_DUPLICATE_PERSON);
        duplicateCommand.execute();
    }

    /**
     * Parses {@code userInput} into a {@code AddPatientQueueCommand}.
     */
    private AddPatientQueueCommand prepareCommand(String userInput) throws IllegalValueException {
        AddPatientQueueCommand command =
                new AddPatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    private void prepareForDuplicatePatient() throws Exception {
        AddPatientQueueCommand duplicateCommand = prepareCommand("6");
        duplicateCommand.execute();
    }
}
```
###### \src\test\java\seedu\address\logic\commands\DeleteAppointmentCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DeleteAppointmentCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class DeleteAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws IllegalValueException {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));

        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        Index firstIndex = ParserUtil.parseIndex("1");

        DeleteAppointmentCommand deleteAppointmentFirstCommand =
                new DeleteAppointmentCommand(firstPredicate, firstIndex);

        DeleteAppointmentCommand deleteAppointmentSecondCommand =
                new DeleteAppointmentCommand(secondPredicate, firstIndex);

        //same object -> return true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        //same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy =
                new DeleteAppointmentCommand(firstPredicate, firstIndex);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        //different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(1));

        //null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        //different pattern -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() throws Exception {
        DeleteAppointmentCommand command = prepareCommand("  2");
        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteAppointmentCommand.MESSAGE_PERSON_NOT_FOUND);

        command.execute();
    }

    @Test
    public void execute_patientExist_deleteSuccessful() throws Exception {
        DeleteAppointmentCommand command = prepareCommand("fiona 1");
        CommandResult commandResult = command.execute();
        assertEquals(DeleteAppointmentCommand.MESSAGE_DELETE_SUCCESS, commandResult.feedbackToUser);
    }

    private DeleteAppointmentCommand prepareCommand(String userInput) throws ParseException {
        DeleteAppointmentCommand command = new DeleteAppointmentCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \src\test\java\seedu\address\logic\commands\RemovePatientQueueCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.exceptions.PatientNotFoundException;
import seedu.address.testutil.TypicalPatients;

public class RemovePatientQueueCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_emptyQueue_throwsCommandException() throws CommandException {
        RemovePatientQueueCommand removeEmptyQueueCommand = prepareEmptyQueueCommand();
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_QUEUE_EMPTY);
        removeEmptyQueueCommand.execute();
    }

    @Test
    public void execute_patientExist_removeSuccessful() throws CommandException, IllegalValueException,
            PatientNotFoundException {
        RemovePatientQueueCommand command = prepareCommand();
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.ALICE.getName().fullName), commandResult.feedbackToUser);
    }

    @Test
    public void execute_patientExist_removeByIndexSuccessful() throws CommandException,
            IllegalValueException, PatientNotFoundException {
        RemovePatientQueueCommand command = prepareCommandMorePatient("2");
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.BENSON.getName().fullName), commandResult.feedbackToUser);
    }

    @Test
    public void execute_emptyQueueRemoveByIndex_throwsCommandException() throws IllegalValueException,
            CommandException {
        RemovePatientQueueCommand command = prepareCommandEmptyQueueIndex("2");
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_QUEUE_EMPTY);
        command.execute();
    }

    @Test
    public void execute_patientNotExistInQueue_throwsCommandException() throws CommandException,
            IllegalValueException, PatientNotFoundException {
        RemovePatientQueueCommand command = prepareCommandMorePatientNotExist("2");
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_PERSON_NOT_FOUND_QUEUE);
        command.execute();
    }

    private RemovePatientQueueCommand prepareEmptyQueueCommand() {
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private RemovePatientQueueCommand prepareCommandEmptyQueueIndex(String userInput) throws IllegalValueException {
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommand() throws IllegalValueException, PatientNotFoundException {
        model.addPatientToQueue(ParserUtil.parseIndex("1"));
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommandMorePatient(String userInput) throws IllegalValueException,
            PatientNotFoundException {
        model.addPatientToQueue(ParserUtil.parseIndex("1"));
        model.addPatientToQueue(ParserUtil.parseIndex("2"));
        model.addPatientToQueue(ParserUtil.parseIndex("3"));
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommandMorePatientNotExist(String userInput) throws IllegalValueException,
            PatientNotFoundException {
        model.addPatientToQueue(ParserUtil.parseIndex("1"));
        model.addPatientToQueue(ParserUtil.parseIndex("3"));
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void equals() throws Exception {
        RemovePatientQueueCommand removePatientQueueFirstCommand = new RemovePatientQueueCommand();

        //same object -> returns true
        assertTrue(removePatientQueueFirstCommand.equals(removePatientQueueFirstCommand));

        //different types -> return false
        assertFalse(removePatientQueueFirstCommand.equals(1));

        //null -> returns false
        assertFalse(removePatientQueueFirstCommand.equals(null));
    }
}
```
###### \src\test\java\seedu\address\logic\commands\ViewAppointmentCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class ViewAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private ViewAppointmentCommand viewAppointmentCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getImdb(), new UserPrefs());
        viewAppointmentCommand = new ViewAppointmentCommand();
        viewAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showSameList() {
        assertCommandSuccess(viewAppointmentCommand, model, ViewAppointmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        ViewAppointmentCommand viewAppointmentFirstCommand = new ViewAppointmentCommand(firstPredicate);
        ViewAppointmentCommand viewAppointmentSecondCommand = new ViewAppointmentCommand(secondPredicate);

        //same object -> returns true
        assertTrue(viewAppointmentFirstCommand.equals(viewAppointmentFirstCommand));

        //same values -> returns true
        ViewAppointmentCommand viewAppointmentFirstCommandCopy = new ViewAppointmentCommand(firstPredicate);
        assertTrue(viewAppointmentFirstCommand.equals(viewAppointmentFirstCommand));

        //null -> returns false
        assertFalse(viewAppointmentFirstCommand.equals(null));

        //different patient -> returns false
        assertFalse(viewAppointmentFirstCommand.equals(viewAppointmentSecondCommand));
    }

    @Test
    public void execute_patientNotExist_noPersonFound() throws Exception {
        ViewAppointmentCommand command = prepareCommand("a");
        thrown.expect(CommandException.class);
        thrown.expectMessage(ViewAppointmentCommand.MESSAGE_PERSON_NOT_FOUND);

        command.execute();
    }

    /**
     * Parses {@code userInput} into a {@code ViewAppointmentCommand}.
     */
    private ViewAppointmentCommand prepareCommand(String userInput) {
        ViewAppointmentCommand command = new ViewAppointmentCommand(new NameContainsKeywordsPredicate(
                Arrays.asList(userInput.split("\\s"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \src\test\java\seedu\address\logic\parser\AddAppointmentCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAddAppointmentCommand() {
        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(new NameContainsKeywordsPredicate(
                Arrays.asList("Alice", "2/4/2018", "1030")), "2/4/2018", "1030");
        assertParseSuccess(parser, "Alice 2/4/2018 1030", expectedCommand);
        assertParseSuccess(parser, "\n Alice 2/4/2018 1030 \n", expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\logic\parser\AddPatientQueueCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPatientQueueCommand;

public class AddPatientQueueCommandParserTest {
    private AddPatientQueueCommandParser parser = new AddPatientQueueCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsString_throwsParseException() {
        assertParseFailure(parser, "alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsNegativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAddPatientQueueCommand() throws IllegalValueException {
        AddPatientQueueCommand expectedCommand = new AddPatientQueueCommand(ParserUtil.parseIndex("1"));
        assertParseSuccess(parser, "1", expectedCommand);

        assertParseSuccess(parser, "\n 1 \n", expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\logic\parser\DeleteAppointmentCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_empty_throwParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnDeleteAppointmentCommand() throws IllegalValueException {
        DeleteAppointmentCommand expectedCommand = new DeleteAppointmentCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "1")), ParserUtil.parseIndex("1"));
        assertParseSuccess(parser, "Alice 1", expectedCommand);
        assertParseSuccess(parser, "\n Alice 1 \n", expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\logic\parser\ImdbParserTest.java
``` java
    @Test
    public void parseCommand_viewAppointment() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ViewAppointmentCommand command = (ViewAppointmentCommand) parser.parseCommand(
                ViewAppointmentCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new ViewAppointmentCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_viewAppointmentCommandAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ViewAppointmentCommand command = (ViewAppointmentCommand) parser.parseCommand(
                ViewAppointmentCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new ViewAppointmentCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_deleteAppointment() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz", "1");
        DeleteAppointmentCommand command = (DeleteAppointmentCommand) parser.parseCommand(
                DeleteAppointmentCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new DeleteAppointmentCommand(new NameContainsKeywordsPredicate(keywords),
                ParserUtil.parseIndex("1")), command);
    }

    @Test
    public void parseCommand_deleteAppointmentCommandAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        List<String> keywords = Arrays.asList("foo", "bar", "baz", "1");
        DeleteAppointmentCommand command = (DeleteAppointmentCommand) parser.parseCommand(
                DeleteAppointmentCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new DeleteAppointmentCommand(new NameContainsKeywordsPredicate(keywords),
                ParserUtil.parseIndex("1")), command);
    }

    @Test
    public void parseCommand_addPatientQueue() throws Exception {
        LoginManager.authenticate("bob", "password456");
        AddPatientQueueCommand command = (AddPatientQueueCommand) parser.parseCommand(
                AddPatientQueueCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new AddPatientQueueCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_addPatientQueueAlias() throws Exception {
        LoginManager.authenticate("bob", "password456");
        AddPatientQueueCommand command = (AddPatientQueueCommand) parser.parseCommand(
                AddPatientQueueCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new AddPatientQueueCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_removePatientQueue_emptyArgs() throws Exception {
        LoginManager.authenticate("bob", "password456");
        assertTrue(parser.parseCommand(RemovePatientQueueCommand.COMMAND_WORD) instanceof RemovePatientQueueCommand);
        assertTrue(parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_WORD + " ") instanceof RemovePatientQueueCommand);
    }

    @Test
    public void parseCommand_removePatientQueue_byIndex() throws Exception {
        RemovePatientQueueCommand command = (RemovePatientQueueCommand) parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RemovePatientQueueCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_removePatientQueueAlias_emptyArgs() throws Exception {
        LoginManager.authenticate("bob", "password456");
        assertTrue(parser.parseCommand(RemovePatientQueueCommand.COMMAND_ALIAS) instanceof RemovePatientQueueCommand);
        assertTrue(parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_ALIAS + " ") instanceof RemovePatientQueueCommand);
    }

    @Test
    public void parseCommand_removePatientQueueAlias_byIndex() throws Exception {
        RemovePatientQueueCommand command = (RemovePatientQueueCommand) parser.parseCommand(
                RemovePatientQueueCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RemovePatientQueueCommand(INDEX_FIRST_PERSON), command);
    }

```
###### \src\test\java\seedu\address\logic\parser\RemovePatientQueueCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemovePatientQueueCommand;

public class RemovePatientQueueCommandParserTest {
    private RemovePatientQueueCommandParser parser = new RemovePatientQueueCommandParser();

    @Test
    public void parse_validArgs_returnsRemovePatientQueueCommand() throws IllegalValueException {
        assertParseSuccess(parser, "1", new RemovePatientQueueCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgsNegativeValue_throwsParserException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePatientQueueCommand.MESSAGE_USAGE_INDEX));
    }

    @Test
    public void parse_invalidArgsAlphaValue_throwsParserException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePatientQueueCommand.MESSAGE_USAGE_INDEX));
    }
}
```
###### \src\test\java\seedu\address\logic\parser\ViewAppointmentCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class ViewAppointmentCommandParserTest {

    private ViewAppointmentCommandParser parser = new ViewAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsViewAppointmentCommand() {
        ViewAppointmentCommand expectedCommand = new ViewAppointmentCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, "Alice", expectedCommand);
        assertParseSuccess(parser, "\n Alice \n", expectedCommand);
    }
}
```
###### \src\test\java\seedu\address\model\appointment\AppointmentEntryTest.java
``` java
package seedu.address.model.appointment;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AppointmentEntryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AppointmentEntry(null, null));
    }
}
```
###### \src\test\java\seedu\address\model\appointment\AppointmentTest.java
``` java
package seedu.address.model.appointment;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AppointmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null));
    }
}
```
###### \src\test\java\seedu\address\model\appointment\DateTimeTest.java
``` java
package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void test_toString() {
        String date = "3/4/2017";
        String time = "2217";
        String dateTimeString = "3/4/2017 2217";

        DateTime toTest = new DateTime(dateTimeString);

        assertEquals(toTest.toString(), date + " " + time);
    }
}
```
###### \src\test\java\seedu\address\model\ImdbTest.java
``` java
    @Test
    public void getVisitingQueue_modifyQueue_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        imdb.getUniquePatientQueue().remove(0);
    }

    @Test
    public void getAppointmentEntryList_modifyList_throwsUnsupportedOperationException() {
        AppointmentEntry entry = new AppointmentEntry(new Appointment("3/4/2017 1030"),
                "test");
        thrown.expect(UnsupportedOperationException.class);
        imdb.getAppointmentEntryList().add(entry);
    }

    @Test
    public void addPatientToQueue_queueUpdate() throws DuplicatePatientException {
        imdbWithAmyAndBob.addPatientToQueue(1);
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();

        expectedImdb.addPatientToQueue(1);

        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void addPatientToQueue_duplicateIndex() throws DuplicatePatientException {
        imdbWithAmyAndBob.addPatientToQueue(1);

        thrown.expect(DuplicatePatientException.class);

        imdbWithAmyAndBob.addPatientToQueue(1);
    }

    @Test
    public void removePatientFromQueue_queueUpdate() throws DuplicatePatientException, PatientNotFoundException {
        imdbWithAmyAndBob.addPatientToQueue(1);
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();

        expectedImdb.addPatientToQueue(1);

        imdbWithAmyAndBob.removePatientFromQueue();
        expectedImdb.removePatientFromQueue();

        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void removePatientFromQueueByIndex_queueUpdate() throws DuplicatePatientException, PatientNotFoundException {
        imdbWithAmyAndBob.addPatientToQueue(2);
        imdbWithAmyAndBob.addPatientToQueue(1);
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();
        expectedImdb.addPatientToQueue(2);
        expectedImdb.addPatientToQueue(1);
        imdbWithAmyAndBob.removePatientFromQueueByIndex(1);
        expectedImdb.removePatientFromQueueByIndex(1);
        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void removePatientByIndex_notInQueue() throws DuplicatePatientException, PatientNotFoundException {
        imdbWithAmyAndBob.addPatientToQueue(2);
        thrown.expect(PatientNotFoundException.class);
        imdbWithAmyAndBob.removePatientFromQueueByIndex(1);
    }

    @Test
    public void removePatientFromQueue_emptyQueue() throws PatientNotFoundException {
        thrown.expect(PatientNotFoundException.class);
        imdbWithAmyAndBob.removePatientFromQueue();
    }

    @Test
    public void removePatientByIndex_emptyQueue() throws PatientNotFoundException {
        thrown.expect(PatientNotFoundException.class);
        imdbWithAmyAndBob.removePatientFromQueueByIndex(2);
    }

```
###### \src\test\java\seedu\address\model\ImdbTest.java
``` java
        @Override
        public ObservableList<AppointmentEntry> getAppointmentEntryList() {
            return appointments;
        }

        @Override
        public ObservableList<Patient> getUniquePatientQueue() {
            return null;
        }

        @Override
        public ObservableList<Integer> getUniquePatientQueueNo() {
            return visitingQueue;
        }
    }

}
```
###### \src\test\java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void getVisitingQueue_modifyQueue_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getVisitingQueue().remove(0);
    }

```
###### \src\test\java\seedu\address\model\UniqueAppointmentListTest.java
``` java
package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;

public class UniqueAppointmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UniqueAppointmentList listToTest = new UniqueAppointmentList();

    @Test
    public void execute_addAppointment_addSuccessful() throws Exception {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        Appointment appointment = new Appointment("3/4/2018 1130");
        listToTest.add(appointment);
        anotherList.add(appointment);
        assertEquals(anotherList, listToTest);
    }

    @Test
    public void execute_removeAppointment_removeSuccessful() throws Exception {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        Appointment appointment = new Appointment("3/4/2018 1130");
        listToTest.add(appointment);
        anotherList.add(appointment);
        listToTest.remove(ParserUtil.parseIndex("1"));
        anotherList.remove(ParserUtil.parseIndex("1"));
        assertEquals(listToTest, anotherList);
    }

    @Test
    public void execute_duplicateAppointment_throwDUplicateException() throws Exception {
        Appointment appointment = new Appointment("3/4/2018 1130");
        listToTest.add(appointment);
        thrown.expect(UniqueAppointmentList.DuplicatedAppointmentException.class);
        listToTest.add(appointment);
    }

    @Test
    public void execute_setAppointmentList_setSucessful() {
        Set<Appointment> appointments = new HashSet<>(Arrays.asList(new Appointment("3/4/2018 1130")));
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        listToTest.setAppointment(appointments);
        anotherList.setAppointment(appointments);
        assertEquals(listToTest, anotherList);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAppointmentList.asObservableList().remove(0);
    }
}
```
###### \src\test\java\seedu\address\model\UniquePatientVisitingQueueTest.java
``` java
package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

public class UniquePatientVisitingQueueTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private UniquePatientVisitingQueue queueToTest = new UniquePatientVisitingQueue();

    @Test
    public void execute_addPatient_addSuccessful() throws Exception {
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(1);
        queueToTest.add(1);
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_removePatient_removeSuccessful() throws Exception {
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(1);
        queueToTest.add(1);
        queueToTest.removePatient();
        anotherQueue.removePatient();
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_removePatientByIndex_removeSuccessful() throws Exception {
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(2);
        anotherQueue.add(4);
        anotherQueue.add(1);
        queueToTest.add(2);
        queueToTest.add(4);
        queueToTest.add(1);
        anotherQueue.removePatient(4);
        queueToTest.removePatient(4);
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_removeByIndexPatientNotInTheQueue_throwsPatientNotFoundException() throws Exception {
        queueToTest.add(4);
        queueToTest.add(2);
        thrown.expect(PatientNotFoundException.class);
        queueToTest.removePatient(3);
    }

    @Test
    public void execute_duplicatePatient_throwsDuplicateException() throws Exception {
        queueToTest.add(1);

        thrown.expect(DuplicatePatientException.class);

        queueToTest.add(1);
    }

    @Test
    public void execute_removeEmptyQueue_throwsPatientNotFoundException() throws Exception {
        thrown.expect(PatientNotFoundException.class);
        queueToTest.removePatient();
    }

    @Test
    public void execute_removeByIndexEmptyQueue_throwsPatientNotFoundException() throws Exception {
        thrown.expect(PatientNotFoundException.class);
        queueToTest.removePatient(3);
    }

    @Test
    public void execute_setVisitingQueue_setSuccessful() {
        Set<Integer> queueNo = new HashSet<>(Arrays.asList(3, 1, 2));
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        queueToTest.setVisitingQueue(queueNo);
        anotherQueue.setVisitingQueue(queueNo);
        assertEquals(queueToTest, anotherQueue);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePatientVisitingQueue uniquePatientQueue = new UniquePatientVisitingQueue();
        thrown.expect(UnsupportedOperationException.class);
        uniquePatientQueue.asObservableList().remove(0);
    }
}
```
