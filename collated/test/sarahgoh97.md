# sarahgoh97
###### \java\seedu\address\logic\commands\AddCellCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddCellCommand.MESSAGE_ALREADY_IN_CELL;
import static seedu.address.logic.commands.AddCellCommand.MESSAGE_FULL_CELL;
import static seedu.address.logic.commands.AddCellCommand.MESSAGE_NOT_PRISONER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalCells.FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.FULL_CELL;
import static seedu.address.testutil.TypicalCells.INVALID_FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.INVALID_SECOND_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.LAST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class AddCellCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public AddCellCommandTest() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Test
    public void execute_validIndexUnfilteredListValidCellAddress_success() throws Exception {
        Person prisonerToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String cellAddress = LAST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, cellAddress);

        String expectedMessage = String.format(AddCellCommand.MESSAGE_ADD_CELL_SUCCESS,
                prisonerToAdd.getName().toString(), cellAddress);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.addPrisonerToCell(prisonerToAdd, cellAddress);

        assertCommandSuccess(addCellCommand, model, expectedMessage, expectedModel);
        addCellCommand.undo();
    }

    @Test
    public void execute_inValidIndexUnfilteredListValidCellAddress_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String cellAddress = FIRST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(outOfBoundIndex, cellAddress);

        assertCommandFailure(addCellCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void execute_validIndexUnfilteredListInvalidFirstDigitCellAddress_failure() {
        String cellAddress = INVALID_FIRST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model, String.format(AddCellCommand.MESSAGE_NON_EXISTENT_CELL,
                cellAddress));

    }

    @Test
    public void execute_validIndexUnfilteredListInvalidSecondDigitCellAddress_failure() {
        String cellAddress = INVALID_SECOND_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model, String.format(AddCellCommand.MESSAGE_NON_EXISTENT_CELL,
                cellAddress));

    }

    @Test
    public void execute_fullCell_failure() {
        String cellAddress = FULL_CELL.getCellAddress();
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model,
                String.format(MESSAGE_FULL_CELL, cellAddress));

    }

    @Test
    public void execute_personNotPrisoner_failure() {
        Person prisonerToAdd = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        String cellAddress = LAST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_SECOND_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model,
                String.format(MESSAGE_NOT_PRISONER, prisonerToAdd.getName().toString()));

    }

    @Test
    public void execute_personInCell_failure() {
        Person prisonerToAdd = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        String cellAddress = LAST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_THIRD_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model,
                String.format(MESSAGE_ALREADY_IN_CELL,
                        prisonerToAdd.getName().toString(), prisonerToAdd.getCellAddress()));

    }

    @Test
    public void equals() {
        AddCellCommand acFirstCommand = prepareCommand(INDEX_FIRST_PERSON, FIRST_CELL_ADDRESS);
        AddCellCommand acSecondCommand = prepareCommand(INDEX_FIRST_PERSON, LAST_CELL_ADDRESS);
        AddCellCommand acThirdCommand = prepareCommand(INDEX_SECOND_PERSON, FIRST_CELL_ADDRESS);


        // same object -> returns true
        assertTrue(acFirstCommand.equals(acFirstCommand));

        // same values -> returns true
        AddCellCommand copy = prepareCommand(INDEX_FIRST_PERSON, FIRST_CELL_ADDRESS);
        assertTrue(acFirstCommand.equals(copy));

        // different types -> returns false
        assertFalse(acFirstCommand.equals(1));

        // null -> returns false
        assertFalse(acFirstCommand.equals(null));

        // different cell -> returns false
        assertFalse(acFirstCommand.equals(acSecondCommand));

        //different person/index -> returns false
        assertFalse(acFirstCommand.equals(acThirdCommand));
    }

    @Test
    public void executeUndoRedo() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, FIRST_CELL_ADDRESS);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // add cell -> first person added to a cell
        Person prisonerToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        addCellCommand.preprocessUndoableCommand();
        addCellCommand.execute();
        undoRedoStack.push(addCellCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.addPrisonerToCell(prisonerToAdd, FIRST_CELL_ADDRESS);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        addCellCommand.undo();
    }

    private AddCellCommand prepareCommand(Index index, String cellAddress) {
        AddCellCommand addCellCommand = new AddCellCommand(index, cellAddress);
        addCellCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addCellCommand;
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void addPrisonerToCell(Person prisoner, String cellAddress) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePrisonerFromCell(Person prisoner) throws PersonNotFoundException, NotImprisonedException {
            fail("This method should not be called.");
        }

        @Override
        public void deletePrisonerFromCellFromUndo(Person prisoner, String cellAddress) {
            fail("This method should not be called.");
        }

        @Override
        public void addPrisonerToCellFromUndo(Person prisoner, String cellAddress) {
            fail("This method should not be called.");
        }

        @Override
        public void updatePrisonerFromUndo(Person orignal, Person changed) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonListForCell(Predicate <Person> predicate, String cellAddress) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\DeleteCellCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.DeleteCellCommand.MESSAGE_NOT_IMPRISONED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class DeleteCellCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public DeleteCellCommandTest() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        DeleteCellCommand deleteCellCommand = prepareCommand(INDEX_THIRD_PERSON);
        Person prisoner = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        String expectedMessage = String.format(DeleteCellCommand.MESSAGE_DELETE_CELL_SUCCESS,
                prisoner.getName().toString());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.deletePrisonerFromCell(prisoner);

        assertCommandSuccess(deleteCellCommand, model, expectedMessage, expectedModel);
        deleteCellCommand.undo();
        expectedModel.addPrisonerToCellFromUndo(prisoner, prisoner.getCellAddress().toString());
    }

    @Test
    public void execute_indexOfNotImprisonedPrisoner_failure() throws Exception {
        DeleteCellCommand deleteCellCommand = prepareCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(deleteCellCommand, model, MESSAGE_NOT_IMPRISONED);
    }

    @Test
    public void execute_indexOfNotImprisonedGuard_failure() throws Exception {
        DeleteCellCommand deleteCellCommand = prepareCommand(INDEX_SECOND_PERSON);
        assertCommandFailure(deleteCellCommand, model, MESSAGE_NOT_IMPRISONED);
    }

    @Test
    public void execute_invalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCellCommand deleteCellCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(deleteCellCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person prisonerReleased = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        DeleteCellCommand deleteCellCommand = prepareCommand(INDEX_THIRD_PERSON);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // delete -> first person deleted
        deleteCellCommand.execute();
        undoRedoStack.push(deleteCellCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deletePrisonerFromCell(prisonerReleased);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        deleteCellCommand.undo();
    }

    @Test
    public void equals() throws Exception {
        DeleteCellCommand deleteCellFirstCommand = prepareCommand(INDEX_THIRD_PERSON);
        DeleteCellCommand deleteCellSecondCommand = prepareCommand(Index.fromOneBased(5));

        // same object -> returns true
        assertTrue(deleteCellFirstCommand.equals(deleteCellFirstCommand));

        // same values -> returns true
        DeleteCellCommand deleteCellFirstCommandCopy = prepareCommand(INDEX_THIRD_PERSON);
        assertTrue(deleteCellFirstCommand.equals(deleteCellFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteCellFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteCellFirstCommand.equals(deleteCellFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteCellFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteCellFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteCellFirstCommand.equals(deleteCellSecondCommand));
    }

    /**
     * Returns a {@code DeleteCellCommand} with the parameter {@code index}.
     */
    private DeleteCellCommand prepareCommand(Index index) {
        DeleteCellCommand deleteCellCommand = new DeleteCellCommand(index);
        deleteCellCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCellCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ListCellCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ListCellCommand.MESSAGE_LISTCELL_SUCCESS;
import static seedu.address.logic.commands.ListCellCommand.MESSAGE_NON_EXISTENT_CELL;
import static seedu.address.testutil.TypicalCells.FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.FULL_CELL;
import static seedu.address.testutil.TypicalCells.INVALID_FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.INVALID_SECOND_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.LAST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AddressContainsCellPredicate;
import seedu.address.model.person.Person;

public class ListCellCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AddressContainsCellPredicate firstPredicate = new AddressContainsCellPredicate(FIRST_CELL_ADDRESS);
        AddressContainsCellPredicate secondPredicate = new AddressContainsCellPredicate(LAST_CELL_ADDRESS);

        ListCellCommand lcFirstCommand = new ListCellCommand(firstPredicate, FIRST_CELL_ADDRESS);
        ListCellCommand lcSecondCommand = new ListCellCommand(secondPredicate, LAST_CELL_ADDRESS);

        // same object -> returns true
        assertTrue(lcFirstCommand.equals(lcFirstCommand));

        // same values -> returns true
        ListCellCommand lcFirstCommandCopy = new ListCellCommand(firstPredicate, FIRST_CELL_ADDRESS);
        assertTrue(lcFirstCommand.equals(lcFirstCommandCopy));

        // different types -> returns false
        assertFalse(lcFirstCommand.equals(1));

        // null -> returns false
        assertFalse(lcFirstCommand.equals(null));

        // different cell address -> returns false
        assertFalse(lcFirstCommand.equals(lcSecondCommand));
    }

    @Test
    public void execute_validEmptyCellAddress_success() throws Exception {
        ListCellCommand listCellCommand = prepareCommand(FIRST_CELL_ADDRESS);
        String expectedMessage = String.format(MESSAGE_LISTCELL_SUCCESS, FIRST_CELL_ADDRESS);
        assertCommandSuccess(listCellCommand, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_validNonEmptyCellAddress_success() throws Exception {
        ListCellCommand listCellCommand = prepareCommand(FULL_CELL.getCellAddress());
        String expectedMessage = String.format(MESSAGE_LISTCELL_SUCCESS, FULL_CELL.getCellAddress());
        assertCommandSuccess(listCellCommand, expectedMessage, Arrays.asList(ELLE, GEORGE));
    }

    @Test
    public void execute_invalidFirstDigitCellAddress_failure() {
        ListCellCommand listCellCommand = prepareCommand(INVALID_FIRST_CELL_ADDRESS);
        assertCommandFailure(listCellCommand, model, String.format(MESSAGE_NON_EXISTENT_CELL,
                INVALID_FIRST_CELL_ADDRESS));
    }

    @Test
    public void execute_invalidSecondDigitCellAddress_failure() {
        ListCellCommand listCellCommand = prepareCommand(INVALID_SECOND_CELL_ADDRESS);
        assertCommandFailure(listCellCommand, model, String.format(MESSAGE_NON_EXISTENT_CELL,
                INVALID_SECOND_CELL_ADDRESS));
    }

    private ListCellCommand prepareCommand(String cellAddress) {
        ListCellCommand command = new ListCellCommand(new AddressContainsCellPredicate(cellAddress), cellAddress);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ListCellCommand command, String expectedMessage,
                                      List<Person> expectedList) throws Exception {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\ShowCellsCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ShowCellsCommandTest {
    private Model model;
    private Model expectedModel;
    private ShowCellsCommand mapCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        mapCommand = new ShowCellsCommand();
        mapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_typicalMap() {
        assertCommandSuccess(mapCommand, model, String.format(mapCommand.MESSAGE_SUCCESS,
                new ShowCellsCommand().getMapString(model.getAddressBook().getCellList().toString())), expectedModel);

    }
}
```
###### \java\seedu\address\logic\commands\UndoableCommandTest.java
``` java
    @Before
    public void setUp() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
```
###### \java\seedu\address\logic\parser\AddCellCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCells.FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.AddCellCommand;

public class AddCellCommandParserTest {

    private AddCellCommandParser parser = new AddCellCommandParser();

    @Test
    public void parse_validArgs_returnsAddCellCommand() {
        assertParseSuccess(parser, "ac 1 1-1", new AddCellCommand(INDEX_FIRST_PERSON, FIRST_CELL_ADDRESS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "ac 1 sdf32",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCellCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCellAddress_throwsParseException() {
        assertParseFailure(parser, "ac 1 2=1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCellCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addCellCommandWord_returnsAddCellCommand() throws Exception {
        assertTrue(parser.parseCommand(AddCellCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + FIRST_CELL_ADDRESS) instanceof AddCellCommand);
        assertTrue(parser.parseCommand("addcell 3 1-4") instanceof AddCellCommand);
        assertTrue(parser.parseCommand("ac 1 1-1") instanceof AddCellCommand);
    }

    @Test
    public void parseCommand_deleteCellCommandWord_returnsDeleteCellCommand() throws Exception {
        assertTrue(parser.parseCommand(DeleteCellCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased())
                instanceof DeleteCellCommand);
        assertTrue(parser.parseCommand("deletecell 3") instanceof DeleteCellCommand);
        assertTrue(parser.parseCommand("dc 2") instanceof DeleteCellCommand);
    }

    @Test
    public void parseCommand_listCellCommandWord_returnsListCellCommand() throws Exception {
        assertTrue(parser.parseCommand(ListCellCommand.COMMAND_WORD + " " + LAST_CELL_ADDRESS)
                instanceof ListCellCommand);
        assertTrue(parser.parseCommand("listcell 2-2") instanceof ListCellCommand);
        assertTrue(parser.parseCommand("lc 1-1") instanceof ListCellCommand);
    }
```
###### \java\seedu\address\logic\parser\DeleteCellCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteCellCommand;

public class DeleteCellCommandParserTest {

    private DeleteCellCommandParser parser = new DeleteCellCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCellCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "dc", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCellCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\testutil\CellBuilder.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.cell.Cell;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Cell objects.
 */

public class CellBuilder {
    public static final ArrayList<Person> DEFAULT_PRISONERS = new ArrayList<Person>();
    public static final String DEFAULT_CELLADDRESS = "1-1";
    public static final boolean DEFAULT_IS_LAST = false;

    private ArrayList<Person> prisoners;
    private String cellAddress;
    private boolean isLast;

    public CellBuilder() {
        prisoners = new ArrayList<Person>(DEFAULT_PRISONERS);
        cellAddress = new String(DEFAULT_CELLADDRESS);
        isLast = DEFAULT_IS_LAST;
    }

    /**
     * Initialises the CellBuilder with data of {@code cellToCopy}
     */
    public CellBuilder(Cell celltoCopy) {
        prisoners = celltoCopy.getPrisoners();
        cellAddress = celltoCopy.getCellAddress();
        isLast = celltoCopy.getIsLast();
    }

    /**
     * Sets the {@code prisoners} of the {@code Cell} that we are building.
     */
    public CellBuilder withPrisoners(ArrayList<Person> prisoners) {
        this.prisoners = new ArrayList<Person>(prisoners);
        return this;
    }

    /**
     * Sets the {@code prisoner} of the {@code Cell} that we are building.
     */
    public CellBuilder withPrisoner(Person prisoner) {
        prisoners.add(prisoner);
        return this;
    }

    /**
     * Sets the {@code cellAddress} of the {@code cell} that we are building.
     */
    public CellBuilder withCellAddress(String cellAddress) {
        this.cellAddress = cellAddress;
        return this;
    }

    /**
     * Sets the  {@code isLast} of the {@code cell} that we are building.
     */
    public CellBuilder withIsLast(boolean isLast) {
        this.isLast = isLast;
        return this;
    }

    public Cell build() {
        return new Cell(prisoners, cellAddress, isLast);
    }
}
```
###### \java\seedu\address\testutil\TypicalCells.java
``` java
package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.cell.Cell;

/**
 * A utility class containing a list of {@code Cell} objects to be used in tests.
 */
public class TypicalCells {
    public static final Cell EMPTY_CELL = new CellBuilder().build();
    public static final Cell CELL_WITH_PRISONER = new CellBuilder().withCellAddress("1-2").withPrisoner(CARL).build();
    public static final Cell FULL_CELL = new CellBuilder().withCellAddress("1-3").withPrisoner(ELLE)
            .withPrisoner(GEORGE).build();
    public static final Cell LAST_CELL_OF_ROW = new CellBuilder().withCellAddress("1-5").withIsLast(true).build();

    public static final String FIRST_CELL_ADDRESS = "1-1";
    public static final String LAST_CELL_ADDRESS = "3-5";
    public static final String INVALID_FIRST_CELL_ADDRESS = "0-1";
    public static final String INVALID_SECOND_CELL_ADDRESS = "3-6";


    public static List<Cell> getTypicalCells() {
        return new ArrayList<>(Arrays.asList(EMPTY_CELL, LAST_CELL_OF_ROW, CELL_WITH_PRISONER, FULL_CELL));
    }
}
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that the total number of people and sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarChangedExceptSaveLocation(int expectedNumberOfPeople) {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        String expectedNumberOfPeopleStatus = expectedNumberOfPeople + " person(s) total.";
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertEquals(expectedNumberOfPeopleStatus, handle.getNumberOfPeopleStatus());
        assertFalse(handle.isSaveLocationChanged());
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Returns a new model that starts with unfiltered list
     */
    protected Model getNewModel() {
        return testApp.getNewModel();
    }
}
```
