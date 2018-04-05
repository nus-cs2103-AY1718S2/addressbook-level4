# Kyholmes
###### \src\main\java\seedu\address\logic\commands\AddPatientQueueCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Add patient to visiting queue (registration)
 */
public class AddPatientQueueCommand extends Command {

    public static final String COMMAND_WORD = "addq";
    public static final String COMMAND_ALIAS = "aq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient into vising queue. "
            + "Parameters: "
            + "NAME ";

    public static final String MESSAGE_SUCCESS = "%1$s is registered in the waiting list";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already registered.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This patient cannot be found in the database.";
    private final NameContainsKeywordsPredicate predicate;


    /**
     * Creates an AddCommand to add the specified {@code Patient}
     */
    public AddPatientQueueCommand(NameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            Patient patientAdded = model.addPatientToQueue(predicate);
            return new CommandResult(String.format(MESSAGE_SUCCESS, patientAdded.getName()));
        } catch (DuplicateDataException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PatientNotFoundException e) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPatientQueueCommand // instanceof handles nulls
                && predicate.equals(((AddPatientQueueCommand) other).predicate));
    }
}
```
###### \src\main\java\seedu\address\logic\commands\RemovePatientQueueCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Remove patient from visiting queue (checkout)
 */
public class RemovePatientQueueCommand extends Command {

    public static final String COMMAND_WORD = "removeq";
    public static final String COMMAND_ALIAS = "rq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a patient from the visiting queue."
            + "Parameters: "
            + "QUEUE INDEX NO";

    public static final String MESSAGE_REMOVE_SUCCESS = "%1$s is removed from the waiting list";
    public static final String MESSAGE_PERSON_NOT_FOUND_QUEUE = "Waiting list is empty";

    public RemovePatientQueueCommand() {
    }

    public RemovePatientQueueCommand(Index targetIndex) {
    }

    @Override
    public CommandResult execute() throws CommandException {
        Patient patientToRemove = null;
        try {
            patientToRemove = model.removePatientFromQueue();
            return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, patientToRemove.getName().toString()));
        } catch (PatientNotFoundException e) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND_QUEUE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemovePatientQueueCommand);
    }
}
```
###### \src\main\java\seedu\address\logic\parser\AddPatientQueueCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.AddPatientQueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new AddPatientQueueCommand object
 */
public class AddPatientQueueCommandParser implements Parser<AddPatientQueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPatientQueueCommandParser
     * and returns an AddPatientQueueCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddPatientQueueCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                   String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientQueueCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s");

        return new AddPatientQueueCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \src\main\java\seedu\address\logic\parser\RemovePatientQueueCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemovePatientQueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemovePatientQueueCommand object
 */
public class RemovePatientQueueCommandParser implements Parser<RemovePatientQueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemovePatientQueueCommand
     * and returns an RemovePatientQueueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemovePatientQueueCommand parse(String args) throws ParseException {

        if (args.isEmpty()) {
            return new RemovePatientQueueCommand();
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemovePatientQueueCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePatientQueueCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \src\main\java\seedu\address\model\Imdb.java
``` java
    /**
     * Adds a patient to the visiting queue.
     * Also checks the new patient's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the patient to point to those in {@link #tags}.
     *
     * @throws DuplicatePatientException if an equivalent patient already exists.
     */
    public void addPatientToQueue(int p) throws DuplicatePatientException {
        requireNonNull(p);
        visitingQueue.add(p);
    }

```
###### \src\main\java\seedu\address\model\Imdb.java
``` java
    public int removePatientFromQueue() throws PatientNotFoundException {
        return visitingQueue.removePatient();
    }

```
###### \src\test\java\seedu\address\logic\commands\AddPatientQueueCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

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
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        AddPatientQueueCommand addQueueFirstCommand = new AddPatientQueueCommand(firstPredicate);
        AddPatientQueueCommand addQueueSecondCommand = new AddPatientQueueCommand(secondPredicate);

        // same object -> returns true
        assertTrue(addQueueFirstCommand.equals(addQueueFirstCommand));

        // same values -> returns true
        AddPatientQueueCommand addQueueFirstCommandCopy = new AddPatientQueueCommand(firstPredicate);
        assertTrue(addQueueFirstCommand.equals(addQueueFirstCommandCopy));

        // different types -> returns false
        assertFalse(addQueueFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addQueueFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(addQueueFirstCommand.equals(addQueueSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() throws Exception {
        AddPatientQueueCommand command = prepareCommand(" ");
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddPatientQueueCommand.MESSAGE_PERSON_NOT_FOUND);

        command.execute();
    }

    @Test
    public void execute_patientExist_addSuccessful() throws Exception {
        AddPatientQueueCommand command = prepareCommand("fiona");
        CommandResult commandResult = command.execute();
        assertEquals(String.format(AddPatientQueueCommand.MESSAGE_SUCCESS, TypicalPatients.FIONA.getName()),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() throws Exception {
        prepareForDuplicatePatient();
        AddPatientQueueCommand duplicateCommand = prepareCommand("fiona");
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddPatientQueueCommand.MESSAGE_DUPLICATE_PERSON);
        duplicateCommand.execute();
    }

    /**
     * Parses {@code userInput} into a {@code AddPatientQueueCommand}.
     */
    private AddPatientQueueCommand prepareCommand(String userInput) {
        AddPatientQueueCommand command =
                new AddPatientQueueCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    private void prepareForDuplicatePatient() throws Exception {
        AddPatientQueueCommand duplicateCommand = prepareCommand("fiona");
        duplicateCommand.execute();
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

import java.util.Arrays;

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
import seedu.address.model.patient.exceptions.DuplicatePatientException;
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
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_PERSON_NOT_FOUND_QUEUE);
        removeEmptyQueueCommand.execute();
    }

    @Test
    public void execute_patientExist_removeSuccessful() throws CommandException, DuplicatePatientException,
            PatientNotFoundException {
        RemovePatientQueueCommand command = prepareCommand();
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.FIONA.getName().toString()), commandResult.feedbackToUser);
    }

    private RemovePatientQueueCommand prepareEmptyQueueCommand() {
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommand() throws DuplicatePatientException, PatientNotFoundException {
        model.addPatientToQueue(new NameContainsKeywordsPredicate(Arrays.asList("Fiona")));
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
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
