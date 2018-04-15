//@@author sarahgoh97
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
