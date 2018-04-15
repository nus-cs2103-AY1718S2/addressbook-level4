//@@author sarahgoh97
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
