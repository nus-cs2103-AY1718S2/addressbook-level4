//@@author sarahgoh97
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
