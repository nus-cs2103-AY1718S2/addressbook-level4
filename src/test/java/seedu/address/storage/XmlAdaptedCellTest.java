package seedu.address.storage;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.Assert;

public class XmlAdaptedCellTest {

    public static final String INVALID_CELL_ADDRESS = "0-0";

    @Test
    public void toModelType_validCell_returnsCell() {
        XmlAdaptedCell cell = new XmlAdaptedCell();
    }

    @Test
    public void toModelType_nullCellAddress_throwsIllegalValueException() {
        XmlAdaptedCell cell = new XmlAdaptedCell();
        String expectedMessage = XmlAdaptedCell.INVALID_CELL;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cell::toModelType);
    }

    @Test
    public void toModelType_invalidCellAddress_throwsIllegalValueException() {
        XmlAdaptedCell cell = new XmlAdaptedCell(INVALID_CELL_ADDRESS, null);
        String expectedMessage = XmlAdaptedCell.INVALID_CELL;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cell::toModelType);
    }
}
