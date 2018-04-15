//@@author sarahgoh97
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
