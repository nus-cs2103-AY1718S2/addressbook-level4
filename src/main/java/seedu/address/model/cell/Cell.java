//@@author sarahgoh97
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
