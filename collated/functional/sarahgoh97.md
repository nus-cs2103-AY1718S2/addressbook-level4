# sarahgoh97
###### \java\seedu\address\commons\events\ui\HideMapEvent.java
``` java
package seedu.address.commons.events.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates an event to hide the map.
 */
public class HideMapEvent extends BaseEvent {

    public final ObservableList<String> list = FXCollections.observableArrayList();

    public HideMapEvent() {
        for (int i = 0; i < 15; i++) {
            list.add("Insufficient Access");
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\AddCellCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cell.exceptions.AlreadyInCellException;
import seedu.address.model.cell.exceptions.FullCellException;
import seedu.address.model.cell.exceptions.NonExistentCellException;
import seedu.address.model.cell.exceptions.NotPrisonerException;
import seedu.address.model.person.Person;

/**
 * Adds a prisoner to a cell in the address book.
 */
public class AddCellCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addcell";
    public static final String COMMAND_ALIAS = "ac";
    public static final int MIN_SECURITY_LEVEL = 2;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a prisoner to the specified cell."
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "CELLADDRESS (block-unit)\n"
            + "Example: " + COMMAND_WORD + " 1 1-1";

    public static final String MESSAGE_ADD_CELL_SUCCESS = "Prisoner %s added to %s.";
    public static final String MESSAGE_FULL_CELL = "Cell %s is already full. Below is the map.";
    public static final String MESSAGE_NON_EXISTENT_CELL = "This cell %s does not exist. Below is the map.";
    public static final String MESSAGE_NOT_PRISONER = "%s is not a prisoner.";
    public static final String MESSAGE_ALREADY_IN_CELL = "%s is already in cell %s";

    public final Index index;

    private Person prisonerToAdd;
    private String cellAddress;

    /**
     * @param index of the person in the filtered person list to edit
     * @param cellAddress cell to be added to
     */
    public AddCellCommand(Index index, String cellAddress) {
        requireNonNull(index);
        requireNonNull(cellAddress);
        this.index = index;
        this.cellAddress = cellAddress;
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        requireNonNull(prisonerToAdd);
        try {
            model.addPrisonerToCell(prisonerToAdd, cellAddress);
        } catch (FullCellException fce) {
            throw new CommandException(String.format(MESSAGE_FULL_CELL,
                    cellAddress, new ShowCellsCommand().getMapString(
                            model.getAddressBook().getCellList().toString())));
        } catch (NonExistentCellException nece) {
            throw new CommandException(String.format(MESSAGE_NON_EXISTENT_CELL, cellAddress));
        } catch (NotPrisonerException npe) {
            throw new CommandException(String.format(MESSAGE_NOT_PRISONER, prisonerToAdd.getName()));
        } catch (AlreadyInCellException aice) {
            throw new CommandException(String.format(MESSAGE_ALREADY_IN_CELL,
                    prisonerToAdd.getName(), prisonerToAdd.getCellAddress()));
        }
        return new CommandResult(String.format(MESSAGE_ADD_CELL_SUCCESS, prisonerToAdd.getName(), cellAddress));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        prisonerToAdd = lastShownList.get(index.getZeroBased());
    }

    public Person getPrisonerToAdd() {
        return prisonerToAdd;
    }

    public String getCellAddress() {
        return cellAddress;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCellCommand // instanceof handles nulls
                && this.index.equals(((AddCellCommand) other).index) // state check
                && Objects.equals(this.prisonerToAdd, ((AddCellCommand) other).prisonerToAdd)  //prisoner check
                && Objects.equals(this.cellAddress, ((AddCellCommand) other).cellAddress)); //celladdress check
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCellCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cell.exceptions.NotImprisonedException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a prisoner from a cell in the address book.
 */
public class DeleteCellCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletecell";
    public static final String COMMAND_ALIAS = "dc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes prisoners in specified cell.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_DELETE_CELL_SUCCESS = "Prisoner %s has been released.";
    public static final String MESSAGE_NOT_IMPRISONED = "The target person is not imprisoned here";

    public final Index targetIndex;

    private Person prisonerToDelete;
    private String cellAddress;

    /**
     * Creates a deleteCellCommand object
     * @param targetIndex of the person being deleted
     */
    public DeleteCellCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(prisonerToDelete);
        try {
            model.deletePrisonerFromCell(prisonerToDelete);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (NotImprisonedException nie) {
            throw new CommandException(MESSAGE_NOT_IMPRISONED);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_CELL_SUCCESS, prisonerToDelete.getName().toString()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        prisonerToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (prisonerToDelete.getIsInCell()) {
            cellAddress = prisonerToDelete.getCellAddress().toString();
        }
    }

    public Person getPrisonerToDelete() {
        return prisonerToDelete;
    }

    public String getCellAddress() {
        return cellAddress;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCellCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCellCommand) other).targetIndex) // state check
                && Objects.equals(this.prisonerToDelete, ((DeleteCellCommand) other).prisonerToDelete));
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    private static Address getUpdatedAddress(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        if (!updatedAddress.equals(personToEdit.getAddress()) && personToEdit.getIsInCell()) {
            //if address has changed and imprisoned person
            String original = personToEdit.getAddress().toString();
            String newAddress = original.substring(0, original.indexOf("s: ") + 3) + updatedAddress.toString() + "]";
            updatedAddress = new Address(newAddress);
        }
        return updatedAddress;
    }

```
###### \java\seedu\address\logic\commands\ListCellCommand.java
``` java
package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cell.exceptions.NonExistentCellException;
import seedu.address.model.person.Person;

/**
 * Lists all person in specified cell
 */
public class ListCellCommand extends Command {
    public static final String COMMAND_WORD = "listcell";
    public static final String COMMAND_ALIAS = "lc";
    public static final int MIN_SECURITY_LEVEL = 1;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all people in the specified cell."
            + " Parameters: CELLADDRESS\n"
            + "Example: " + COMMAND_WORD + " 1-1";

    public static final String MESSAGE_LISTCELL_SUCCESS = "Listed persons in cell %s";
    public static final String MESSAGE_NON_EXISTENT_CELL = "This cell %s does not exist";
    private final Predicate<Person> predicate;
    private final String cellAddress;

    public ListCellCommand(Predicate<Person> predicate, String cellAddress) {
        this.predicate = predicate;
        this.cellAddress = cellAddress;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.updateFilteredPersonListForCell(predicate, cellAddress);
        } catch (NonExistentCellException nece) {
            throw new CommandException(String.format(MESSAGE_NON_EXISTENT_CELL, cellAddress));
        }
        return new CommandResult(String.format(MESSAGE_LISTCELL_SUCCESS, cellAddress));
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCellCommand // instanceof handles nulls
                && this.predicate.equals(((ListCellCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ShowCellsCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.cell.CellMap;

/**
 * Shows all cells and number of prisoners in each of them to the user.
 */
public class ShowCellsCommand extends Command {

    public static final String COMMAND_WORD = "map";
    public static final String COMMAND_ALIAS = "m";
    public static final int MIN_SECURITY_LEVEL = 1;

    public static final String MESSAGE_SUCCESS = "%s\nShown cells with number of people in them.";


    @Override
    public CommandResult execute() {
        String cells = model.getAddressBook().getCellList().toString();
        String map = getMapString(cells);
        return new CommandResult(String.format(MESSAGE_SUCCESS, map));
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

    public String getMapString(String cells) {
        for (int i = 1; i <= CellMap.MAX_ROW; i++) {
            cells = cells.replace(", " + i + "-1", i + "-1");
        }
        cells = cells.substring(1, cells.length() - 1);

        return cells;
    }
}
```
###### \java\seedu\address\logic\parser\AddCellCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCellCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCellCommand object
 */
public class AddCellCommandParser implements Parser<AddCellCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCellCommand
     * and returns an AddCellCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCellCommand parse (String args) throws ParseException {
        try {
            requireNonNull(args);
            String removePrefix = null;
            String unparsedIndex = null;
            if (args.contains(" ") && args.length() > args.indexOf(" ")) {
                removePrefix = args.substring(args.indexOf(" ") + 1);
                if (removePrefix.contains(" ") && removePrefix.length() > removePrefix.indexOf(" ")) {
                    unparsedIndex = removePrefix.substring(0, removePrefix.indexOf(" "));
                } else {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddCellCommand.MESSAGE_USAGE));
                }
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCellCommand.MESSAGE_USAGE));
            }
            if (removePrefix != null && removePrefix.contains(" ") && unparsedIndex != null) {
                String cellAddress = removePrefix.substring(removePrefix.indexOf(" ") + 1);
                Index index = ParserUtil.parseIndex(unparsedIndex);
                if (cellAddress.matches("\\d+-\\d+")) {
                    return new AddCellCommand(index, cellAddress);
                } else {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddCellCommand.MESSAGE_USAGE));
                }
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCellCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCellCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ShowCellsCommand.COMMAND_WORD:
        case ShowCellsCommand.COMMAND_ALIAS:
            return new ShowCellsCommand();

        case AddCellCommand.COMMAND_WORD:
        case AddCellCommand.COMMAND_ALIAS:
            return new AddCellCommandParser().parse(arguments);

        case DeleteCellCommand.COMMAND_WORD:
        case DeleteCellCommand.COMMAND_ALIAS:
            return new DeleteCellCommandParser().parse(arguments);

        case ListCellCommand.COMMAND_WORD:
        case ListCellCommand.COMMAND_ALIAS:
            return new ListCellCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\DeleteCellCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCellCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given input and gives a new DeleteCellCommand object.
 */
public class DeleteCellCommandParser implements Parser<DeleteCellCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of DeleteCellCommand
     * and returns a DeleteCellCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteCellCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCellCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCellCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
        if (args.contains(PREFIX_ROLE.toString())) {
            throw new ParseException(String.format(EditCommand.MESSAGE_CANNOT_CHANGE_ROLE));
        }
```
###### \java\seedu\address\logic\parser\ListCellCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCellCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsCellPredicate;

/**
 * Parses the given input and gives a new ListCellCommand object.
 */
public class ListCellCommandParser implements Parser<ListCellCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of ListCellCommand
     * and returns a ListCellCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public ListCellCommand parse(String args) throws ParseException {
        args = args.trim();
        if (args.matches("\\d+-\\d+")) {
            return new ListCellCommand(new AddressContainsCellPredicate(args), args);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCellCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
        if (target.getIsInCell()) {
            cells.setPrisonerToCell(target, syncedEditedPerson);
        }
    }

    /**
     * Replaces the given person {@code changed} in the list with {@code original} in the cellMap.
     * This is only done from undo.
     */
    public void updatePrisonerFromUndo(Person changed, Person original) {
        requireAllNonNull(original, changed);

        cells.setPrisonerToCell(changed, original);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
            if (key.getIsInCell() == true) {
                String cellAddress = key.getCellAddress().toString();
                cells.deletePrisonerFromCell(key, cellAddress);
            }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// cell-level operations

    /**
     *
     * @param c is the cell to add to the map
     */
    public void addCell(Cell c) {
        String cellAddress = c.getCellAddress();
        cells.setCell(c, cellAddress);
    }

    /**
     * Adds a prisoner to a cell
     * @param cellAddress to get the correct cell
     * @param prisoner to be added into the cell
     * @throws FullCellException if the cell already has the maximum number of prisoners
     * @throws NonExistentCellException if the cell address is invalid
     * @throws NotPrisonerException if the Person passed is not a prisoner
     */
    public void addPrisonerToCell(String cellAddress, Person prisoner) throws FullCellException,
            NonExistentCellException, NotPrisonerException, AlreadyInCellException {
        requireAllNonNull(prisoner, cellAddress);
        if (!Cell.isValidCellAddress(cellAddress)) {
            throw new NonExistentCellException();
        } else if (!prisoner.getRole().equals(Role.PRISONER)) {
            throw new NotPrisonerException();
        } else if (prisoner.getIsInCell()) {
            throw new AlreadyInCellException();
        } else if (cells.getCell(cellAddress).getNumberOfPrisoners() >= Cell.MAX_SIZE) {
            throw new FullCellException();
        } else {
            Person updatedPrisoner = new Person(prisoner, true, cellAddress);
            updatePrisoner(prisoner, updatedPrisoner);
            addPrisonerToCellPermitted(updatedPrisoner, cellAddress);
        }
    }

    /**
     * Adding prisoner to CellMap once exceptions cleared
     * @param prisoner is the correct person without requiring editing
     * @param cellAddress is the String corresponding to the cell shown on map
     */
    public void addPrisonerToCellPermitted(Person prisoner, String cellAddress) {
        cells.addPrisonerToCell(prisoner, cellAddress);
    }

    /**
     * Deletes prisoner from a specified cell
     */
    public void deletePrisonerFromCell(Person prisoner, String cellAddress) {
        cells.deletePrisonerFromCell(prisoner, cellAddress);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code updatedPrisoner}.
     */
    public void updatePrisoner(Person target, Person updatedPrisoner) {
        persons.setPrisoner(target, updatedPrisoner);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags\n"
                + cells.getCellList() + users.getUserList();
        // TODO: refine later
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Cell> getCellList() {
        return cells.getCellList();
    }
```
###### \java\seedu\address\model\cell\Cell.java
``` java
package seedu.address.model.cell;

import java.util.ArrayList;

import seedu.address.model.person.Person;

/**
 * Represents a cell in the prison.
 * Guarantees: Cells created are all part of the CellMap
 */
public class Cell {

    public static final int MAX_SIZE = 2;
    private final ArrayList<Person> prisoners;
    private String cellAddress;
    private boolean isLast;

    /**
     * Represents a cell in the Prison.
     */
    public Cell(int row, int column) {
        prisoners = new ArrayList<Person>(MAX_SIZE);
        cellAddress = row + "-" + column;
        if (column == CellMap.MAX_COL) {
            isLast = true;
        } else {
            isLast = false;
        }
    }

    /**
     * A copied cell
     */
    public Cell(ArrayList<Person> prisoners, String cellAddress, boolean isLast) {
        this.prisoners = new ArrayList<Person>(prisoners);
        this.cellAddress = cellAddress;
        this.isLast = isLast;
    }

    public static int getCol(String cellAddress) {
        return Integer.parseInt(cellAddress.substring(cellAddress.indexOf("-") + 1));
    }

    public static int getRow(String cellAddress) {
        return Integer.parseInt(cellAddress.substring(0, cellAddress.indexOf("-")));
    }

    public void addPrisoner(Person prisoner) {
        prisoners.add(prisoner);
    }

    public void deletePrisoner(Person prisoner) {
        prisoners.remove(prisoner);
    }

    public ArrayList<Person> getPrisoners() {
        return prisoners;
    }

    public String getCellAddress() {
        return cellAddress;
    }

    public int getNumberOfPrisoners() {
        return prisoners.size();
    }

    public boolean getIsLast() {
        return isLast;
    }

    /**
     * Returns true if a given string is a valid cell.
     */
    public static boolean isValidCellAddress(String test) {
        if (test.matches("\\d+-\\d+")) {
            int row = getRow(test);
            int col = getCol(test);
            return row <= CellMap.MAX_ROW && row > 0
                    && col <= CellMap.MAX_COL && col > 0;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String string = getCellAddress() + " [" + getNumberOfPrisoners() + "]";
        if (isLast) {
            return string + "\n";
        }
        return string;
    }

}
```
###### \java\seedu\address\model\cell\CellMap.java
``` java
package seedu.address.model.cell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * Contains the cells of the prison for the PrisonBook
 * Cells are not allowed to exceed the maximum fixed size of the prison
 */
public class CellMap {
    public static final int MAX_ROW = 3;
    public static final int MAX_COL = 5;
    private Cell[][] cellMap;
    private final ObservableList<Cell> internalList = FXCollections.observableArrayList();

    /**
     * Represents a fixed-sized map of the cells in the prison.
     * Initialised at start of program.
     */
    public CellMap() {
        resetData();
    }

    /**
     * @param cellAddress has to be within boundaries
     * @return Cell from cellAddress
     */
    public Cell getCell(String cellAddress) {
        int row = getCellMapRow(cellAddress);
        int col = getCellMapCol(cellAddress);
        int index = getIndex(row, col);
        return internalList.get(index);
    }

    public void setCells(ObservableList<Cell> cells) {
        for (Cell c: cells) {
            setCell(c, c.getCellAddress());
        }
        internalList.clear();
        internalList.setAll(cells);
    }

    public void setCell(Cell cell, String cellAddress) {
        int row = getCellMapRow(cellAddress);
        int col = getCellMapCol(cellAddress);
        cellMap[row][col] = cell;
        int num = getIndex(row, col);
        if (num >= internalList.size()) {
            internalList.add(cell);
        } else {
            internalList.set(num, cell);
        }
    }

    private int getCellMapRow(String cellAddress) {
        return Cell.getRow(cellAddress) - 1;
    }

    private int getCellMapCol(String cellAddress) {
        return Cell.getCol(cellAddress) - 1;
    }

    /**
     * Adds a prisoner to a specified cell.
     */
    public void addPrisonerToCell(Person prisoner, String cellAddress) {
        int row = getCellMapRow(cellAddress);
        int col = getCellMapCol(cellAddress);
        addPrisonerToCell(prisoner, row, col);
    }

    /**
     * private method called from public method above
     */
    private void addPrisonerToCell(Person prisoner, int row, int col) {
        Cell cell = cellMap[row][col];
        cellMap[row][col].addPrisoner(prisoner);
        int index = getIndex(row, col);
        internalList.set(index, cell);
    }

    private int getIndex(int row, int col) {
        return row * MAX_COL + col;
    }

    /**
     * Removes a prisoner from a specified cell
     */
    public void deletePrisonerFromCell(Person prisoner, String cellAddress) {
        int row = getCellMapRow(cellAddress);
        int col = getCellMapCol(cellAddress);
        deletePrisonerFromCell(prisoner, row, col);
    }

    /**
     * private method called from public method above
     */
    private void deletePrisonerFromCell(Person prisoner, int row, int col) {
        Cell cell = cellMap[row][col];
        cell.deletePrisoner(prisoner);
        int index = getIndex(row, col);
        internalList.set(index, cell);
    }

    /**
     * Sets new edited person to original cell
     * Precondition: target must be imprisoned
     */
    public void setPrisonerToCell(Person target, Person updatedPrisoner) {
        assert(target.getIsInCell());
        assert(updatedPrisoner.getIsInCell());
        String cellAddress = target.getCellAddress().toString();
        deletePrisonerFromCell(target, cellAddress);
        addPrisonerToCell(updatedPrisoner, cellAddress);
    }

    /**
     * For storage purposes
     */
    public ObservableList<Cell> getCellList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Resets the existing data of this {@code CellMap}.
     */
    public void resetData() {
        cellMap = new Cell[MAX_ROW][MAX_COL];
        internalList.clear();
        for (int currRow = 0; currRow < MAX_ROW; currRow++) {
            for (int currCol = 0; currCol < MAX_COL; currCol++) {
                Cell cell = new Cell(currRow + 1, currCol + 1);
                cellMap[currRow][currCol] = cell;
                internalList.add(cell);
            }
        }
    }

    /**
     * Returns a map of the cells and their addresses
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] cArray: cellMap) {
            for (Cell c: cArray) {
                sb.append(c.getCellAddress() + " [" + c.getNumberOfPrisoners() + "] ");
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CellMap // instanceof handles nulls
                && this.toString().equals((other.toString())));
    }
}
```
###### \java\seedu\address\model\cell\exceptions\AlreadyInCellException.java
``` java
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the prisoner being added is already in a cell in the prison.
 */
public class AlreadyInCellException extends IllegalValueException {
    public AlreadyInCellException() {
        super("Prisoner already in prison.");
    }
}
```
###### \java\seedu\address\model\cell\exceptions\FullCellException.java
``` java
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the operation will be adding a prisoner to a full cell.
 */
public class FullCellException extends IllegalValueException {
    public FullCellException() {
        super("Invalid cell. Cell is already full.");
    }
}
```
###### \java\seedu\address\model\cell\exceptions\NonExistentCellException.java
``` java
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the cell indicated is not within boundaries
 */
public class NonExistentCellException extends IllegalValueException {
    public NonExistentCellException() {
        super("No such cell exists.");
    }
}
```
###### \java\seedu\address\model\cell\exceptions\NotImprisonedException.java
``` java
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the operation will be removing a person not in jail from the jail.
 */
public class NotImprisonedException extends IllegalValueException {
    public NotImprisonedException() {
        super("This person is not imprisoned in this prison.");
    }
}
```
###### \java\seedu\address\model\cell\exceptions\NotPrisonerException.java
``` java
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the person added to a cell is not a prisoner
 */
public class NotPrisonerException extends IllegalValueException {
    public NotPrisonerException() {
        super("Invalid person to add, not a prisoner.");
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds given prisoner into a cell */
    void addPrisonerToCell(Person prisoner, String cellAddress)
            throws FullCellException, NonExistentCellException,
            NotPrisonerException, AlreadyInCellException;

    /**Deletes given prisoner from a cell from DeleteCellCommand */
    void deletePrisonerFromCell(Person prisoner) throws PersonNotFoundException, NotImprisonedException;

    /**Adds given prisoner back into a cell from undo command */
    void addPrisonerToCellFromUndo(Person prisoner, String cellAddress);

    /** Deletes given prisoner from a cell from undo command*/
    void deletePrisonerFromCellFromUndo(Person prisoner, String cellAddress);

    /** Updates given prisoner who changed from undo command*/
    void updatePrisonerFromUndo(Person changed, Person original);
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the filter of the filtered person list to show only people in the cell.
     */
    void updateFilteredPersonListForCell(Predicate<Person> predicate, String cellAddress)
            throws NonExistentCellException;

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updatePrisonerFromUndo(Person changed, Person original) {
        addressBook.updatePrisonerFromUndo(changed, original);
        indicateAddressBookChanged();
    }

    @Override
    public void addPrisonerToCell(Person prisoner, String cellAddress)
            throws FullCellException, NonExistentCellException,
            NotPrisonerException, AlreadyInCellException {
        requireAllNonNull(prisoner, cellAddress);
        addressBook.addPrisonerToCell(cellAddress, prisoner);
        indicateAddressBookChanged();
    }

    /**
     * Deletes a prisoner from a cell from DeleteCellCommand
      * @param prisoner to be removed from cell
     * @throws PersonNotFoundException if prisoner does not exist
     * @throws NotImprisonedException if person chosen is not imprisoned here
     */
    @Override
    public void deletePrisonerFromCell(Person prisoner) throws PersonNotFoundException, NotImprisonedException {
        requireNonNull(prisoner);
        if (!filteredPersons.contains(prisoner)) {
            throw new PersonNotFoundException();
        } else {
            String cellAddress = prisoner.getAddress().toString();
            if (prisoner.getIsInCell()) {
                cellAddress = cellAddress.substring(0, cellAddress.indexOf(" "));
                addressBook.deletePrisonerFromCell(prisoner, cellAddress);
                Person freedPrisoner = new Person(prisoner, false);
                addressBook.updatePrisoner(prisoner, freedPrisoner);
            } else {
                throw new NotImprisonedException();
            }
        }
        indicateAddressBookChanged();
    }

    /* this is to undo AddCellCommand*/
    @Override
    public void deletePrisonerFromCellFromUndo(Person prisoner, String cellAddress) {
        requireAllNonNull(prisoner, cellAddress);
        Person updatedPrisoner = new Person(prisoner, true, cellAddress);
        addressBook.deletePrisonerFromCell(updatedPrisoner, cellAddress);
        indicateAddressBookChanged();
    }

    /* this is to undo DeleteCellCommand*/
    @Override
    public void addPrisonerToCellFromUndo(Person prisoner, String cellAddress) {
        requireAllNonNull(prisoner, cellAddress);
        addressBook.addPrisonerToCellPermitted(prisoner, cellAddress);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /* This is for ListCellCommand */
    @Override
    public void updateFilteredPersonListForCell(Predicate<Person> predicate, String cellAddress)
            throws NonExistentCellException {
        requireNonNull(predicate);
        requireNonNull(cellAddress);
        if (Cell.isValidCellAddress(cellAddress)) {
            filteredPersons.setPredicate(predicate);
        } else {
            throw new NonExistentCellException();
        }
    }
```
###### \java\seedu\address\model\person\AddressContainsCellPredicate.java
``` java
package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that the person is in the cell
 */
public class AddressContainsCellPredicate implements Predicate<Person> {
    private final String cellAddress;

    public AddressContainsCellPredicate(String cellAddress) {
        this.cellAddress = cellAddress;
    }

    @Override
    public boolean test(Person person) {
        if (person.getIsInCell()) {
            if (person.getCellAddress().toString().equals(cellAddress)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsCellPredicate // instanceof handles nulls
                && this.cellAddress.equals(((AddressContainsCellPredicate) other).cellAddress)); // state check
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    //only called if prisoner isInCell
    public Address getCellAddress() {
        assert(isInCell);
        String cellAddress = address.value.substring(0, address.value.indexOf("[") - 1);
        return new Address(cellAddress);
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Replaces the person {@code target} in the list with {@code updatedPrisoner}.
     */
    public void setPrisoner(Person target, Person updatedPrisoner) {
        int index = internalList.indexOf(target);
        internalList.set(index, updatedPrisoner);
    }
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the cells list.
     * This list will not contain any duplicate cells.
     */
    ObservableList<Cell> getCellList();

```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    public static Cell[] getSampleCells() {
        Person alex = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("1-1 [Old address: Blk 30 Geylang Street 29, #06-40]"), new Role("p"),
                getTagSet("ChickenAllergy"), true);
        Person charlotte = new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("1-1 [Old address: Blk 11 Ang Mo Kio Street 74, #11-04]"), new Role("p"),
                getTagSet("ViolentTendencies"), true);
        Person sarah = new Person(new Name("Sarah Goh"), new Phone("90288173"), new Email("gohst@example.com"),
                new Address("3-5 [Old address: Tung Ann Association Building 141 Cecil Street #03-02]"),
                new Role("p"), getTagSet(), true);

        return new Cell[] {
            new Cell(getPrisoners(alex, charlotte), "1-1", false),
            new Cell(1, 2), new Cell(1, 3), new Cell(1, 4), new Cell(1,  5),
            new Cell(2, 1), new Cell(2, 2), new Cell(2, 3), new Cell(2, 4), new Cell(2,  5),
            new Cell(3, 1), new Cell(3, 2), new Cell(3, 3), new Cell(3, 4),
            new Cell(getPrisoners(sarah), "3-5", true)
        };
    }

    public static ArrayList<Person> getPrisoners(Person... prisoners) {
        ArrayList<Person> list = new ArrayList<Person>();
        for (Person p: prisoners) {
            list.add(p);
        }
        return list;
    }

```
###### \java\seedu\address\storage\XmlAdaptedCell.java
``` java
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cell.Cell;
import seedu.address.model.person.Person;

/**
 * JAXB-friendly version of the Cell.
 */
public class XmlAdaptedCell {

    public static final String INVALID_CELL = "This cell does not exist!";

    @XmlElement (required = true)
    private String cellAddress;
    @XmlElement (required = true)
    private List<XmlAdaptedPerson> prisoners = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedCell.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCell() {}

    /**
     * Constructs a {@code XmlAdaptedCell} with the given Cell.
     */
    public XmlAdaptedCell(String cellAddress, List<XmlAdaptedPerson> prisoners) {
        this.cellAddress = cellAddress;
        if (prisoners != null) {
            this.prisoners = new ArrayList<>(prisoners);
        }
    }

    /**
     * Converts a given Cell into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedCell(Cell source) {
        cellAddress = source.getCellAddress();
        for (Person person: source.getPrisoners()) {
            prisoners.add(new XmlAdaptedPerson(person));
        }
    }

    /**
     * Converts this jaxb-friendly adapted Cell object into the model's Cell object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted cell
     */
    public Cell toModelType() throws IllegalValueException {
        if (this.cellAddress == null) {
            throw new IllegalValueException(INVALID_CELL);
        }
        if (!Cell.isValidCellAddress(this.cellAddress)) {
            throw new IllegalValueException(INVALID_CELL);
        }
        int row = Cell.getRow(cellAddress);
        int col = Cell.getCol(cellAddress);
        Cell cell = new Cell(row, col);

        for (XmlAdaptedPerson person : prisoners) {
            cell.addPrisoner(person.toModelType());
        }
        return cell;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCell)) {
            return false;
        }

        XmlAdaptedCell otherCell = (XmlAdaptedCell) other;
        return Objects.equals(cellAddress, otherCell.cellAddress)
                && prisoners.equals(otherCell.prisoners);
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        mapPanel = new MapPanel();
        mapPanelPlaceholder.getChildren().add(mapPanel.getRoot());
```
###### \java\seedu\address\ui\MapPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.HideMapEvent;
import seedu.address.model.cell.Cell;


/**
 * The cellMap of the App.
 */
public class MapPanel extends UiPart<Region> {

    private static final String FXML = "MapPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(MapPanel.class);

    @FXML
    private Label cellAddress11;
    @FXML
    private Label cellAddress12;
    @FXML
    private Label cellAddress13;
    @FXML
    private Label cellAddress14;
    @FXML
    private Label cellAddress15;
    @FXML
    private Label cellAddress21;
    @FXML
    private Label cellAddress22;
    @FXML
    private Label cellAddress23;
    @FXML
    private Label cellAddress24;
    @FXML
    private Label cellAddress25;
    @FXML
    private Label cellAddress31;
    @FXML
    private Label cellAddress32;
    @FXML
    private Label cellAddress33;
    @FXML
    private Label cellAddress34;
    @FXML
    private Label cellAddress35;

    public MapPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<String> list) {
        cellAddress11.setText(list.get(0));
        cellAddress12.setText(list.get(1));
        cellAddress13.setText(list.get(2));
        cellAddress14.setText(list.get(3));
        cellAddress15.setText(list.get(4));
        cellAddress21.setText(list.get(5));
        cellAddress22.setText(list.get(6));
        cellAddress23.setText(list.get(7));
        cellAddress24.setText(list.get(8));
        cellAddress25.setText(list.get(9));
        cellAddress31.setText(list.get(10));
        cellAddress32.setText(list.get(11));
        cellAddress33.setText(list.get(12));
        cellAddress34.setText(list.get(13));
        cellAddress35.setText(list.get(14));
    }

    private void setStrings(ObservableList<Cell> cellList) {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = 0; i < 15; i++) {
            list.add(Integer.toString(cellList.get(i).getNumberOfPrisoners()));
        }
        setConnections(list);
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Changing map\n"));
        setStrings(abce.data.getCellList());
    }

    @Subscribe
    private void handleHideMapRequestEvent(HideMapEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(event.list);
    }

}
```
###### \resources\view\MapPanel.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>

<GridPane alignment="CENTER" gridLinesVisible="true" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="700.0" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
           <columnConstraints>
               <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
               <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
               <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
               <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
               <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
           </columnConstraints>
           <rowConstraints>
               <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
               <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
               <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
               <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
               <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
               <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
           </rowConstraints>
           <children>
               <Label alignment="CENTER" prefHeight="21.0" prefWidth="143.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="1-5" GridPane.columnIndex="4" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="158.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="1-2" GridPane.columnIndex="1" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="182.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="1-3" GridPane.columnIndex="2" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="224.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="1-4" GridPane.columnIndex="3" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="187.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="3-3" GridPane.columnIndex="2" GridPane.rowIndex="4" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="173.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="2-3" GridPane.columnIndex="2" GridPane.rowIndex="2" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="190.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="2-2" GridPane.columnIndex="1" GridPane.rowIndex="2" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="264.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="3-1" GridPane.rowIndex="4" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="202.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="2-1" GridPane.rowIndex="2" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="163.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="2-5" textAlignment="CENTER" GridPane.columnIndex="4" GridPane.rowIndex="2" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="221.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="3-5" GridPane.columnIndex="4" GridPane.rowIndex="4" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="176.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="3-4" GridPane.columnIndex="3" GridPane.rowIndex="4" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="201.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="2-4" GridPane.columnIndex="3" GridPane.rowIndex="2" />
               <Label alignment="CENTER" prefHeight="62.0" prefWidth="164.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="1-1" />
               <Label fx:id="cellAddress11" alignment="CENTER" prefHeight="21.0" prefWidth="215.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.rowIndex="1" />
               <Label fx:id="cellAddress15" alignment="CENTER" prefHeight="21.0" prefWidth="168.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="4" GridPane.rowIndex="1" />
               <Label fx:id="cellAddress14" alignment="CENTER" prefHeight="21.0" prefWidth="159.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="3" GridPane.rowIndex="1" />
               <Label fx:id="cellAddress23" alignment="CENTER" prefHeight="21.0" prefWidth="187.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="2" GridPane.rowIndex="3" />
               <Label fx:id="cellAddress22" alignment="CENTER" prefHeight="21.0" prefWidth="163.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="1" GridPane.rowIndex="3" />
               <Label fx:id="cellAddress31" alignment="CENTER" prefHeight="21.0" prefWidth="191.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.rowIndex="5" />
               <Label fx:id="cellAddress21" alignment="CENTER" prefHeight="21.0" prefWidth="143.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.rowIndex="3" />
               <Label fx:id="cellAddress13" alignment="CENTER" prefHeight="21.0" prefWidth="173.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="2" GridPane.rowIndex="1" />
               <Label fx:id="cellAddress12" alignment="CENTER" prefHeight="21.0" prefWidth="171.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="1" GridPane.rowIndex="1" />
               <Label fx:id="cellAddress32" alignment="CENTER" prefHeight="21.0" prefWidth="186.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="1" GridPane.rowIndex="5" />
               <Label fx:id="cellAddress25" alignment="CENTER" prefHeight="21.0" prefWidth="256.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="4" GridPane.rowIndex="3" />
               <Label fx:id="cellAddress24" alignment="CENTER" prefHeight="21.0" prefWidth="185.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="3" GridPane.rowIndex="3" />
               <Label fx:id="cellAddress35" alignment="CENTER" prefHeight="21.0" prefWidth="212.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="4" GridPane.rowIndex="5" />
               <Label fx:id="cellAddress34" alignment="CENTER" prefHeight="21.0" prefWidth="201.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="3" GridPane.rowIndex="5" />
               <Label fx:id="cellAddress33" alignment="CENTER" prefHeight="21.0" prefWidth="167.0" styleClass="label-bright" stylesheets="@DarkTheme.css" text="Insufficient Access" GridPane.columnIndex="2" GridPane.rowIndex="5" />
      <Label alignment="CENTER" prefHeight="62.0" prefWidth="221.0" styleClass="label-header" stylesheets="@DarkTheme.css" text="3-2" GridPane.columnIndex="1" GridPane.rowIndex="4" />
           </children>
       </GridPane>
```
